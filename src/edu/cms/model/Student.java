package edu.cms.model;

import java.util.Objects;

public class Student {
    private final String id;   // unique
    private String name;

    public Student(String id, String name) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Student id required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Student name required");
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override public String toString() { return id + " — " + name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return id.equalsIgnoreCase(s.id);
    }
    @Override public int hashCode() { return Objects.hash(id.toLowerCase()); }
}
