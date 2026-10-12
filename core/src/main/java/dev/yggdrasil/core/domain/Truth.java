package dev.yggdrasil.core.domain;

/** Three-valued condition outcome. */
public enum Truth {
    TRUE, FALSE, UNKNOWN;

    public Truth negate() {
        return switch (this) {
            case TRUE -> FALSE;
            case FALSE -> TRUE;
            case UNKNOWN -> UNKNOWN;
        };
    }
}
