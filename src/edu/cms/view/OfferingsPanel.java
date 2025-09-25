package edu.cms.view;

import edu.cms.model.Instructor;
import edu.cms.model.Unit;
import edu.cms.model.UnitOffering;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OfferingsPanel extends JPanel {
    private final JTextField idField = new JTextField(12);
    private final JComboBox<Unit> unitCombo = new JComboBox<>();
    private final JTextField termField = new JTextField(10);
    private final JComboBox<Instructor> instructorCombo = new JComboBox<>();
    private final JButton addBtn = new JButton("Add Offering");

    private final DefaultTableModel model =
            new DefaultTableModel(new String[]{"ID","Unit Code","Unit Name","Term","Instructor","Enrolled"}, 0);
    private final JTable table = new JTable(model);

    public OfferingsPanel() {
        setLayout(new BorderLayout(12,12));

        // ---------- TOP FORM (GridBag so it wraps nicely) ----------
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,6,4,6);
        c.gridy = 0; c.anchor = GridBagConstraints.WEST;

        // row 0
        c.gridx = 0; form.add(new JLabel("Offering ID:"), c);
        c.gridx = 1; form.add(idField, c);

        c.gridx = 2; form.add(new JLabel("Unit:"), c);
        c.gridx = 3; unitCombo.setPrototypeDisplayValue(new Unit("XXXXXXXX","XXXXXXXXXXXX",6,"")); // width hint
                 form.add(unitCombo, c);

        c.gridx = 4; form.add(new JLabel("Term:"), c);
        c.gridx = 5; form.add(termField, c);

        // row 1
        c.gridy = 1;
        c.gridx = 0; form.add(new JLabel("Instructor:"), c);
        c.gridx = 1; c.gridwidth = 3; form.add(instructorCombo, c);
        c.gridwidth = 1;

        // place button on the right of row 1 and let it stick
        c.gridx = 5; c.anchor = GridBagConstraints.EAST;
        form.add(addBtn, c);

        add(form, BorderLayout.NORTH);

        // ---------- TABLE ----------
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Enter key submits the form
        addBtn.setMnemonic('A');
        idField.addActionListener(e -> addBtn.doClick());
        termField.addActionListener(e -> addBtn.doClick());
    }

    // getters for controller
    public String getOfferingIdInput() { return idField.getText().trim(); }
    public Unit getSelectedUnit()      { return (Unit) unitCombo.getSelectedItem(); }
    public String getTermInput()       { return termField.getText().trim(); }
    public Instructor getSelectedInstructor() { return (Instructor) instructorCombo.getSelectedItem(); }
    public JButton getAddBtn()         { return addBtn; }

    public JComboBox<Unit> getUnitCombo() { return unitCombo; }
    public JComboBox<Instructor> getInstructorCombo() { return instructorCombo; }
    public void clearForm() { idField.setText(""); termField.setText(""); }

    public void refreshTable(List<UnitOffering> offs) {
        model.setRowCount(0);
        for (UnitOffering o : offs) {
            String unitCode = o.getUnit().getCode();
            String unitName = o.getUnit().getName();
            String insName = (o.getInstructor() == null) ? "" : o.getInstructor().getName();
            model.addRow(new Object[]{ o.getId(), unitCode, unitName, o.getTerm(), insName, o.getEnrolled().size() });
        }
    }
}
