package dev.yggdrasil.examples;

import dev.yggdrasil.core.domain.*;
import dev.yggdrasil.core.application.Evaluator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/** Consumer smoke scenarios compiled against the packaged JPMS API. */
public final class CoreConsumer {
    public static void main(String[] args) {
        var evaluator = new Evaluator();
        var condition = Leaf.of(UUID.randomUUID(), Operator.GREATER_THAN,
                Operand.alias("amount"), Operand.literal(1000));
        var tree = new Tree(UUID.randomUUID(), "high-amount", condition, new BigDecimal("30"), null);
        var forest = forest(List.of(tree), Map.of("amount", AttributePath.keys("transaction", "amount")), List.of());
        var input = new EvaluationInput(Map.of("transaction", Map.of("amount", 1500)));
        var result = evaluator.evaluate(forest, input);
        require(result.decision().equals(Decision.REVIEW), "Alias and decimal scoring");
        require(result.score().compareTo(new BigDecimal("30")) == 0, "Score");
        require(result.explanation().getFirst().children().getFirst().operands().isEmpty(), "Default privacy");

        var investigation = evaluator.evaluate(forest, input, EvaluationOptions.INVESTIGATION);
        require(investigation.explanation().getFirst().children().getFirst().operands().getFirst().value()
                .equals(new BigDecimal("1500")), "Explicit resolved value capture");
        var unknown = evaluator.evaluate(forest, new EvaluationInput(Map.of()));
        require(unknown.state() == EvaluationResult.State.UNKNOWN, "Missing data state");
        require(unknown.decision().equals(Decision.BLOCK), "Configured risk decision");
        require(unknown.explanation().getFirst().children().getFirst().diagnostic()
                == Explanation.Diagnostic.MISSING_ATTRIBUTE, "Missing data diagnosis");

        var civil = Leaf.of(UUID.randomUUID(), Operator.SAME_DATE, Operand.attribute("first"), Operand.attribute("second"));
        var civilTree = new Tree(UUID.randomUUID(), "same-civil-date", civil, BigDecimal.ONE, null);
        var civilResult = evaluator.evaluate(forest(List.of(civilTree), Map.of(), List.of()),
                new EvaluationInput(Map.of("first", LocalDate.EPOCH, "second", LocalDate.EPOCH)));
        require(civilResult.score().compareTo(BigDecimal.ONE) == 0, "Civil dates without timezone");

        var publication = new ComponentRevision(1, condition);
        var catalog = new ComponentCatalog(List.of(publication));
        var pinned = catalog.resolve(condition.id(), 1);
        var publishedForest = forest(List.of(tree), forest.aliases(), List.of(pinned));
        require(evaluator.evaluate(publishedForest, input).decision().equals(Decision.REVIEW), "Pinned published component");
        var changed = Leaf.of(condition.id(), Operator.GREATER_THAN, Operand.alias("amount"), Operand.literal(2000));
        var changedTree = new Tree(tree.id(), tree.name(), changed, tree.contribution(), null);
        try {
            forest(List.of(changedTree), forest.aliases(), List.of(pinned));
            throw new AssertionError("Mismatched pinned definition accepted");
        } catch (IllegalArgumentException expected) {
            require(expected.getMessage().contains("component"), "Pinned mismatch diagnosis");
        }

        var terminal = new Tree(UUID.randomUUID(), "confirmed-fraud",
                Leaf.of(UUID.randomUUID(), Operator.EQUAL, Operand.literal(true), Operand.literal(true)),
                BigDecimal.TEN, new Decision("REQUIRE_AUTHENTICATION"));
        var terminalResult = evaluator.evaluate(new Forest(UUID.randomUUID(), 1, List.of(terminal, tree),
                ranges(), Decision.BLOCK, forest.aliases()), input);
        require(terminalResult.partialScore(), "Terminal score is partial");
        require(terminalResult.explanation().getFirst().terminalDecision().equals(terminal.terminal()), "Terminal explanation");
        require(terminalResult.explanation().get(1).outcome() == Explanation.Outcome.NOT_EVALUATED, "Skipped later rule");
        System.out.println("Consumer scenarios: scoring, aliases, privacy, investigation, diagnostics, dates, components, terminal decisions OK");
    }

    private static List<Threshold> ranges() {
        return List.of(new Threshold(null, false, new BigDecimal("30"), false, Decision.ALLOW),
                new Threshold(new BigDecimal("30"), true, null, false, Decision.REVIEW));
    }

    private static Forest forest(List<Tree> trees, Map<String, AttributePath> aliases, List<ComponentRevision> components) {
        return new Forest(UUID.randomUUID(), 1, trees, ranges(), Decision.BLOCK, aliases,
                Set.of(Decision.ALLOW, Decision.REVIEW, Decision.BLOCK), components);
    }

    private static void require(boolean value, String scenario) {
        if (!value) throw new AssertionError(scenario);
    }
}
