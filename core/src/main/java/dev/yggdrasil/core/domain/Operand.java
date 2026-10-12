package dev.yggdrasil.core.domain;

import java.util.Objects;

public sealed interface Operand permits Operand.Literal, Operand.Attribute, Operand.Alias {
    record Literal(Object value) implements Operand {
        public Literal { value = Values.freeze(value); }
    }
    record Attribute(AttributePath path) implements Operand {
        public Attribute { Objects.requireNonNull(path, "path"); }
    }
    record Alias(String name) implements Operand {
        public Alias { name = Checks.name(name); }
    }

    static Operand literal(Object value) { return new Literal(value); }
    static Operand attribute(String... keys) { return new Attribute(AttributePath.keys(keys)); }
    static Operand attribute(AttributePath path) { return new Attribute(path); }
    static Operand alias(String name) { return new Alias(name); }
}
