package edu.cms.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentsPanel extends JPanel {
    private final JTextField idField = new JTextField(10);
    private final JTextField nameField = new JTextField(16);
    private final JButton addBtn = new JButton("Add Student");
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Name"}, 0);
    private final JTable table = new JTable(model);

    public StudentsPanel() {
        setLayout(new BorderLayout(12,12));
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(addBtn);
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public String getStudentIdInput() { return idField.getText().trim(); }
    public String getStudentNameInput() { return nameField.getText().trim(); }
    public JButton getAddBtn() { return addBtn; }
    public void clearForm() { idField.setText(""); nameField.setText(""); }

    public void refreshTable(java.util.List<edu.cms.model.Student> students) {
        model.setRowCount(0);
        for (edu.cms.model.Student s : students) {
            model.addRow(new Object[]{ s.getId(), s.getName() });
        }
    }
}
