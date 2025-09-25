package edu.cms.controller;

import edu.cms.model.DataStore;
import edu.cms.model.Student;
import edu.cms.model.UnitOffering;
import edu.cms.view.EnrollmentPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentController {
    private final EnrollmentPanel view;
    private final DataStore store = DataStore.get();

    public EnrollmentController(EnrollmentPanel view) {
        this.view = view;

        refreshCombos();
        refreshTable();

        view.getEnrollBtn().addActionListener(e -> onEnroll());

        // Auto-refresh when the Enrollment tab is selected
        SwingUtilities.invokeLater(() -> {
            JTabbedPane tabs = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, view);
            if (tabs != null) {
                tabs.addChangeListener(new ChangeListener() {
                    @Override public void stateChanged(ChangeEvent e) {
                        if (tabs.getSelectedComponent() == view) {
                            refreshCombos();
                            refreshTable();
                        }
                    }
                });
            }
        });
    }

    private void refreshCombos() {
        view.getStudentCombo().removeAllItems();
        for (Student s : store.getStudents()) view.getStudentCombo().addItem(s);

        view.getOfferingCombo().removeAllItems();
        for (UnitOffering o : store.getOfferings()) view.getOfferingCombo().addItem(o);
    }

    private void onEnroll() {
        Student s = (Student) view.getStudentCombo().getSelectedItem();
        UnitOffering o = (UnitOffering) view.getOfferingCombo().getSelectedItem();
        if (s == null || o == null) {
            JOptionPane.showMessageDialog(null, "Select a Student and an Offering",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok = o.enrollStudent(s);
        if (!ok) {
            JOptionPane.showMessageDialog(null, "Student already enrolled in this offering",
                    "Duplicate", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Persist offerings.csv after enrollment
        store.saveOfferings();

        refreshTable();
    }

    private void refreshTable() {
        List<Object[]> rows = new ArrayList<>();
        for (UnitOffering o : store.getOfferings()) {
            for (Student s : o.getEnrolled()) {
                rows.add(new Object[]{ s.getId(), s.getName(), o.getId(), o.getUnit().getCode(), o.getTerm() });
            }
        }
        view.refreshTable(rows);
    }
}
