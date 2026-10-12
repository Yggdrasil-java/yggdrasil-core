package dev.yggdrasil.core.domain;

import java.util.Objects;

final class Checks {
    private Checks() {}

    static String name(String value) {
        Objects.requireNonNull(value, "name");
        if (value.isBlank()) throw new IllegalArgumentException("Name must not be blank");
        return value;
    }
}
