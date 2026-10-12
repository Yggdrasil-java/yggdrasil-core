package dev.yggdrasil.core.domain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record Branch(UUID id, Composition composition, List<Condition> conditions) implements Condition {
    public Branch {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(composition, "composition");
        conditions = List.copyOf(conditions);
        if (conditions.isEmpty() || (composition == Composition.NOT && conditions.size() != 1))
            throw new IllegalArgumentException("Invalid branch arity");
    }
}
