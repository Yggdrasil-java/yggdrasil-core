package dev.yggdrasil.core.application;

import dev.yggdrasil.core.domain.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static dev.yggdrasil.core.domain.Operand.attribute;
import static dev.yggdrasil.core.domain.Operand.literal;
import static org.junit.jupiter.api.Assertions.*;
import static dev.yggdrasil.core.support.RuleFixtures.*;

class EvaluatorTest {
    private final Evaluator evaluator = new Evaluator();

    EvaluationResult evaluate(Condition condition, Map<String, Object> attributes) {
        return evaluator.evaluate(forest(List.of(tree(condition, "1", null))), new EvaluationInput(attributes));
    }
    Truth truth(Condition condition) {
        return Truth.valueOf(evaluate(condition, Map.of()).explanation().getFirst().outcome().name());
    }

    record Case(Operator operator, Object left, Object right, Truth expected) {}
    static Stream<Case> operators() {
        return Stream.of(
                new Case(Operator.EQUAL, decimal("1.0"), decimal("1.00"), Truth.TRUE),
                new Case(Operator.EQUAL, "1", 1, Truth.UNKNOWN),
                new Case(Operator.EQUAL, null, null, Truth.TRUE),
                new Case(Operator.NOT_EQUAL, null, false, Truth.TRUE),
                new Case(Operator.NOT_EQUAL, "1", 1, Truth.UNKNOWN),
                new Case(Operator.GREATER_THAN, 2, 1, Truth.TRUE),
                new Case(Operator.GREATER_THAN_OR_EQUAL, 1, 1, Truth.TRUE),
                new Case(Operator.LESS_THAN, -2, -1, Truth.TRUE),
                new Case(Operator.LESS_THAN_OR_EQUAL, 1, 1, Truth.TRUE),
                new Case(Operator.GREATER_THAN, "2", 1, Truth.UNKNOWN),
                new Case(Operator.GREATER_THAN, LocalDate.parse("2026-10-12"), LocalDate.parse("2026-10-11"), Truth.UNKNOWN),
                new Case(Operator.CONTAINS, "device", "vic", Truth.TRUE),
                new Case(Operator.CONTAINS, "device", "VIC", Truth.FALSE),
                new Case(Operator.CONTAINS, List.of(1), 1, Truth.UNKNOWN),
                new Case(Operator.STARTS_WITH, "device", "dev", Truth.TRUE),
                new Case(Operator.ENDS_WITH, "device", "ice", Truth.TRUE),
                new Case(Operator.ENDS_WITH, "a", "long", Truth.FALSE),
                new Case(Operator.IN, 1, List.of(decimal("1.00"), 2), Truth.TRUE),
                new Case(Operator.IN, 1, List.of("1"), Truth.UNKNOWN),
                new Case(Operator.IN, 1, List.of("1", 1), Truth.TRUE),
                new Case(Operator.CONTAINS_ANY, List.of(1), List.of(), Truth.FALSE),
                new Case(Operator.CONTAINS_ALL, List.of(), List.of(), Truth.TRUE),
                new Case(Operator.CONTAINS_ALL, List.of(1), List.of(1, 1), Truth.TRUE),
                new Case(Operator.CONTAINS_ALL, List.of(1), List.of("1", 2), Truth.FALSE),
                new Case(Operator.CONTAINS_ANY, List.of(1), List.of("1", 2), Truth.UNKNOWN),
                new Case(Operator.BEFORE, Instant.parse("2026-10-11T00:00:00Z"), Instant.parse("2026-10-12T00:00:00Z"), Truth.TRUE),
                new Case(Operator.AFTER, LocalDate.parse("2026-10-12"), LocalDate.parse("2026-10-11"), Truth.TRUE),
                new Case(Operator.BEFORE, "2026-10-11", "2026-10-12", Truth.UNKNOWN),
                new Case(Operator.SAME_DATE, LocalDate.parse("2026-10-12"), LocalDate.parse("2026-10-12"), Truth.TRUE),
                new Case(Operator.EQUAL, List.of(1, "a"), List.of("1", "b"), Truth.FALSE),
                new Case(Operator.EQUAL, List.of(1), List.of("1"), Truth.UNKNOWN),
                new Case(Operator.EQUAL, Map.of("a", 1, "b", 2), Map.of("b", 2, "a", decimal("1.0")), Truth.TRUE),
                new Case(Operator.EQUAL, Map.of("a", 1), Map.of("b", 1), Truth.FALSE),
                new Case(Operator.EQUAL, Arrays.asList(null, 1), Arrays.asList(null, decimal("1.0")), Truth.TRUE),
                new Case(Operator.LESS_THAN, null, 1, Truth.UNKNOWN));
    }

    @ParameterizedTest @MethodSource("operators")
    void comparesWithoutImplicitCoercion(Case test) {
        assertEquals(test.expected(), truth(leaf(test.operator(), test.left(), test.right())));
    }

    @Test void scoresInOrderAndSelectsBoundary() {
        Forest forest = forest(List.of(tree(leaf(Operator.EQUAL, true, true), "30", null),
                tree(leaf(Operator.EQUAL, true, true), "50", null)));
        EvaluationResult result = evaluator.evaluate(forest, new EvaluationInput(Map.of()));
        assertEquals(Decision.BLOCK, result.decision());
        assertEquals(decimal("80"), result.score());
        assertFalse(result.partialScore());
        assertEquals(forest.id(), result.forestId());
        assertEquals(forest.revision(), result.revision());
        assertEquals(EvaluationResult.Reason.THRESHOLD, result.reason());
        assertEquals(List.of(decimal("30"), decimal("50")), result.explanation().stream().map(Explanation::contribution).toList());
    }

    @Test void terminalIncludesItsContributionAndMarksLaterTreesSkipped() {
        Forest forest = forest(List.of(tree(leaf(Operator.EQUAL, true, true), "-10", null),
                tree(leaf(Operator.EQUAL, 1, 1), "30", Decision.BLOCK),
                tree(leaf(Operator.EQUAL, true, true), "1000", null)));
        EvaluationResult result = evaluator.evaluate(forest, new EvaluationInput(Map.of()));
        assertEquals(decimal("20"), result.score());
        assertEquals(Decision.BLOCK, result.decision());
        assertTrue(result.partialScore());
        assertEquals(EvaluationResult.Reason.TERMINAL, result.reason());
        assertEquals(Decision.BLOCK, result.explanation().get(1).terminalDecision());
        assertEquals(Explanation.Outcome.NOT_EVALUATED, result.explanation().get(2).outcome());
        assertEquals(Explanation.Outcome.NOT_EVALUATED, result.explanation().get(2).children().getFirst().outcome());
    }

    @Test void unknownKeepsKnownScoreAndConfiguredRiskDecision() {
        var result = evaluator.evaluate(forest(List.of(tree(Leaf.of(id(), Operator.EQUAL, attribute("missing"), literal(1)), "50", null),
                tree(leaf(Operator.EQUAL, true, true), "30", null))), new EvaluationInput(Map.of()));
        assertEquals(EvaluationResult.State.UNKNOWN, result.state());
        assertEquals(EvaluationResult.Reason.UNKNOWN_POLICY, result.reason());
        assertEquals(Decision.REVIEW, result.decision());
        assertEquals(decimal("30"), result.score());
        assertTrue(result.partialScore());
    }

    @Test void terminalOverridesPreviousUnknown() {
        var result = evaluator.evaluate(forest(List.of(tree(Leaf.of(id(), Operator.EQUAL, attribute("missing"), literal(1)), "50", null),
                tree(leaf(Operator.EQUAL, true, true), null, Decision.BLOCK))), new EvaluationInput(Map.of()));
        assertEquals(EvaluationResult.State.DETERMINATE, result.state());
        assertEquals(Decision.BLOCK, result.decision());
        assertTrue(result.partialScore());
    }

    @Test void falseDoesNotScoreOrTerminate() {
        var result = evaluator.evaluate(forest(List.of(tree(leaf(Operator.EQUAL, 1, 2), "99", Decision.BLOCK))), new EvaluationInput(Map.of()));
        assertEquals(BigDecimal.ZERO, result.score());
        assertEquals(Decision.ALLOW, result.decision());
        assertFalse(result.partialScore());
    }

    @Test void branchesPreserveUnknownAndRecordShortCircuit() {
        Leaf unknown = leaf(Operator.EQUAL, 1, "1");
        Leaf no = leaf(Operator.EQUAL, 1, 2);
        Leaf yes = leaf(Operator.EQUAL, 1, 1);
        assertEquals(Truth.FALSE, truth(new Branch(id(), Composition.ALL, List.of(unknown, no))));
        assertEquals(Truth.TRUE, truth(new Branch(id(), Composition.ANY, List.of(unknown, yes))));
        assertEquals(Truth.UNKNOWN, truth(new Branch(id(), Composition.ALL, List.of(unknown, yes))));
        assertEquals(Truth.UNKNOWN, truth(new Branch(id(), Composition.ANY, List.of(unknown, no))));
        assertEquals(Truth.UNKNOWN, truth(new Branch(id(), Composition.NOT, List.of(unknown))));
        assertEquals(Truth.FALSE, truth(new Branch(id(), Composition.NOT, List.of(yes))));
        var result = evaluate(new Branch(id(), Composition.ALL, List.of(no, unknown)), Map.of());
        assertEquals(Explanation.Outcome.NOT_EVALUATED, result.explanation().getFirst().children().getFirst().children().get(1).outcome());
    }

    @Test void missingNullAndInvalidNavigationAreDistinct() {
        Map<String, Object> input = new HashMap<>(); input.put("null", null); input.put("empty", "");
        assertOutcome(Operator.EXISTS, attribute("null"), input, Truth.TRUE);
        assertOutcome(Operator.IS_NULL, attribute("null"), input, Truth.TRUE);
        assertOutcome(Operator.IS_NULL, attribute("absent"), input, Truth.FALSE);
        assertOutcome(Operator.IS_MISSING, attribute("absent"), input, Truth.TRUE);
        assertOutcome(Operator.EXISTS, attribute("absent"), input, Truth.FALSE);
        assertOutcome(Operator.IS_EMPTY, attribute("null"), input, Truth.UNKNOWN);
        assertOutcome(Operator.IS_EMPTY, attribute("empty"), input, Truth.TRUE);
        assertOutcome(Operator.IS_MISSING, attribute("null", "child"), input, Truth.UNKNOWN);
        assertOutcome(Operator.IS_MISSING, attribute("empty", "child"), input, Truth.UNKNOWN);
        assertEquals(Explanation.Outcome.UNKNOWN, evaluate(Leaf.of(id(), Operator.EQUAL, attribute("absent"), literal(null)), input).explanation().getFirst().outcome());
    }
    void assertOutcome(Operator operator, Operand operand, Map<String, Object> input, Truth truth) {
        assertEquals(truth.name(), evaluate(Leaf.of(id(), operator, operand), input).explanation().getFirst().outcome().name());
    }

    @Test void typedPathsAndAliasesResolveWithoutParsingKeys() {
        var path = AttributePath.of(new AttributePath.Key("a.b"), new AttributePath.Index(0), new AttributePath.Key("value"));
        Tree tree = tree(Leaf.of(id(), Operator.EQUAL, Operand.alias("amount"), literal(10)), "80", null);
        Forest forest = new Forest(id(), 1, List.of(tree), thresholds(), Decision.BLOCK, Map.of("amount", path));
        var result = evaluator.evaluate(forest, new EvaluationInput(Map.of("a.b", List.of(Map.of("value", 10)))));
        assertEquals(Decision.BLOCK, result.decision());
        assertOutcome(Operator.IS_MISSING, Operand.attribute(AttributePath.of(new AttributePath.Key("list"), new AttributePath.Index(3))), Map.of("list", List.of()), Truth.TRUE);
    }

    @Test void sameDateUsesExplicitZoneAndPreservesTemporalTypes() {
        var a = Instant.parse("2026-10-12T01:00:00.123456789Z");
        var b = Instant.parse("2026-10-11T23:00:00Z");
        Leaf leaf = new Leaf(id(), Operator.SAME_DATE, List.of(literal(a), literal(b)), false, true, true, ZoneId.of("America/Sao_Paulo"));
        assertEquals(Truth.TRUE, truth(leaf));
        assertEquals(Truth.UNKNOWN, truth(new Leaf(id(), Operator.SAME_DATE, List.of(literal(a), literal(LocalDate.parse("2026-10-12"))), false, true, true, ZoneId.of("UTC"))));
    }

    @Test void betweenRespectsExplicitBoundsAndDynamicInversion() {
        assertEquals(Truth.TRUE, truth(leaf(Operator.BETWEEN, 10, 10, 20)));
        assertEquals(Truth.FALSE, truth(new Leaf(id(), Operator.BETWEEN, List.of(literal(10), literal(10), literal(20)), false, false, true, null)));
        assertEquals(Truth.TRUE, truth(leaf(Operator.BETWEEN, LocalDate.parse("2026-10-12"), LocalDate.parse("2026-10-11"), LocalDate.parse("2026-10-13"))));
        var result = evaluate(Leaf.of(id(), Operator.BETWEEN, literal(15), attribute("low"), attribute("high")), Map.of("low", 20, "high", 10));
        assertEquals(EvaluationResult.State.UNKNOWN, result.state());
    }

    @Test void ignoreCaseDoesNotDependOnDefaultLocale() {
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            for (Operator op : List.of(Operator.EQUAL, Operator.CONTAINS, Operator.STARTS_WITH, Operator.ENDS_WITH)) {
                assertEquals(Truth.TRUE, truth(new Leaf(id(), op, List.of(literal("I"), literal("i")), true, true, true, null)));
            }
        } finally { Locale.setDefault(previous); }
    }

    @Test void inputsAndLiteralsAreDeeplyImmutableAndDoNotLeakIntoExplanation() {
        List<Object> list = new ArrayList<>(); list.add(null); list.add(10);
        Map<String, Object> map = new HashMap<>(); map.put("private-value", list);
        EvaluationInput input = new EvaluationInput(map);
        Operand.Literal literal = new Operand.Literal(map);
        list.add(99); map.clear();
        assertEquals(2, ((List<?>) input.attributes().get("private-value")).size());
        assertEquals(input.attributes(), literal.value());
        assertThrows(UnsupportedOperationException.class, () -> input.attributes().put("x", 1));
        var result = evaluator.evaluate(forest(List.of(tree(Leaf.of(id(), Operator.EXISTS, attribute("private-value")), "1", null))), input);
        assertFalse(result.explanation().toString().contains("private-value"));
        assertThrows(UnsupportedOperationException.class, () -> result.explanation().clear());
    }

    @Test void customDecisionsAndDecimalScoresHaveNoFixedScale() {
        Decision authenticate = new Decision("REQUIRE_AUTHENTICATION");
        var result = evaluator.evaluate(forest(List.of(tree(leaf(Operator.EQUAL, 1, 1), "10000.000001", authenticate))), new EvaluationInput(Map.of()));
        assertEquals(authenticate, result.decision());
        assertEquals(decimal("10000.000001"), result.score());
    }
}
