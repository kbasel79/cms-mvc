package edu.cms.view;

import edu.cms.model.Student;
import edu.cms.model.UnitOffering;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EnrollmentPanel extends JPanel {
    private final JComboBox<Student> studentCombo = new JComboBox<>();
    private final JComboBox<UnitOffering> offeringCombo = new JComboBox<>();
    private final JButton enrollBtn = new JButton("Enroll");

    private final DefaultTableModel model =
            new DefaultTableModel(new String[]{"Student ID","Student Name","Offering ID","Unit Code","Term"}, 0);
    private final JTable table = new JTable(model);

    public EnrollmentPanel() {
        setLayout(new BorderLayout(12,12));

        // Top form (simpler now)
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Student:"));
        form.add(studentCombo);
        form.add(new JLabel("Offering:"));
        form.add(offeringCombo);
        form.add(enrollBtn);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // accessors for controller
    public JComboBox<Student> getStudentCombo() { return studentCombo; }
    public JComboBox<UnitOffering> getOfferingCombo() { return offeringCombo; }
    public JButton getEnrollBtn() { return enrollBtn; }

    public void refreshTable(List<Object[]> rows) {
        model.setRowCount(0);
        for (Object[] r : rows) model.addRow(r);
    }
}
