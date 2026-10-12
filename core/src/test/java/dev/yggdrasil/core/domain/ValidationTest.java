package dev.yggdrasil.core.domain;

import dev.yggdrasil.core.application.Evaluator;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static dev.yggdrasil.core.support.RuleFixtures.*;
import static dev.yggdrasil.core.domain.Operand.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    List<Tree> trees() { return List.of(tree(leaf(Operator.EQUAL, true, true), "1", null)); }
    Forest ranges(List<Threshold> ranges) { return new Forest(id(), 1, trees(), ranges, Decision.REVIEW, Map.of()); }

    @Test void rejectsMissingPolicyEmptyForestAndInvalidRevision() {
        assertThrows(NullPointerException.class, () -> new Forest(id(), 1, trees(), thresholds(), null, Map.of()));
        assertThrows(IllegalArgumentException.class, () -> new Forest(id(), 1, List.of(), thresholds(), Decision.REVIEW, Map.of()));
        assertThrows(IllegalArgumentException.class, () -> new Forest(id(), 0, trees(), thresholds(), Decision.REVIEW, Map.of()));
        assertThrows(IllegalArgumentException.class, () -> new Tree(id(), "rule", leaf(Operator.EQUAL, 1, 1), null, null));
        assertThrows(IllegalArgumentException.class, () -> new Decision(" "));
    }

    @Test void rejectsThresholdGapsOverlapsAndEmptyRanges() {
        assertThrows(IllegalArgumentException.class, () -> ranges(List.of()));
        assertThrows(IllegalArgumentException.class, () -> ranges(List.of(new Threshold(BigDecimal.ZERO, true, null, false, Decision.ALLOW))));
        assertThrows(IllegalArgumentException.class, () -> ranges(List.of(new Threshold(null, false, BigDecimal.ZERO, false, Decision.ALLOW))));
        for (boolean included : List.of(true, false)) {
            assertThrows(IllegalArgumentException.class, () -> ranges(List.of(
                    new Threshold(null, false, BigDecimal.ZERO, included, Decision.ALLOW),
                    new Threshold(BigDecimal.ZERO, included, null, false, Decision.BLOCK))));
        }
        assertThrows(IllegalArgumentException.class, () -> ranges(List.of(
                new Threshold(null, false, BigDecimal.ZERO, false, Decision.ALLOW),
                new Threshold(BigDecimal.ONE, true, null, false, Decision.BLOCK))));
        assertThrows(IllegalArgumentException.class, () -> new Threshold(BigDecimal.ONE, true, BigDecimal.ZERO, true, Decision.ALLOW));
        assertThrows(IllegalArgumentException.class, () -> new Threshold(BigDecimal.ZERO, false, BigDecimal.ZERO, true, Decision.ALLOW));
    }

    @Test void acceptsSingletonRangeAndSortsWithoutChangingCoverage() {
        Forest forest = ranges(List.of(
                new Threshold(BigDecimal.ZERO, false, null, false, Decision.BLOCK),
                new Threshold(BigDecimal.ZERO, true, BigDecimal.ZERO, true, Decision.REVIEW),
                new Threshold(null, false, BigDecimal.ZERO, false, Decision.ALLOW)));
        var result = new Evaluator().evaluate(new Forest(id(), 1,
                List.of(tree(leaf(Operator.EQUAL, 1, 2), "1", null)), forest.thresholds(), Decision.BLOCK, Map.of()), new EvaluationInput(Map.of()));
        assertEquals(Decision.REVIEW, result.decision());
    }

    @Test void validatesOperatorArityBoundsAndTemporalConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> Leaf.of(id(), Operator.EQUAL, literal(1)));
        assertThrows(IllegalArgumentException.class, () -> Leaf.of(id(), Operator.EXISTS, literal(1), literal(2)));
        assertThrows(IllegalArgumentException.class, () -> leaf(Operator.BETWEEN, 1, 20, 10));
        assertThrows(IllegalArgumentException.class, () -> leaf(Operator.BETWEEN, 1, "a", "b"));
        assertThrows(IllegalArgumentException.class, () -> leaf(Operator.SAME_DATE, Instant.EPOCH, Instant.EPOCH));
        assertThrows(IllegalArgumentException.class, () -> new Leaf(id(), Operator.IN, List.of(literal(1), literal(List.of(1))), true, true, true, null));
        assertThrows(IllegalArgumentException.class, () -> new Branch(id(), Composition.ALL, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Branch(id(), Composition.NOT, List.of(leaf(Operator.EQUAL, 1, 1), leaf(Operator.EQUAL, 1, 2))));
    }

    @Test void rejectsNonFiniteUnsupportedAndCyclicInput() {
        assertThrows(IllegalArgumentException.class, () -> new EvaluationInput(Map.of("number", Double.NaN)));
        assertThrows(IllegalArgumentException.class, () -> literal(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> literal(new Object()));
        Map<String, Object> cycle = new HashMap<>(); cycle.put("self", cycle);
        assertThrows(IllegalArgumentException.class, () -> new EvaluationInput(cycle));
        assertThrows(IllegalArgumentException.class, () -> new AttributePath.Index(-1));
        assertThrows(IllegalArgumentException.class, () -> AttributePath.keys());
    }

    @Test void validatesIdentitiesAliasesCatalogAndComponentProvenance() {
        Tree duplicate = trees().getFirst();
        assertThrows(IllegalArgumentException.class, () -> forest(List.of(duplicate, duplicate)));
        Tree unresolved = tree(Leaf.of(id(), Operator.EXISTS, alias("unknown")), "1", null);
        assertThrows(IllegalArgumentException.class, () -> forest(List.of(unresolved)));
        List<Tree> trees = trees();
        assertThrows(IllegalArgumentException.class, () -> new Forest(id(), 1, trees, thresholds(), Decision.REVIEW, Map.of(), Set.of(Decision.ALLOW), List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Forest(id(), 1, trees, thresholds(), Decision.REVIEW, Map.of(),
                Set.of(Decision.ALLOW, Decision.REVIEW, Decision.BLOCK), List.of(new ComponentRevision(1, leaf(Operator.EQUAL, 1, 1)))));
        Forest forest = new Forest(id(), 1, trees, thresholds(), Decision.REVIEW, Map.of(),
                Set.of(Decision.ALLOW, Decision.REVIEW, Decision.BLOCK), List.of(new ComponentRevision(3, trees.getFirst().condition())));
        assertEquals(3, forest.components().getFirst().revision());
        assertThrows(IllegalArgumentException.class, () -> new ComponentRevision(0, leaf(Operator.EQUAL, 1, 1)));
    }

    @Test void forestDefensivelyCopiesConfiguration() {
        List<Tree> trees = new ArrayList<>(trees());
        Map<String, AttributePath> aliases = new HashMap<>(); aliases.put("value", AttributePath.keys("a"));
        Forest forest = new Forest(id(), 1, trees, thresholds(), Decision.REVIEW, aliases);
        trees.clear(); aliases.clear();
        assertEquals(1, forest.trees().size());
        assertEquals(1, forest.aliases().size());
        assertThrows(UnsupportedOperationException.class, () -> forest.trees().clear());
    }
}
