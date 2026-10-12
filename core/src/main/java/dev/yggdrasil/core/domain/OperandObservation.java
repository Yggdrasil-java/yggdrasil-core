package dev.yggdrasil.core.domain;

/** Resolved operand captured only when the host explicitly enables investigation. */
public record OperandObservation(int position, Status status, Object value) {
    public enum Status { PRESENT, PRESENT_NULL, MISSING, INVALID_NAVIGATION }

    public OperandObservation {
        if (position < 0) throw new IllegalArgumentException("Negative operand position");
        java.util.Objects.requireNonNull(status, "status");
        value = Values.freeze(value);
        if (status != Status.PRESENT && value != null)
            throw new IllegalArgumentException("Non-present operand cannot carry a value");
    }
}
