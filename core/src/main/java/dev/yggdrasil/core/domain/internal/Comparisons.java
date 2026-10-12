package dev.yggdrasil.core.domain.internal;

import dev.yggdrasil.core.domain.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/** Internal comparison policies shared by rule validation and evaluation. */
public final class Comparisons {
    private Comparisons() {}

    public static Truth truth(boolean value) { return value ? Truth.TRUE : Truth.FALSE; }

    public static Integer order(Object left, Object right) {
        if (left instanceof BigDecimal a && right instanceof BigDecimal b) return a.compareTo(b);
        if (left instanceof Instant a && right instanceof Instant b) return a.compareTo(b);
        if (left instanceof LocalDate a && right instanceof LocalDate b) return a.compareTo(b);
        return null;
    }

    public static Truth equal(Object left, Object right, boolean ignoreCase) {
        if (left == null || right == null) return truth(left == right);
        if (left instanceof BigDecimal a && right instanceof BigDecimal b) return truth(a.compareTo(b) == 0);
        if (left instanceof String a && right instanceof String b)
            return truth(ignoreCase ? a.equalsIgnoreCase(b) : a.equals(b));
        if ((left instanceof Boolean && right instanceof Boolean)
                || (left instanceof Instant && right instanceof Instant)
                || (left instanceof LocalDate && right instanceof LocalDate)) return truth(left.equals(right));
        if (left instanceof List<?> a && right instanceof List<?> b) {
            if (a.size() != b.size()) return Truth.FALSE;
            boolean unknown = false;
            for (int i = 0; i < a.size(); i++) {
                Truth result = equal(a.get(i), b.get(i), ignoreCase);
                if (result == Truth.FALSE) return Truth.FALSE;
                unknown |= result == Truth.UNKNOWN;
            }
            return unknown ? Truth.UNKNOWN : Truth.TRUE;
        }
        if (left instanceof Map<?, ?> a && right instanceof Map<?, ?> b) {
            if (!a.keySet().equals(b.keySet())) return Truth.FALSE;
            boolean unknown = false;
            for (Object key : a.keySet()) {
                Truth result = equal(a.get(key), b.get(key), ignoreCase);
                if (result == Truth.FALSE) return Truth.FALSE;
                unknown |= result == Truth.UNKNOWN;
            }
            return unknown ? Truth.UNKNOWN : Truth.TRUE;
        }
        return Truth.UNKNOWN;
    }

    public static Truth member(Object needle, List<?> haystack) {
        boolean unknown = false;
        for (Object item : haystack) {
            Truth match = equal(needle, item, false);
            if (match == Truth.TRUE) return Truth.TRUE;
            unknown |= match == Truth.UNKNOWN;
        }
        return unknown ? Truth.UNKNOWN : Truth.FALSE;
    }

    public static Truth collection(List<?> left, List<?> right, boolean all) {
        boolean unknown = false;
        for (Object needle : right) {
            Truth result = member(needle, left);
            if (all && result == Truth.FALSE) return Truth.FALSE;
            if (!all && result == Truth.TRUE) return Truth.TRUE;
            unknown |= result == Truth.UNKNOWN;
        }
        return unknown ? Truth.UNKNOWN : truth(all);
    }

    public static boolean region(String text, int offset, String query, boolean ignoreCase) {
        return text.regionMatches(ignoreCase, offset, query, 0, query.length());
    }

    public static boolean contains(String text, String query, boolean ignoreCase) {
        for (int i = 0; i <= text.length() - query.length(); i++)
            if (region(text, i, query, ignoreCase)) return true;
        return false;
    }
}
