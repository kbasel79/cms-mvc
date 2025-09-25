package edu.cms.controller;

import edu.cms.model.DataStore;
import edu.cms.model.Student;
import edu.cms.model.UnitOffering;
import edu.cms.util.AlgorithmUtil;
import edu.cms.view.ReportsPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ReportsController {
    private final ReportsPanel view;
    private final DataStore store = DataStore.get();

    public ReportsController(ReportsPanel view) {
        this.view = view;
        bind();
    }

    private void bind() {
        view.getGenEnrollmentsBtn().addActionListener(e -> generateStudentEnrollments());
        view.getGenOfferingsBtn().addActionListener(e -> generateOfferingsCounts());
        view.getSearchBtn().addActionListener(e -> searchStudentById()); // NEW
    }

    private void generateStudentEnrollments() {
        StringBuilder sb = new StringBuilder();
        List<Student> students = new ArrayList<>(store.getStudents());
        AlgorithmUtil.bubbleSortStudentsByName(students);

        if (students.isEmpty()) { view.setReport("No students.\n"); return; }

        for (Student s : students) {
            sb.append("Student: ").append(s.getName()).append(" (ID ").append(s.getId()).append(")\n");
            boolean any = false;
            for (UnitOffering o : store.getOfferings()) {
                boolean enrolled = o.getEnrolled().stream().anyMatch(x -> x.getId().equalsIgnoreCase(s.getId()));
                if (enrolled) {
                    any = true;
                    String ins = (o.getInstructor() == null) ? "TBA" : o.getInstructor().getName();
                    sb.append("  - ").append(o.getUnit().getCode()).append("  ").append(o.getTerm())
                      .append("  [Instructor: ").append(ins).append("]\n");
                }
            }
            if (!any) sb.append("  - (none)\n");
            sb.append('\n');
        }
        view.setReport(sb.toString());
    }

    private void generateOfferingsCounts() {
        StringBuilder sb = new StringBuilder();
        List<UnitOffering> offs = new ArrayList<>(store.getOfferings());
        AlgorithmUtil.bubbleSortOfferingsById(offs);

        if (offs.isEmpty()) { view.setReport("No offerings.\n"); return; }

        for (UnitOffering o : offs) {
            String ins = (o.getInstructor() == null) ? "TBA" : o.getInstructor().getName();
            sb.append(o.getId()).append("  ").append(o.getUnit().getCode()).append("  ").append(o.getTerm())
              .append("  [Instructor: ").append(ins).append("]  [Students: ").append(o.getEnrolled().size()).append("]\n");
        }
        view.setReport(sb.toString());
    }

    // ----------- NEW FEATURE: Binary Search -----------
    private void searchStudentById() {
        String id = view.getSearchIdInput();
        if (id.isBlank()) {
            JOptionPane.showMessageDialog(null, "Enter a Student ID to search",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // sort by ID before searching
        List<Student> students = new ArrayList<>(store.getStudents());
        AlgorithmUtil.bubbleSortStudentsById(students);

        int idx = AlgorithmUtil.binarySearchStudentById(students, id);
        if (idx == -1) {
            view.setReport("No student found with ID: " + id);
        } else {
            Student s = students.get(idx);
            view.setReport("Found student:\nID: " + s.getId() + "\nName: " + s.getName());
        }
    }
}
