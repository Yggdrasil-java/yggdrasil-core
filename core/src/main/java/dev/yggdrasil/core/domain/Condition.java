package dev.yggdrasil.core.domain;

import java.util.UUID;

public sealed interface Condition extends RuleComponent permits Branch, Leaf {
    UUID id();
}
