package dev.yggdrasil.core.domain;

import dev.yggdrasil.core.application.Evaluator;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static dev.yggdrasil.core.support.RuleFixtures.*;
import static dev.yggdrasil.core.domain.Operand.*;
import static org.junit.jupiter.api.Assertions.*;

class ComponentCatalogTest {
    final Set<Decision> decisions = Set.of(Decision.ALLOW, Decision.REVIEW, Decision.BLOCK);

    @Test void resolvesExactVersionAndRejectsMissingRevision() {
        Leaf first = leaf(Operator.EQUAL, 1, 1);
        Leaf second = Leaf.of(first.id(), Operator.EQUAL, literal(1), literal(2));
        var one = new ComponentRevision(1, first);
        var two = new ComponentRevision(2, second);
        ComponentCatalog catalog = new ComponentCatalog(List.of(one, two));
        assertSame(one, catalog.resolve(first.id(), 1));
        assertSame(two, catalog.resolve(first.id(), 2));
        assertThrows(IllegalArgumentException.class, () -> catalog.resolve(first.id(), 3));
        assertThrows(IllegalArgumentException.class, () -> new ComponentRevision(id(), 1, first));
    }

    @Test void rejectsConflictingDefinitionForSameVersion() {
        Leaf first = leaf(Operator.EQUAL, 1, 1);
        Leaf second = Leaf.of(first.id(), Operator.EQUAL, literal(1), literal(2));
        assertThrows(IllegalArgumentException.class,
                () -> new ComponentCatalog(List.of(new ComponentRevision(1, first), new ComponentRevision(1, second))));
    }

    @Test void forestRejectsMaterializedDefinitionThatDiffersFromPinnedSnapshot() {
        Leaf first = leaf(Operator.EQUAL, 1, 1);
        Leaf altered = Leaf.of(first.id(), Operator.EQUAL, literal(1), literal(2));
        var snapshot = new ComponentRevision(1, first);
        assertThrows(IllegalArgumentException.class, () -> new Forest(id(), 1, List.of(tree(altered, "1", null)),
                thresholds(), Decision.REVIEW, Map.of(), decisions, List.of(snapshot)));
        assertDoesNotThrow(() -> new Forest(id(), 1, List.of(tree(first, "1", null)),
                thresholds(), Decision.REVIEW, Map.of(), decisions, List.of(snapshot)));
    }

    @Test void pinsTreeSnapshotAndRejectsTwoVersionsInOneForest() {
        Tree tree = tree(leaf(Operator.EQUAL, 1, 1), "1", null);
        var first = new ComponentRevision(1, tree);
        var second = new ComponentRevision(2, tree);
        assertDoesNotThrow(() -> new Forest(id(), 1, List.of(tree), thresholds(), Decision.REVIEW, Map.of(), decisions, List.of(first)));
        assertThrows(IllegalArgumentException.class, () -> new Forest(id(), 1, List.of(tree),
                thresholds(), Decision.REVIEW, Map.of(), decisions, List.of(first, second)));
    }

    @Test void catalogCopiesItsInputAndOldForestKeepsOriginalSnapshot() {
        Leaf leaf = leaf(Operator.EQUAL, 1, 1);
        var revision = new ComponentRevision(1, leaf);
        var list = new ArrayList<>(List.of(revision));
        ComponentCatalog original = new ComponentCatalog(list); list.clear();
        assertSame(revision, original.resolve(leaf.id(), 1));
        var next = new ComponentRevision(2, Leaf.of(leaf.id(), Operator.EQUAL, literal(1), literal(2)));
        ComponentCatalog updated = new ComponentCatalog(List.of(revision, next));
        assertEquals(leaf, updated.resolve(leaf.id(), 1).definition());
        assertNotEquals(leaf, updated.resolve(leaf.id(), 2).definition());
    }
}
