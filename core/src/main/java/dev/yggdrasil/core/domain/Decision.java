package dev.yggdrasil.core.domain;

/** Operational decision identifier; the host performs the corresponding action. */
public record Decision(String id) {
    public static final Decision ALLOW = new Decision("ALLOW");
    public static final Decision REVIEW = new Decision("REVIEW");
    public static final Decision BLOCK = new Decision("BLOCK");

    public Decision { id = Checks.name(id); }
}
