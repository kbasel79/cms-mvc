package edu.cms.model;

import java.util.Objects;

public class Instructor {
    private final String id;   // unique
    private String name;

    public Instructor(String id, String name) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Instructor id required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Instructor name required");
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override public String toString() { return id + " — " + name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instructor)) return false;
        Instructor i = (Instructor) o;
        return id.equalsIgnoreCase(i.id);
    }
    @Override public int hashCode() { return Objects.hash(id.toLowerCase()); }
}
