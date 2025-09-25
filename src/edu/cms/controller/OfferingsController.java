package edu.cms.controller;

import edu.cms.model.DataStore;
import edu.cms.model.Instructor;
import edu.cms.model.Unit;
import edu.cms.model.UnitOffering;
import edu.cms.util.AlgorithmUtil;
import edu.cms.view.OfferingsPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class OfferingsController {
    private final OfferingsPanel view;
    private final DataStore store = DataStore.get();

    public OfferingsController(OfferingsPanel view) {
        this.view = view;

        refreshCombos();
        refreshTable();

        view.getAddBtn().addActionListener(e -> onAdd());

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
        view.getUnitCombo().removeAllItems();
        for (Unit u : store.getUnits()) view.getUnitCombo().addItem(u);
        view.getInstructorCombo().removeAllItems();
        for (Instructor i : store.getInstructors()) view.getInstructorCombo().addItem(i);
    }

    private void onAdd() {
        String id   = view.getOfferingIdInput();
        Unit unit   = view.getSelectedUnit();
        String term = view.getTermInput();
        Instructor ins = view.getSelectedInstructor();

        if (id.isBlank() || unit == null || term.isBlank()) {
            JOptionPane.showMessageDialog(null, "Offering ID, Unit, and Term are required", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        UnitOffering off = new UnitOffering(id, unit, term, ins);
        boolean ok = store.addOffering(off);
        if (!ok) {
            JOptionPane.showMessageDialog(null, "Offering ID already exists", "Duplicate", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view.clearForm();
        refreshCombos();
        refreshTable();
    }

    private void refreshTable() {
        var list = new ArrayList<>(store.getOfferings());
        AlgorithmUtil.bubbleSortOfferingsById(list);
        view.refreshTable(list);
    }
}
