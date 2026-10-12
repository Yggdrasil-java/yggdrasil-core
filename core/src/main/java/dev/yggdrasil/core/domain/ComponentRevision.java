package dev.yggdrasil.core.domain;

import java.util.Objects;
import java.util.UUID;

/** An immutable published definition bound to an identity and positive revision. */
public record ComponentRevision(UUID id, long revision, RuleComponent definition) {
    public ComponentRevision {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(definition, "definition");
        if (!id.equals(definition.id())) throw new IllegalArgumentException("Component identity does not match definition");
        if (revision <= 0) throw new IllegalArgumentException("Revision must be positive");
    }

    public ComponentRevision(long revision, RuleComponent definition) {
        this(definition.id(), revision, definition);
    }
}
