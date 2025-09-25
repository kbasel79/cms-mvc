package edu.cms.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UnitsPanel extends JPanel {
    private final JTextField codeField = new JTextField(8);
    private final JTextField nameField = new JTextField(16);
    private final JSpinner creditsSpinner = new JSpinner(new SpinnerNumberModel(6, 0, 100, 1));
    private final JButton addBtn = new JButton("Add Unit");
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Code","Name","Credits"}, 0);
    private final JTable table = new JTable(model);

    public UnitsPanel() {
        setLayout(new BorderLayout(12,12));
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Code:")); form.add(codeField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Credits:")); form.add(creditsSpinner);
        form.add(addBtn);
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public String getUnitCodeInput() { return codeField.getText().trim(); }
    public String getUnitNameInput() { return nameField.getText().trim(); }
    public int getUnitCreditsInput() { return (int) creditsSpinner.getValue(); }
    public JButton getAddBtn() { return addBtn; }
    public void clearForm() { codeField.setText(""); nameField.setText(""); creditsSpinner.setValue(6); }

    public void refreshTable(java.util.List<edu.cms.model.Unit> units) {
        model.setRowCount(0);
        for (edu.cms.model.Unit u : units) {
            model.addRow(new Object[]{ u.getCode(), u.getName(), u.getCredits() });
        }
    }
}
