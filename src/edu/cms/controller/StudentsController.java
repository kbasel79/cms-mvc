package edu.cms.controller;

import edu.cms.model.DataStore;
import edu.cms.model.Student;
import edu.cms.util.AlgorithmUtil;
import edu.cms.view.StudentsPanel;

import javax.swing.*;
import java.util.ArrayList;

public class StudentsController {
    private final StudentsPanel view;
    private final DataStore store = DataStore.get();

    public StudentsController(StudentsPanel view) {
        this.view = view;
        refresh();
        view.getAddBtn().addActionListener(e -> onAdd());
    }

    private void onAdd() {
        String id = view.getStudentIdInput();
        String name = view.getStudentNameInput();
        if (id.isBlank() || name.isBlank()) {
            JOptionPane.showMessageDialog(null, "Both ID and Name are required", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok = store.addStudent(new Student(id, name));
        if (!ok) {
            JOptionPane.showMessageDialog(null, "Student ID already exists", "Duplicate", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view.clearForm();
        refresh();
    }

    private void refresh() {
        var list = new ArrayList<>(store.getStudents());
        AlgorithmUtil.bubbleSortStudentsByName(list);
        view.refreshTable(list);
    }
}
