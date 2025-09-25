package edu.cms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Unit {
    private final String code; // unique
    private String name;
    private int credits;
    private String description;
    private final List<String> prerequisites = new ArrayList<>(); // store codes

    public Unit(String code, String name, int credits, String description) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Unit code required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Unit name required");
        if (credits < 0) throw new IllegalArgumentException("Credits cannot be negative");
        this.code = code.trim();
        this.name = name.trim();
        this.credits = credits;
        this.description = description == null ? "" : description.trim();
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getPrerequisites() { return prerequisites; }
    public void addPrerequisite(String unitCode) {
        if (unitCode != null && !unitCode.isBlank() && !prerequisites.contains(unitCode)) {
            prerequisites.add(unitCode.trim());
        }
    }

    @Override public String toString() { return code + " — " + name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        Unit u = (Unit) o;
        return code.equalsIgnoreCase(u.code);
    }
    @Override public int hashCode() { return Objects.hash(code.toLowerCase()); }
}
