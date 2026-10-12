package dev.yggdrasil.core.domain;

import dev.yggdrasil.core.application.Evaluator;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static dev.yggdrasil.core.support.RuleFixtures.*;
import static dev.yggdrasil.core.domain.Operand.*;
import static org.junit.jupiter.api.Assertions.*;

class RevisionTest {
    @Test void sharedConditionHasPinnedProvenanceAndConflictingDefinitionIsRejected() {
        Leaf shared = leaf(Operator.EQUAL, 1, 1);
        Tree first = tree(shared, "10", null);
        Tree second = tree(new Branch(id(), Composition.ALL, List.of(shared, leaf(Operator.EQUAL, 2, 2))), "20", null);
        Forest forest = new Forest(id(), 1, List.of(first, second), thresholds(), Decision.REVIEW, Map.of(),
                java.util.Set.of(Decision.ALLOW, Decision.REVIEW, Decision.BLOCK), List.of(new ComponentRevision(7, shared)));
        var result = new Evaluator().evaluate(forest, new EvaluationInput(Map.of()));
        assertEquals(decimal("30"), result.score());
        Leaf conflicting = Leaf.of(shared.id(), Operator.EQUAL, literal(2), literal(3));
        assertThrows(IllegalArgumentException.class, () -> forest(List.of(first, tree(conflicting, "5", null))));
    }

    @Test void revisionsAndConcurrentEvaluationsDoNotShareMutableState() throws Exception {
        Tree rule = tree(Leaf.of(id(), Operator.GREATER_THAN, attribute("amount"), literal(1000)), "80", null);
        Forest first = forest(List.of(rule));
        Forest next = new Forest(first.id(), first.revision() + 1, first.trees(), first.thresholds(), Decision.BLOCK, Map.of());
        Evaluator evaluator = new Evaluator();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var allow = executor.submit(() -> evaluator.evaluate(first, new EvaluationInput(Map.of("amount", 10))));
            var block = executor.submit(() -> evaluator.evaluate(next, new EvaluationInput(Map.of("amount", 1500))));
            assertEquals(Decision.ALLOW, allow.get().decision());
            assertEquals(Decision.BLOCK, block.get().decision());
            assertEquals(1, allow.get().revision());
            assertEquals(2, block.get().revision());
            assertEquals(decimal("80"), block.get().score());
        }
    }

    @Test void temporalPathsRequireZoneAndCalendarOverflowIsUnknown() {
        assertDoesNotThrow(() -> Leaf.of(id(), Operator.SAME_DATE, attribute("a"), attribute("b")));
        Leaf condition = new Leaf(id(), Operator.SAME_DATE, List.of(attribute("a"), attribute("b")), false, true, true, ZoneId.of("UTC"));
        Forest forest = forest(List.of(tree(condition, "80", null)));
        Evaluator evaluator = new Evaluator();
        var civil = evaluator.evaluate(forest, new EvaluationInput(Map.of("a", LocalDate.EPOCH, "b", LocalDate.EPOCH)));
        assertEquals(Decision.BLOCK, civil.decision());
        var extremes = evaluator.evaluate(forest, new EvaluationInput(Map.of("a", Instant.MAX, "b", Instant.MAX)));
        assertEquals(EvaluationResult.State.UNKNOWN, extremes.state());
    }
}
