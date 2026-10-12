package dev.yggdrasil.core.domain;

import java.util.Map;
import java.util.Objects;

/** Deeply immutable attributes supplied by the host; missing keys are preserved. */
public record EvaluationInput(Map<String, Object> attributes) {
    @SuppressWarnings("unchecked")
    public EvaluationInput {
        Objects.requireNonNull(attributes, "attributes");
        attributes = (Map<String, Object>) Values.freeze(attributes);
    }
}
