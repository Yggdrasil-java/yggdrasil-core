package dev.yggdrasil.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

/** Null endpoints represent unbounded extremes. */
public record Threshold(BigDecimal lower, boolean lowerInclusive, BigDecimal upper,
                        boolean upperInclusive, Decision decision) {
    public Threshold {
        Objects.requireNonNull(decision, "decision");
        if (lower != null && upper != null) {
            int order = lower.compareTo(upper);
            if (order > 0 || (order == 0 && (!lowerInclusive || !upperInclusive)))
                throw new IllegalArgumentException("Empty or inverted threshold");
        }
    }

    public boolean contains(BigDecimal score) {
        int low = lower == null ? 1 : score.compareTo(lower);
        int high = upper == null ? -1 : score.compareTo(upper);
        return (low > 0 || (low == 0 && lowerInclusive))
                && (high < 0 || (high == 0 && upperInclusive));
    }
}
