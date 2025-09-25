package edu.cms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** A specific class of a Unit in a given term, optionally taught by an Instructor. */
public class UnitOffering {
    private final String id;        // e.g. COS100-S1-2025 (must be unique)
    private final Unit unit;
    private final String term;      // e.g. "S1 2025" or "T2 2025"
    private Instructor instructor;  // can be null (TBA)
    private final List<Student> enrolled = new ArrayList<>(); // used later

    public UnitOffering(String id, Unit unit, String term, Instructor instructor) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Offering id required");
        if (unit == null) throw new IllegalArgumentException("Unit required");
        if (term == null || term.isBlank()) throw new IllegalArgumentException("Term required");
        this.id = id.trim();
        this.unit = unit;
        this.term = term.trim();
        this.instructor = instructor; // may be null
    }

    public String getId() { return id; }
    public Unit getUnit() { return unit; }
    public String getTerm() { return term; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    // Enrollment helpers (we'll use these in the next step)
    public List<Student> getEnrolled() { return Collections.unmodifiableList(enrolled); }
    public boolean enrollStudent(Student s) {
        if (s == null) return false;
        boolean exists = enrolled.stream().anyMatch(x -> x.getId().equalsIgnoreCase(s.getId()));
        if (exists) return false;
        return enrolled.add(s);
    }
    public boolean unenrollStudent(String studentId) {
        return enrolled.removeIf(s -> s.getId().equalsIgnoreCase(studentId));
    }

    @Override public String toString() { return id + " — " + unit.getCode() + " (" + term + ")"; }
    @Override public boolean equals(Object o) {
        return (this == o) || (o instanceof UnitOffering && id.equalsIgnoreCase(((UnitOffering)o).id));
    }
    @Override public int hashCode() { return Objects.hash(id.toLowerCase()); }
}
