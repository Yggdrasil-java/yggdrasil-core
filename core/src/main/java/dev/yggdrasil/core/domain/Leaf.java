package dev.yggdrasil.core.domain;

import dev.yggdrasil.core.domain.internal.Comparisons;

import java.time.ZoneId;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Operands include the left value, followed by comparison values or BETWEEN bounds. */
public record Leaf(UUID id, Operator operator, List<Operand> operands, boolean ignoreCase,
                   boolean lowerInclusive, boolean upperInclusive, ZoneId zone) implements Condition {
    public Leaf {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(operator, "operator");
        operands = List.copyOf(operands);
        int arity = switch (operator) {
            case EXISTS, IS_MISSING, IS_NULL, IS_EMPTY -> 1;
            case BETWEEN -> 3;
            default -> 2;
        };
        if (operands.size() != arity) throw new IllegalArgumentException("Invalid operator arity");
        if (ignoreCase && operator != Operator.EQUAL && operator != Operator.NOT_EQUAL
                && operator != Operator.CONTAINS && operator != Operator.STARTS_WITH
                && operator != Operator.ENDS_WITH)
            throw new IllegalArgumentException("ignoreCase is only supported for text comparisons");
        if (zone != null && operator != Operator.SAME_DATE)
            throw new IllegalArgumentException("Zone only applies to SAME_DATE");
        if (operator == Operator.SAME_DATE && zone == null && operands.stream().anyMatch(
                operand -> operand instanceof Operand.Literal literal && literal.value() instanceof Instant))
            throw new IllegalArgumentException("SAME_DATE on instants requires a zone");
        if (operator == Operator.BETWEEN && operands.get(1) instanceof Operand.Literal lower
                && operands.get(2) instanceof Operand.Literal upper) {
            Integer comparison = Comparisons.order(lower.value(), upper.value());
            if (comparison == null || comparison > 0)
                throw new IllegalArgumentException("Invalid BETWEEN bounds");
        }
    }

    public Leaf(UUID id, Operator operator, List<Operand> operands) {
        this(id, operator, operands, false, true, true, null);
    }

    public static Leaf of(UUID id, Operator operator, Operand... operands) {
        return new Leaf(id, operator, List.of(operands));
    }
}
