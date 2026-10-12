package dev.yggdrasil.core.domain;

import java.util.List;
import java.util.Objects;

/** Explicit segments preserve keys containing dots and zero-based list indices. */
public record AttributePath(List<Segment> segments) {
    public sealed interface Segment permits Key, Index {}
    public record Key(String value) implements Segment {
        public Key { Objects.requireNonNull(value, "key"); }
    }
    public record Index(int value) implements Segment {
        public Index {
            if (value < 0) throw new IllegalArgumentException("Negative index");
        }
    }

    public AttributePath {
        segments = List.copyOf(segments);
        if (segments.isEmpty()) throw new IllegalArgumentException("Empty path");
    }

    public static AttributePath of(Segment... segments) {
        return new AttributePath(List.of(segments));
    }

    public static AttributePath keys(String... keys) {
        return new AttributePath(java.util.Arrays.stream(keys).<Segment>map(Key::new).toList());
    }
}
