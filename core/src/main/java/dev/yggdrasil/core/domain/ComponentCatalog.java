package dev.yggdrasil.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Host-supplied published snapshots. Does not fetch or mutate external versions. */
public final class ComponentCatalog {
    private record Key(UUID id, long revision) {}
    private final Map<Key, ComponentRevision> snapshots;

    public ComponentCatalog(List<ComponentRevision> components) {
        Map<Key, ComponentRevision> versions = new HashMap<>();
        for (ComponentRevision component : List.copyOf(components)) {
            ComponentRevision prior = versions.putIfAbsent(new Key(component.id(), component.revision()), component);
            if (prior != null && !prior.definition().equals(component.definition()))
                throw new IllegalArgumentException("Conflicting definitions for the same published revision");
        }
        snapshots = Map.copyOf(versions);
    }

    public ComponentRevision resolve(UUID id, long revision) {
        Objects.requireNonNull(id, "id");
        ComponentRevision component = snapshots.get(new Key(id, revision));
        if (component == null) throw new IllegalArgumentException("Unresolved component revision: " + id + ":" + revision);
        return component;
    }
}
