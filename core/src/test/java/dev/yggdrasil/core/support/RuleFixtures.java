package dev.yggdrasil.core.support;

import dev.yggdrasil.core.domain.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Shared rule builders for contract tests. */
public final class RuleFixtures {
    private RuleFixtures() {}

    public static UUID id() { return UUID.randomUUID(); }
    public static BigDecimal decimal(String value) { return new BigDecimal(value); }
    public static Leaf leaf(Operator operator, Object... values) {
        return Leaf.of(id(), operator, Arrays.stream(values).map(Operand::literal).toArray(Operand[]::new));
    }
    public static Tree tree(Condition condition, String score, Decision terminal) {
        return new Tree(id(), "rule", condition, score == null ? null : decimal(score), terminal);
    }
    public static List<Threshold> thresholds() {
        return List.of(new Threshold(null, false, decimal("30"), false, Decision.ALLOW),
                new Threshold(decimal("30"), true, decimal("80"), false, Decision.REVIEW),
                new Threshold(decimal("80"), true, null, false, Decision.BLOCK));
    }
    public static Forest forest(List<Tree> trees) {
        return new Forest(id(), 1, trees, thresholds(), Decision.REVIEW, Map.of());
    }
}
