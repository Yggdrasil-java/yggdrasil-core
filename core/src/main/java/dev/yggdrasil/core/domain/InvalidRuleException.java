package dev.yggdrasil.core.domain;

import java.util.UUID;

/** Invalid host-dependent rule configuration detected before any Tree is evaluated. */
public final class InvalidRuleException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;
    private final UUID conditionId;

    public InvalidRuleException(UUID conditionId, String message) {
        super(message);
        this.conditionId = java.util.Objects.requireNonNull(conditionId, "conditionId");
    }

    public UUID conditionId() { return conditionId; }
}
