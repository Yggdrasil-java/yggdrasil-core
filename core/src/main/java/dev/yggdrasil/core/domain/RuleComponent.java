package dev.yggdrasil.core.domain;

import java.util.UUID;

/** A publishable immutable rule definition. */
public sealed interface RuleComponent permits Tree, Condition {
    UUID id();
}
