package dev.yggdrasil.core.domain;

/** Value capture is explicit and scoped to one evaluation call. */
public record EvaluationOptions(boolean includeValues) {
    public static final EvaluationOptions DEFAULT = new EvaluationOptions(false);
    public static final EvaluationOptions INVESTIGATION = new EvaluationOptions(true);
}
