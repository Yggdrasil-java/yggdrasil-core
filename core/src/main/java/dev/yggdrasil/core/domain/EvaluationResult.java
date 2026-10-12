package dev.yggdrasil.core.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record EvaluationResult(UUID forestId, long revision, State state, Decision decision,
                               BigDecimal score, boolean partialScore, Reason reason,
                               List<Explanation> explanation) {
    public enum State { DETERMINATE, UNKNOWN }
    public enum Reason { THRESHOLD, TERMINAL, UNKNOWN_POLICY }

    public EvaluationResult { explanation = List.copyOf(explanation); }
}
