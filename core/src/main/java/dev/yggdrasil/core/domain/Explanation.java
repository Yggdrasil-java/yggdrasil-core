package dev.yggdrasil.core.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/** Ordered trace. Operand values are omitted unless investigation is explicitly enabled. */
public record Explanation(UUID id, Kind kind, Operator operator, Outcome outcome,
                          BigDecimal contribution, Decision terminalDecision, Diagnostic diagnostic,
                          List<OperandObservation> operands, List<Explanation> children) {
    public enum Kind { TREE, BRANCH, LEAF }
    public enum Outcome { TRUE, FALSE, UNKNOWN, NOT_EVALUATED, CANCELLED }
    public enum Diagnostic {
        NONE, MISSING_ATTRIBUTE, INVALID_NAVIGATION, NULL_OPERAND,
        TYPE_MISMATCH, INVALID_BOUNDS, TEMPORAL_OUT_OF_RANGE
    }

    public Explanation {
        java.util.Objects.requireNonNull(diagnostic, "diagnostic");
        operands = List.copyOf(operands);
        children = List.copyOf(children);
    }

    public Explanation(UUID id, Kind kind, Operator operator, Outcome outcome,
                       BigDecimal contribution, Decision terminalDecision, List<Explanation> children) {
        this(id, kind, operator, outcome, contribution, terminalDecision, Diagnostic.NONE, List.of(), children);
    }

    public Explanation(UUID id, Kind kind, Operator operator, Outcome outcome,
                       BigDecimal contribution, List<Explanation> children) {
        this(id, kind, operator, outcome, contribution, null, children);
    }
}
