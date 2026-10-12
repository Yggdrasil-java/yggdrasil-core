package dev.yggdrasil.core.application;

import dev.yggdrasil.core.domain.*;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.yggdrasil.core.support.RuleFixtures.*;
import static dev.yggdrasil.core.domain.Operand.*;
import static org.junit.jupiter.api.Assertions.*;

class ExplanationTest {
    final Evaluator evaluator = new Evaluator();
    Explanation evaluate(Leaf leaf, Map<String, Object> input, EvaluationOptions options) {
        return evaluator.evaluate(forest(List.of(tree(leaf, "1", null))), new EvaluationInput(input), options)
                .explanation().getFirst().children().getFirst();
    }

    @Test void civilDateAttributesDoNotRequireZone() {
        Leaf leaf = Leaf.of(id(), Operator.SAME_DATE, attribute("first"), attribute("second"));
        var result = evaluate(leaf, Map.of("first", LocalDate.EPOCH, "second", LocalDate.EPOCH), EvaluationOptions.DEFAULT);
        assertEquals(Explanation.Outcome.TRUE, result.outcome());
    }

    @Test void instantAttributesWithoutZoneAreRejectedBeforeEvaluationEvenIfSkipped() {
        Leaf invalid = Leaf.of(id(), Operator.SAME_DATE, attribute("first"), attribute("second"));
        Forest forest = forest(List.of(tree(leaf(Operator.EQUAL, 1, 1), "1", Decision.BLOCK), tree(invalid, "1", null)));
        var error = assertThrows(InvalidRuleException.class, () -> evaluator.evaluate(forest,
                new EvaluationInput(Map.of("first", Instant.EPOCH, "second", Instant.EPOCH))));
        assertEquals(invalid.id(), error.conditionId());
        assertEquals(Explanation.Outcome.UNKNOWN, evaluate(invalid,
                Map.of("first", Instant.EPOCH, "second", LocalDate.EPOCH), EvaluationOptions.DEFAULT).outcome());
    }

    @Test void investigationCapturesResolvedValuesOnlyForEvaluatedLeaves() {
        Leaf sensitive = Leaf.of(id(), Operator.EQUAL, attribute("secret"), literal("private"));
        Forest forest = forest(List.of(tree(sensitive, "1", Decision.BLOCK), tree(sensitive, "1", null)));
        EvaluationInput input = new EvaluationInput(Map.of("secret", "private"));
        var hidden = evaluator.evaluate(forest, input);
        var captured = evaluator.evaluate(forest, input, EvaluationOptions.INVESTIGATION);
        Explanation leaf = captured.explanation().getFirst().children().getFirst();
        assertEquals(2, leaf.operands().size());
        assertEquals("private", leaf.operands().getFirst().value());
        assertEquals(0, leaf.operands().getFirst().position());
        assertEquals(OperandObservation.Status.PRESENT, leaf.operands().getFirst().status());
        assertTrue(hidden.explanation().getFirst().children().getFirst().operands().isEmpty());
        assertFalse(hidden.toString().contains("private"));
        assertTrue(captured.explanation().get(1).children().getFirst().operands().isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> leaf.operands().clear());
    }

    @Test void captureDistinguishesNullMissingAndInvalidNavigation() {
        Map<String, Object> input = new HashMap<>(); input.put("value", null);
        var nullResult = evaluate(Leaf.of(id(), Operator.IS_NULL, attribute("value")), input, EvaluationOptions.INVESTIGATION);
        var missing = evaluate(Leaf.of(id(), Operator.EXISTS, attribute("missing")), input, EvaluationOptions.INVESTIGATION);
        var invalid = evaluate(Leaf.of(id(), Operator.EXISTS, attribute("value", "child")), input, EvaluationOptions.INVESTIGATION);
        assertEquals(OperandObservation.Status.PRESENT_NULL, nullResult.operands().getFirst().status());
        assertEquals(OperandObservation.Status.MISSING, missing.operands().getFirst().status());
        assertEquals(OperandObservation.Status.INVALID_NAVIGATION, invalid.operands().getFirst().status());
        assertNull(nullResult.operands().getFirst().value());
        assertNull(missing.operands().getFirst().value());
    }

    @Test void diagnosesUnknownWithoutLeakingValues() {
        assertDiagnostic(Leaf.of(id(), Operator.EQUAL, attribute("missing"), literal(1)), Map.of(), Explanation.Diagnostic.MISSING_ATTRIBUTE);
        assertDiagnostic(Leaf.of(id(), Operator.EQUAL, attribute("value", "child"), literal(1)), Map.of("value", 1), Explanation.Diagnostic.INVALID_NAVIGATION);
        assertDiagnostic(leaf(Operator.GREATER_THAN, null, 1), Map.of(), Explanation.Diagnostic.NULL_OPERAND);
        assertDiagnostic(leaf(Operator.EQUAL, 1, "secret"), Map.of(), Explanation.Diagnostic.TYPE_MISMATCH);
        assertDiagnostic(Leaf.of(id(), Operator.BETWEEN, literal(10), attribute("low"), attribute("high")),
                Map.of("low", 20, "high", 5), Explanation.Diagnostic.INVALID_BOUNDS);
        assertDiagnostic(new Leaf(id(), Operator.SAME_DATE, List.of(literal(Instant.MAX), literal(Instant.MAX)),
                false, true, true, ZoneId.of("UTC")), Map.of(), Explanation.Diagnostic.TEMPORAL_OUT_OF_RANGE);
    }

    void assertDiagnostic(Leaf leaf, Map<String, Object> input, Explanation.Diagnostic expected) {
        Explanation result = evaluate(leaf, input, EvaluationOptions.DEFAULT);
        assertEquals(Explanation.Outcome.UNKNOWN, result.outcome());
        assertEquals(expected, result.diagnostic());
        assertTrue(result.operands().isEmpty());
    }

    @Test void captureIsImmutableAndOptionsDoNotPersistBetweenCalls() {
        Leaf leaf = Leaf.of(id(), Operator.EQUAL, attribute("list"), literal(List.of(1)));
        var result = evaluate(leaf, Map.of("list", List.of(1)), EvaluationOptions.INVESTIGATION);
        assertThrows(UnsupportedOperationException.class, () -> ((List<?>) result.operands().getFirst().value()).clear());
        assertTrue(evaluate(leaf, Map.of("list", List.of(1)), EvaluationOptions.DEFAULT).operands().isEmpty());
    }

    @Test void shortCircuitedBranchDoesNotCaptureSkippedOperands() {
        Branch branch = new Branch(id(), Composition.ANY,
                List.of(leaf(Operator.EQUAL, 1, 1), Leaf.of(id(), Operator.EQUAL, attribute("secret"), literal("private"))));
        var result = evaluator.evaluate(forest(List.of(tree(branch, "1", null))),
                new EvaluationInput(Map.of("secret", "private")), EvaluationOptions.INVESTIGATION);
        var skipped = result.explanation().getFirst().children().getFirst().children().get(1);
        assertEquals(Explanation.Outcome.NOT_EVALUATED, skipped.outcome());
        assertTrue(skipped.operands().isEmpty());
    }
}
