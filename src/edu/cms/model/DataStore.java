package edu.cms.model;

import java.io.*;
import java.util.*;

/** Singleton store with CSV persistence (project folder). */
public class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    private final List<Student> students = new ArrayList<>();
    private final List<Unit> units = new ArrayList<>();
    private final List<Instructor> instructors = new ArrayList<>();
    private final List<UnitOffering> offerings = new ArrayList<>();

    // CSV file names (created next to where you run App.java)
    private static final String STUDENTS_CSV    = "students.csv";
    private static final String UNITS_CSV       = "units.csv";
    private static final String INSTRUCTORS_CSV = "instructors.csv";
    private static final String OFFERINGS_CSV   = "offerings.csv";

    private DataStore() {}
    public static DataStore get() { return INSTANCE; }

    // ---------------- Public API ----------------
    public List<Student> getStudents()     { return Collections.unmodifiableList(students); }
    public List<Unit> getUnits()           { return Collections.unmodifiableList(units); }
    public List<Instructor> getInstructors(){ return Collections.unmodifiableList(instructors); }
    public List<UnitOffering> getOfferings(){ return Collections.unmodifiableList(offerings); }

    public boolean addStudent(Student s) {
        if (students.stream().anyMatch(x -> x.getId().equalsIgnoreCase(s.getId()))) return false;
        boolean ok = students.add(s);
        if (ok) saveStudents();
        return ok;
    }
    public boolean addUnit(Unit u) {
        if (units.stream().anyMatch(x -> x.getCode().equalsIgnoreCase(u.getCode()))) return false;
        boolean ok = units.add(u);
        if (ok) saveUnits();
        return ok;
    }
    public boolean addInstructor(Instructor i) {
        if (instructors.stream().anyMatch(x -> x.getId().equalsIgnoreCase(i.getId()))) return false;
        boolean ok = instructors.add(i);
        if (ok) saveInstructors();
        return ok;
    }
    public boolean addOffering(UnitOffering o) {
        if (offerings.stream().anyMatch(x -> x.getId().equalsIgnoreCase(o.getId()))) return false;
        boolean ok = offerings.add(o);
        if (ok) saveOfferings();
        return ok;
    }

    /** Call this after changing enrollments to persist offerings.csv */
    public void saveOfferings() { writeOfferings(); }

    /** Load all CSVs (safe to call at startup). */
    public void loadAll() {
        readStudents();
        readUnits();
        readInstructors();
        readOfferings(); // depends on the three above
    }

    // ---------------- Load/Save: Students ----------------
    private void readStudents() {
        students.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENTS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = splitCsv(line, 2);
                if (p != null) students.add(new Student(p[0], p[1]));
            }
        } catch (FileNotFoundException ignored) { /* no file yet */ }
          catch (IOException e) { e.printStackTrace(); }
    }
    private void saveStudents() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENTS_CSV))) {
            for (Student s : students) pw.println(s.getId()+","+escape(s.getName()));
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ---------------- Load/Save: Units ----------------
    private void readUnits() {
        units.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(UNITS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                // code,name,credits,description,prereq1|prereq2|...
                String[] p = splitCsv(line, 5);
                if (p != null) {
                    Unit u = new Unit(p[0], p[1], parseIntSafe(p[2], 0), p[3]);
                    if (p[4] != null && !p[4].isBlank()) {
                        for (String code : p[4].split("\\|")) u.addPrerequisite(code);
                    }
                    units.add(u);
                }
            }
        } catch (FileNotFoundException ignored) {}
          catch (IOException e) { e.printStackTrace(); }
    }
    private void saveUnits() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(UNITS_CSV))) {
            for (Unit u : units) {
                String prereq = String.join("|", u.getPrerequisites());
                pw.println(u.getCode()+","+escape(u.getName())+","+u.getCredits()+","+escape(u.getDescription())+","+prereq);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ---------------- Load/Save: Instructors ----------------
    private void readInstructors() {
        instructors.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(INSTRUCTORS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = splitCsv(line, 2);
                if (p != null) instructors.add(new Instructor(p[0], p[1]));
            }
        } catch (FileNotFoundException ignored) {}
          catch (IOException e) { e.printStackTrace(); }
    }
    private void saveInstructors() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(INSTRUCTORS_CSV))) {
            for (Instructor i : instructors) pw.println(i.getId()+","+escape(i.getName()));
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ---------------- Load/Save: Offerings ----------------
    private void readOfferings() {
        offerings.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(OFFERINGS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                // id,unitCode,term,instructorId,studentIds(pipe-delimited)
                String[] p = splitCsv(line, 5);
                if (p != null) {
                    String id = p[0];
                    Unit unit = findUnitByCode(p[1]);
                    if (unit == null) continue; // skip if unit missing
                    String term = p[2];
                    Instructor ins = findInstructorById(p[3]);
                    UnitOffering off = new UnitOffering(id, unit, term, ins);
                    if (p[4] != null && !p[4].isBlank()) {
                        for (String sid : p[4].split("\\|")) {
                            Student s = findStudentById(sid);
                            if (s != null) off.enrollStudent(s);
                        }
                    }
                    offerings.add(off);
                }
            }
        } catch (FileNotFoundException ignored) {}
          catch (IOException e) { e.printStackTrace(); }
    }
    private void writeOfferings() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(OFFERINGS_CSV))) {
            for (UnitOffering o : offerings) {
                String insId = (o.getInstructor() == null) ? "" : o.getInstructor().getId();
                String studentIds = String.join("|", o.getEnrolled().stream().map(Student::getId).toList());
                pw.println(o.getId()+","+o.getUnit().getCode()+","+escape(o.getTerm())+","+insId+","+studentIds);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ---------------- Helpers ----------------
    private Student findStudentById(String id) {
        for (Student s : students) if (s.getId().equalsIgnoreCase(id)) return s;
        return null;
    }
    private Unit findUnitByCode(String code) {
        for (Unit u : units) if (u.getCode().equalsIgnoreCase(code)) return u;
        return null;
    }
    private Instructor findInstructorById(String id) {
        for (Instructor i : instructors) if (i.getId().equalsIgnoreCase(id)) return i;
        return null;
    }

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    private static String escape(String s) {
        if (s == null) return "";
        // simple CSV escape for commas/newlines/quotes
        if (s.contains(",") || s.contains("\n") || s.contains("\"")) {
            s = s.replace("\"","\"\"");
            return "\"" + s + "\"";
        }
        return s;
    }

    /** Split a CSV line into up to 'n' fields (last field may contain commas). */
    private static String[] splitCsv(String line, int n) {
        if (line == null) return null;
        List<String> out = new ArrayList<>(n);
        boolean inQuotes = false;
        StringBuilder cur = new StringBuilder();
        int fields = 0;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '\"') {
                inQuotes = !inQuotes;
                // handle escaped quotes ("")
                if (inQuotes && i+1 < line.length() && line.charAt(i+1) == '\"') {
                    cur.append('\"'); i++; inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes && fields < n-1) {
                out.add(cur.toString()); cur.setLength(0); fields++;
            } else {
                cur.append(ch);
            }
        }
        out.add(cur.toString());
        while (out.size() < n) out.add("");
        return out.toArray(new String[0]);
    }
}
