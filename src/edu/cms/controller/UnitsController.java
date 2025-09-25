package edu.cms.controller;

import edu.cms.model.DataStore;
import edu.cms.model.Unit;
import edu.cms.util.AlgorithmUtil;
import edu.cms.view.UnitsPanel;

import javax.swing.*;
import java.util.ArrayList;

public class UnitsController {
    private final UnitsPanel view;
    private final DataStore store = DataStore.get();

    public UnitsController(UnitsPanel view) {
        this.view = view;
        refresh();
        view.getAddBtn().addActionListener(e -> onAdd());
    }

    private void onAdd() {
        String code = view.getUnitCodeInput();
        String name = view.getUnitNameInput();
        int credits = view.getUnitCreditsInput();
        if (code.isBlank() || name.isBlank()) {
            JOptionPane.showMessageDialog(null, "Code and Name are required", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok = store.addUnit(new Unit(code, name, credits, ""));
        if (!ok) {
            JOptionPane.showMessageDialog(null, "Unit code already exists", "Duplicate", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view.clearForm();
        refresh();
    }

    private void refresh() {
        var list = new ArrayList<>(store.getUnits());
        AlgorithmUtil.bubbleSortUnitsByCode(list);
        view.refreshTable(list);
    }
}
