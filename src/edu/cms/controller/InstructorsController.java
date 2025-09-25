package edu.cms.controller;

import edu.cms.model.DataStore;
import edu.cms.model.Instructor;
import edu.cms.util.AlgorithmUtil;
import edu.cms.view.InstructorsPanel;

import javax.swing.*;
import java.util.ArrayList;

public class InstructorsController {
    private final InstructorsPanel view;
    private final DataStore store = DataStore.get();

    public InstructorsController(InstructorsPanel view) {
        this.view = view;
        refresh();
        view.getAddBtn().addActionListener(e -> onAdd());
    }

    private void onAdd() {
        String id = view.getInstructorIdInput();
        String name = view.getInstructorNameInput();
        if (id.isBlank() || name.isBlank()) {
            JOptionPane.showMessageDialog(null, "Both ID and Name are required", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok = store.addInstructor(new Instructor(id, name));
        if (!ok) {
            JOptionPane.showMessageDialog(null, "Instructor ID already exists", "Duplicate", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view.clearForm();
        refresh();
    }

    private void refresh() {
        var list = new ArrayList<>(store.getInstructors());
        AlgorithmUtil.bubbleSortInstructorsByName(list);
        view.refreshTable(list);
    }
}
