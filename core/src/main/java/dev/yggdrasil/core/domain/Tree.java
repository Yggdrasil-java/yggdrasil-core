package dev.yggdrasil.core.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/** One ordered rule; null contribution/terminal means that capability is absent. */
public record Tree(UUID id, String name, Condition condition, BigDecimal contribution, Decision terminal) implements RuleComponent {
    public Tree {
        Objects.requireNonNull(id, "id");
        name = Checks.name(name);
        Objects.requireNonNull(condition, "condition");
        if (contribution == null && terminal == null)
            throw new IllegalArgumentException("Tree requires score contribution or terminal decision");
    }
}
