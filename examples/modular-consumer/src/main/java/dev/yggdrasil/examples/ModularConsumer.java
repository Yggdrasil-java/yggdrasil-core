package dev.yggdrasil.examples;

import dev.yggdrasil.administration.application.AdministrationService;
import dev.yggdrasil.administration.domain.*;
import dev.yggdrasil.administration.infrastructure.StaticAccessPolicy;
import dev.yggdrasil.authoring.application.AuthoringService;
import dev.yggdrasil.authoring.domain.ForestDefinition;
import dev.yggdrasil.authoring.infrastructure.InMemoryForestRepository;
import dev.yggdrasil.core.application.Evaluator;
import dev.yggdrasil.core.domain.*;
import dev.yggdrasil.experimentation.application.ExperimentationService;
import dev.yggdrasil.experimentation.infrastructure.InMemoryExperimentRepository;
import dev.yggdrasil.extensions.application.ExtensionRunner;
import dev.yggdrasil.extensions.domain.*;
import dev.yggdrasil.extensions.infrastructure.InMemoryExtensionState;
import dev.yggdrasil.history.application.HistoryService;
import dev.yggdrasil.history.domain.*;
import dev.yggdrasil.history.infrastructure.InMemoryHistoryRepository;
import dev.yggdrasil.replay.application.*;
import dev.yggdrasil.replay.domain.*;
import dev.yggdrasil.replay.infrastructure.InMemoryBackfillRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/** Local, executable composition with no production actions or durable backend. */
public final class ModularConsumer {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    private static Forest forest(UUID id, long revision, int contribution) {
        Leaf condition = Leaf.of(UUID.randomUUID(), Operator.GREATER_THAN_OR_EQUAL,
                Operand.attribute("attempts"), Operand.literal(2));
        Tree tree = new Tree(UUID.randomUUID(), "repeated-attempts", condition, BigDecimal.valueOf(contribution), null);
        return new Forest(id, revision, List.of(tree), List.of(
                new Threshold(null, false, BigDecimal.valueOf(80), false, Decision.ALLOW),
                new Threshold(BigDecimal.valueOf(80), true, null, false, Decision.BLOCK)), Decision.REVIEW, Map.of());
    }
    public static void main(String[] args) {
        UUID id = UUID.randomUUID();
        AuthoringService authoring = new AuthoringService(new InMemoryForestRepository());
        authoring.create(UUID.randomUUID(), id, ForestDefinition.from(forest(id, 1, 10)));
        Forest initial = authoring.publish(UUID.randomUUID(), id, 1);
        // The trusted host explicitly chooses the already authorized bootstrap revision.
        StaticAccessPolicy policy = new StaticAccessPolicy(List.of(new AccessGrant("maintainer",
                EnumSet.allOf(AdminAction.class), Set.of(id))));
        var experiments = new ExperimentationService(new InMemoryExperimentRepository(List.of(initial)), policy);
        var admin = new AdministrationService(authoring, experiments, policy);
        admin.edit("maintainer", UUID.randomUUID(), id, 2, ForestDefinition.from(forest(id, 2, 80)));
        Forest candidate = admin.publish("maintainer", UUID.randomUUID(), id, 3);
        var experiment = admin.start("maintainer", UUID.randomUUID(), UUID.randomUUID(), candidate);

        HistoryService history = new HistoryService(new InMemoryHistoryRepository());
        EntityId account = new EntityId("example", "account", "42");
        Instant time = Instant.parse("2026-01-01T00:10:00Z");
        HistoryEvent earlier = new HistoryEvent(UUID.randomUUID(), account, "attempt", time.minusSeconds(60), time.minusSeconds(60), new EvaluationInput(Map.of()));
        HistoryEvent current = new HistoryEvent(UUID.randomUUID(), account, "attempt", time, time, new EvaluationInput(Map.of()));
        history.record(earlier); history.record(current); history.record(current);
        var window = new EventWindow(account, "attempt", time.minusSeconds(600), time, time, true);
        check(history.count(window, current) == 2, "Current was duplicated or omitted");
        EntityId device = new EntityId("example", "device", "phone");
        history.relate(new Relation(UUID.randomUUID(), account, device, "uses", time.minusSeconds(60), null, time));
        check(history.neighbors(account, "uses", time, time).contains(device), "Graph relation missing");

        ProviderKey key = new ProviderKey("attempt-count", 1);
        ExtensionProvider provider = new ExtensionProvider() {
            @Override public ProviderKey key() { return key; }
            @Override public ProviderValue resolve(ExtensionContext context, Map<String, Object> arguments) {
                check(Thread.currentThread().isVirtual(), "Provider needs virtual thread");
                var query = new EventWindow(account, "attempt", context.eventTime().minusSeconds(600),
                        context.eventTime(), context.knownAt(), true);
                long count = history.count(query, current);
                context.state().put("last-count", count);
                return ProviderValue.of(count);
            }
        };
        EvaluationInput input;
        try (var runner = new ExtensionRunner(List.of(provider), 4, new InMemoryExtensionState())) {
            var live = runner.resolve(new ExtensionRequest(key, Map.of(), "live/" + id, time, time, Instant.now().plusSeconds(5)));
            var shadow = runner.resolve(new ExtensionRequest(key, Map.of(), experiment.shadowNamespace(), time, time, Instant.now().plusSeconds(5)));
            check(live.status() == Resolution.Status.PRESENT && live.value().equals(shadow.value()), "Provider results differ");
            input = new EvaluationInput(Map.of("attempts", live.value().value()));
        }
        UUID comparisonId = UUID.randomUUID();
        var comparison = admin.compare("maintainer", comparisonId, experiment.id(), input);
        check(comparison.divergent(), "Candidate should diverge");
        check(admin.live("maintainer", id).revision() == 1, "Shadow activated Live");
        var approved = admin.approve("maintainer", UUID.randomUUID(), experiment.id(), 1);
        admin.promote("maintainer", UUID.randomUUID(), experiment.id(), approved.version());
        check(admin.live("maintainer", id).revision() == 2, "Promotion failed");

        ReplayService replay = new ReplayService();
        var original = new Evaluator().evaluate(initial, input);
        var snapshot = new EvaluationSnapshot(UUID.randomUUID(), time, time, initial, input, EvaluationOptions.DEFAULT, original);
        check(replay.replay(snapshot).matchesOriginal(), "Faithful replay diverged");
        var historical = new HistoricalInput(current.id(), time, time, input);
        var earlierWindow = new EventWindow(account, "attempt", earlier.occurredAt().minusSeconds(600),
                earlier.occurredAt(), earlier.ingestedAt(), true);
        var earlierInput = new EvaluationInput(Map.of("attempts", history.count(earlierWindow, earlier)));
        var historicalEarlier = new HistoricalInput(earlier.id(), earlier.occurredAt(), earlier.ingestedAt(), earlierInput);
        var simulation = replay.simulate(candidate, List.of(historical, historicalEarlier));
        check(simulation.getFirst().result().decision().equals(Decision.ALLOW), "Future event leaked into earlier simulation");
        check(simulation.getLast().result().decision().equals(Decision.BLOCK), "Simulation failed");
        check(replay.dryRun(candidate, input).decision().equals(Decision.BLOCK), "Dry-run failed");
        var backfill = new BackfillService(new InMemoryBackfillRepository());
        UUID job = UUID.randomUUID();
        var backfillEvents = List.of(historicalEarlier, historical);
        check(!backfill.run(job, "backfill/example", backfillEvents, 1, HistoricalInput::input).completed(), "Batch limit ignored");
        check(backfill.entries(job).size() == 1, "First batch checkpoint missing");
        check(backfill.run(job, "backfill/example", backfillEvents, 1, HistoricalInput::input).completed(), "Backfill resume failed");
        backfill.run(job, "backfill/example", backfillEvents, 1, HistoricalInput::input);
        check(backfill.entries(job).size() == 2, "Backfill repeated an effect");
        admin.rollback("maintainer", UUID.randomUUID(), id, 2, 1);
        check(admin.live("maintainer", id).revision() == 1, "Rollback failed");
        check(admin.activations("maintainer", id).size() == 2, "Activation audit missing");
        System.out.println("All modules: publication, history, providers, shadow, approval, promotion, replay, backfill and rollback OK");
    }
}
