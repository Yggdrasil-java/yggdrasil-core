package dev.yggdrasil.core.application;

import dev.yggdrasil.core.domain.internal.Comparisons;

import dev.yggdrasil.core.domain.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static dev.yggdrasil.core.domain.Explanation.Diagnostic;

import static dev.yggdrasil.core.domain.Explanation.Kind;
import static dev.yggdrasil.core.domain.Explanation.Outcome;
import static dev.yggdrasil.core.domain.EvaluationResult.Reason;
import static dev.yggdrasil.core.domain.EvaluationResult.State;

/** Stateless, synchronous evaluator. Safe to share between concurrent host calls. */
public final class Evaluator {
    public Evaluator() {}

    public EvaluationResult evaluate(Forest forest, EvaluationInput input) {
        return evaluate(forest, input, EvaluationOptions.DEFAULT);
    }

    public EvaluationResult evaluate(Forest forest, EvaluationInput input, EvaluationOptions options) {
        Objects.requireNonNull(forest, "forest");
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(options, "options");
        Set<UUID> validated = new HashSet<>();
        for (Tree tree : forest.trees()) validateTemporal(tree.condition(), forest, input, validated);
        BigDecimal score = BigDecimal.ZERO;
        boolean unknown = false;
        Decision terminal = null;
        List<Explanation> trace = new ArrayList<>();
        for (Tree tree : forest.trees()) {
            if (terminal != null) {
                trace.add(new Explanation(tree.id(), Kind.TREE, null, Outcome.NOT_EVALUATED,
                        null, List.of(skipped(tree.condition()))));
                continue;
            }
            Node evaluated = evaluate(tree.condition(), forest, input, options);
            BigDecimal contribution = evaluated.truth() == Truth.UNKNOWN ? null : BigDecimal.ZERO;
            if (evaluated.truth() == Truth.TRUE) {
                if (tree.contribution() != null) contribution = tree.contribution();
                score = score.add(contribution);
                terminal = tree.terminal();
            }
            unknown |= evaluated.truth() == Truth.UNKNOWN;
            trace.add(new Explanation(tree.id(), Kind.TREE, null, outcome(evaluated.truth()),
                    contribution, evaluated.truth() == Truth.TRUE ? tree.terminal() : null, List.of(evaluated.explanation())));
        }
        if (terminal != null)
            return result(forest, State.DETERMINATE, terminal, score, true, Reason.TERMINAL, trace);
        if (unknown)
            return result(forest, State.UNKNOWN, forest.unknownDecision(), score,
                    true, Reason.UNKNOWN_POLICY, trace);
        BigDecimal total = score;
        Decision decision = forest.thresholds().stream().filter(range -> range.contains(total))
                .findFirst().orElseThrow().decision();
        return result(forest, State.DETERMINATE, decision, score, false, Reason.THRESHOLD, trace);
    }

    private EvaluationResult result(Forest forest, State state, Decision decision, BigDecimal score,
                                    boolean partial, Reason reason, List<Explanation> trace) {
        return new EvaluationResult(forest.id(), forest.revision(), state, decision, score, partial, reason, trace);
    }

    private Node evaluate(Condition condition, Forest forest, EvaluationInput input, EvaluationOptions options) {
        if (condition instanceof Leaf leaf) {
            List<Value> values = leaf.operands().stream().map(operand -> resolve(operand, forest, input)).toList();
            Truth truth = evaluate(leaf, values);
            return new Node(truth, new Explanation(leaf.id(), Kind.LEAF, leaf.operator(), outcome(truth),
                    null, null, diagnostic(leaf, values, truth), observations(values, options), List.of()));
        }
        Branch branch = (Branch) condition;
        List<Explanation> children = new ArrayList<>();
        boolean unknown = false;
        Truth decisive = null;
        for (Condition child : branch.conditions()) {
            if (decisive != null) {
                children.add(skipped(child));
                continue;
            }
            Node node = evaluate(child, forest, input, options);
            children.add(node.explanation());
            unknown |= node.truth() == Truth.UNKNOWN;
            if (branch.composition() == Composition.NOT) decisive = node.truth().negate();
            if (branch.composition() == Composition.ALL && node.truth() == Truth.FALSE) decisive = Truth.FALSE;
            if (branch.composition() == Composition.ANY && node.truth() == Truth.TRUE) decisive = Truth.TRUE;
        }
        Truth truth = decisive != null ? decisive : unknown ? Truth.UNKNOWN
                : branch.composition() == Composition.ALL ? Truth.TRUE : Truth.FALSE;
        return new Node(truth, new Explanation(branch.id(), Kind.BRANCH, null, outcome(truth), null, children));
    }

    private Explanation skipped(Condition condition) {
        return switch (condition) {
            case Leaf leaf -> new Explanation(leaf.id(), Kind.LEAF, leaf.operator(), Outcome.NOT_EVALUATED, null, List.of());
            case Branch branch -> new Explanation(branch.id(), Kind.BRANCH, null, Outcome.NOT_EVALUATED,
                    null, branch.conditions().stream().map(this::skipped).toList());
        };
    }

    private Outcome outcome(Truth truth) { return Outcome.valueOf(truth.name()); }

    private Truth evaluate(Leaf leaf, List<Value> values) {
        Value left = values.getFirst();
        if (left.status() == Status.INVALID) return Truth.UNKNOWN;
        if (leaf.operator() == Operator.EXISTS) return Comparisons.truth(left.status() == Status.PRESENT);
        if (leaf.operator() == Operator.IS_MISSING) return Comparisons.truth(left.status() == Status.MISSING);
        if (leaf.operator() == Operator.IS_NULL)
            return Comparisons.truth(left.status() == Status.PRESENT && left.value() == null);
        if (values.stream().anyMatch(value -> value.status() != Status.PRESENT)) return Truth.UNKNOWN;
        Object a = left.value();
        if (leaf.operator() == Operator.IS_EMPTY) {
            if (a instanceof String text) return Comparisons.truth(text.isEmpty());
            if (a instanceof List<?> list) return Comparisons.truth(list.isEmpty());
            if (a instanceof Map<?, ?> map) return Comparisons.truth(map.isEmpty());
            return Truth.UNKNOWN;
        }
        Object b = values.get(1).value();
        if (leaf.operator() == Operator.EQUAL || leaf.operator() == Operator.NOT_EQUAL) {
            Truth equal = Comparisons.equal(a, b, leaf.ignoreCase());
            return leaf.operator() == Operator.NOT_EQUAL ? equal.negate() : equal;
        }
        if (a == null || b == null) return Truth.UNKNOWN;
        return switch (leaf.operator()) {
            case GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL ->
                    a instanceof BigDecimal && b instanceof BigDecimal ? ordered(a, b, leaf.operator()) : Truth.UNKNOWN;
            case BEFORE, AFTER -> (a instanceof Instant && b instanceof Instant)
                    || (a instanceof LocalDate && b instanceof LocalDate) ? ordered(a, b, leaf.operator()) : Truth.UNKNOWN;
            case BETWEEN -> between(a, b, values.get(2).value(), leaf);
            case SAME_DATE -> sameDate(a, b, leaf);
            case CONTAINS, STARTS_WITH, ENDS_WITH -> text(a, b, leaf);
            case IN -> b instanceof List<?> list ? Comparisons.member(a, list) : Truth.UNKNOWN;
            case CONTAINS_ANY, CONTAINS_ALL -> a instanceof List<?> first && b instanceof List<?> second
                    ? Comparisons.collection(first, second, leaf.operator() == Operator.CONTAINS_ALL) : Truth.UNKNOWN;
            default -> throw new IllegalStateException("Unhandled operator: " + leaf.operator());
        };
    }

    private void validateTemporal(Condition condition, Forest forest, EvaluationInput input, Set<UUID> validated) {
        if (!validated.add(condition.id())) return;
        if (condition instanceof Branch branch) {
            for (Condition child : branch.conditions()) validateTemporal(child, forest, input, validated);
        } else {
            Leaf leaf = (Leaf) condition;
            if (leaf.operator() == Operator.SAME_DATE && leaf.zone() == null) {
                Value first = resolve(leaf.operands().getFirst(), forest, input);
                Value second = resolve(leaf.operands().get(1), forest, input);
                if (first.value() instanceof Instant && second.value() instanceof Instant)
                    throw new InvalidRuleException(leaf.id(), "SAME_DATE on instants requires an explicit zone");
            }
        }
    }

    private List<OperandObservation> observations(List<Value> values, EvaluationOptions options) {
        if (!options.includeValues()) return List.of();
        List<OperandObservation> captured = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Value value = values.get(i);
            OperandObservation.Status status = switch (value.status()) {
                case MISSING -> OperandObservation.Status.MISSING;
                case INVALID -> OperandObservation.Status.INVALID_NAVIGATION;
                case PRESENT -> value.value() == null ? OperandObservation.Status.PRESENT_NULL : OperandObservation.Status.PRESENT;
            };
            captured.add(new OperandObservation(i, status, value.value()));
        }
        return List.copyOf(captured);
    }

    private Diagnostic diagnostic(Leaf leaf, List<Value> values, Truth truth) {
        if (truth != Truth.UNKNOWN) return Diagnostic.NONE;
        if (values.getFirst().status() == Status.INVALID) return Diagnostic.INVALID_NAVIGATION;
        if (values.stream().anyMatch(value -> value.status() == Status.MISSING)) return Diagnostic.MISSING_ATTRIBUTE;
        if (values.stream().anyMatch(value -> value.status() == Status.INVALID)) return Diagnostic.INVALID_NAVIGATION;
        if (values.stream().anyMatch(value -> value.value() == null)) return Diagnostic.NULL_OPERAND;
        if (leaf.operator() == Operator.BETWEEN) {
            Integer bounds = Comparisons.order(values.get(1).value(), values.get(2).value());
            if (bounds != null && bounds > 0) return Diagnostic.INVALID_BOUNDS;
        }
        if (leaf.operator() == Operator.SAME_DATE && values.getFirst().value() instanceof Instant
                && values.get(1).value() instanceof Instant && leaf.zone() != null)
            return Diagnostic.TEMPORAL_OUT_OF_RANGE;
        return Diagnostic.TYPE_MISMATCH;
    }

    private Truth ordered(Object a, Object b, Operator operator) {
        Integer order = Comparisons.order(a, b);
        if (order == null) return Truth.UNKNOWN;
        return Comparisons.truth(switch (operator) {
            case GREATER_THAN, AFTER -> order > 0;
            case GREATER_THAN_OR_EQUAL -> order >= 0;
            case LESS_THAN, BEFORE -> order < 0;
            case LESS_THAN_OR_EQUAL -> order <= 0;
            default -> throw new IllegalArgumentException("Not an ordered operator");
        });
    }

    private Truth between(Object value, Object lower, Object upper, Leaf leaf) {
        Integer bounds = Comparisons.order(lower, upper);
        Integer low = Comparisons.order(value, lower);
        Integer high = Comparisons.order(value, upper);
        if (bounds == null || bounds > 0 || low == null || high == null) return Truth.UNKNOWN;
        return Comparisons.truth((low > 0 || (low == 0 && leaf.lowerInclusive()))
                && (high < 0 || (high == 0 && leaf.upperInclusive())));
    }

    private Truth sameDate(Object a, Object b, Leaf leaf) {
        if (a instanceof LocalDate first && b instanceof LocalDate second) return Comparisons.truth(first.equals(second));
        if (a instanceof Instant first && b instanceof Instant second && leaf.zone() != null) {
            try {
                return Comparisons.truth(first.atZone(leaf.zone()).toLocalDate().equals(second.atZone(leaf.zone()).toLocalDate()));
            } catch (DateTimeException outsideCalendarRange) {
                return Truth.UNKNOWN;
            }
        }
        return Truth.UNKNOWN;
    }

    private Truth text(Object a, Object b, Leaf leaf) {
        if (!(a instanceof String first) || !(b instanceof String second)) return Truth.UNKNOWN;
        return Comparisons.truth(switch (leaf.operator()) {
            case CONTAINS -> Comparisons.contains(first, second, leaf.ignoreCase());
            case STARTS_WITH -> Comparisons.region(first, 0, second, leaf.ignoreCase());
            case ENDS_WITH -> Comparisons.region(first, first.length() - second.length(), second, leaf.ignoreCase());
            default -> throw new IllegalArgumentException("Not a text operator");
        });
    }

    private Value resolve(Operand operand, Forest forest, EvaluationInput input) {
        return switch (operand) {
            case Operand.Literal literal -> new Value(Status.PRESENT, literal.value());
            case Operand.Attribute attribute -> navigate(attribute.path(), input.attributes());
            case Operand.Alias alias -> navigate(forest.aliases().get(alias.name()), input.attributes());
        };
    }

    private Value navigate(AttributePath path, Map<String, Object> attributes) {
        Object current = attributes;
        for (AttributePath.Segment segment : path.segments()) {
            if (segment instanceof AttributePath.Key key) {
                if (!(current instanceof Map<?, ?> map)) return new Value(Status.INVALID, null);
                if (!map.containsKey(key.value())) return new Value(Status.MISSING, null);
                current = map.get(key.value());
            } else {
                AttributePath.Index index = (AttributePath.Index) segment;
                if (!(current instanceof List<?> list)) return new Value(Status.INVALID, null);
                if (index.value() >= list.size()) return new Value(Status.MISSING, null);
                current = list.get(index.value());
            }
        }
        return new Value(Status.PRESENT, current);
    }

    private enum Status { PRESENT, MISSING, INVALID }
    private record Value(Status status, Object value) {}
    private record Node(Truth truth, Explanation explanation) {}
}
