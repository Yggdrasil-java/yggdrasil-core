package dev.yggdrasil.core.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class Values {
    private Values() {}

    static Object freeze(Object value) {
        return freeze(value, new IdentityHashMap<>());
    }

    private static Object freeze(Object value, IdentityHashMap<Object, Boolean> active) {
        if (value == null || value instanceof String || value instanceof Boolean
                || value instanceof Instant || value instanceof LocalDate || value instanceof BigDecimal) return value;
        if (value instanceof BigInteger n) return new BigDecimal(n);
        if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)
            return BigDecimal.valueOf(((Number) value).longValue());
        if (value instanceof Float || value instanceof Double) {
            double number = ((Number) value).doubleValue();
            if (!Double.isFinite(number)) throw new IllegalArgumentException("Non-finite number");
            return new BigDecimal(value.toString());
        }
        if (active.put(value, Boolean.TRUE) != null) throw new IllegalArgumentException("Cyclic input");
        try {
            if (value instanceof Map<?, ?> map) {
                Map<String, Object> copy = new LinkedHashMap<>();
                for (var entry : map.entrySet()) {
                    if (!(entry.getKey() instanceof String key)) throw new IllegalArgumentException("Object keys must be strings");
                    copy.put(key, freeze(entry.getValue(), active));
                }
                return Collections.unmodifiableMap(copy);
            }
            if (value instanceof List<?> list) {
                List<Object> copy = new ArrayList<>();
                for (Object item : list) copy.add(freeze(item, active));
                return Collections.unmodifiableList(copy);
            }
            throw new IllegalArgumentException("Unsupported attribute type: " + value.getClass().getName());
        } finally {
            active.remove(value);
        }
    }
}
