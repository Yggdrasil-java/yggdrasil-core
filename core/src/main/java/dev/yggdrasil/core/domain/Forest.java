package dev.yggdrasil.core.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/** Published, structurally validated revision. Revision allocation belongs to the host. */
public record Forest(UUID id, long revision, List<Tree> trees, List<Threshold> thresholds,
                     Decision unknownDecision, Map<String, AttributePath> aliases,
                     Set<Decision> decisions, List<ComponentRevision> components) {
    public Forest {
        Objects.requireNonNull(id, "id");
        if (revision <= 0) throw new IllegalArgumentException("Revision must be positive");
        trees = List.copyOf(trees);
        if (trees.isEmpty()) throw new IllegalArgumentException("Empty Forest");
        Objects.requireNonNull(unknownDecision, "unknownDecision");
        aliases = Map.copyOf(aliases);
        aliases.keySet().forEach(Checks::name);
        decisions = Set.copyOf(decisions);
        components = List.copyOf(components);
        Map<UUID, Object> ids = new HashMap<>();
        ids.put(id, Forest.class);
        for (Tree tree : trees) {
            if (ids.putIfAbsent(tree.id(), tree) != null) throw new IllegalArgumentException("Duplicate identity");
            validate(tree.condition(), ids, aliases);
            if (tree.terminal() != null && !decisions.contains(tree.terminal()))
                throw new IllegalArgumentException("Unknown terminal decision");
        }
        Set<UUID> componentIds = new HashSet<>();
        for (ComponentRevision component : components) {
            if (!componentIds.add(component.id()) || !component.definition().equals(ids.get(component.id())) || component.id().equals(id))
                throw new IllegalArgumentException("Invalid component reference");
        }
        if (!decisions.contains(unknownDecision)) throw new IllegalArgumentException("Unknown risk decision");
        ArrayList<Threshold> sorted = new ArrayList<>(thresholds);
        sorted.sort(Comparator.comparing(Threshold::lower, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(range -> !range.lowerInclusive()));
        if (sorted.isEmpty() || sorted.getFirst().lower() != null || sorted.getLast().upper() != null)
            throw new IllegalArgumentException("Thresholds must cover all decimal scores");
        for (int i = 0; i < sorted.size(); i++) {
            Threshold current = sorted.get(i);
            if (!decisions.contains(current.decision())) throw new IllegalArgumentException("Unknown threshold decision");
            if (i > 0) {
                Threshold prior = sorted.get(i - 1);
                if (prior.upper() == null || current.lower() == null
                        || prior.upper().compareTo(current.lower()) != 0
                        || prior.upperInclusive() == current.lowerInclusive())
                    throw new IllegalArgumentException("Threshold gap or overlap");
            }
        }
        thresholds = List.copyOf(sorted);
    }

    public Forest(UUID id, long revision, List<Tree> trees, List<Threshold> thresholds,
                  Decision unknownDecision, Map<String, AttributePath> aliases) {
        this(id, revision, trees, thresholds, unknownDecision, aliases,
                catalog(trees, thresholds, unknownDecision), List.of());
    }

    private static Set<Decision> catalog(List<Tree> trees, List<Threshold> thresholds, Decision risk) {
        Set<Decision> catalog = new HashSet<>(Set.of(Decision.ALLOW, Decision.REVIEW, Decision.BLOCK));
        catalog.add(Objects.requireNonNull(risk));
        trees.stream().map(Tree::terminal).filter(Objects::nonNull).forEach(catalog::add);
        thresholds.stream().map(Threshold::decision).forEach(catalog::add);
        return catalog;
    }

    private static void validate(Condition condition, Map<UUID, Object> ids, Map<String, AttributePath> aliases) {
        Object prior = ids.putIfAbsent(condition.id(), condition);
        if (prior != null) {
            if (!condition.equals(prior)) throw new IllegalArgumentException("Conflicting condition identity");
            return;
        }
        switch (condition) {
            case Branch branch -> branch.conditions().forEach(child -> validate(child, ids, aliases));
            case Leaf leaf -> {
                for (Operand operand : leaf.operands()) {
                    if (operand instanceof Operand.Alias alias && !aliases.containsKey(alias.name()))
                        throw new IllegalArgumentException("Unresolved alias: " + alias.name());
                }
            }
        }
    }
}
