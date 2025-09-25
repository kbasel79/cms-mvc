package edu.cms.view;

import javax.swing.*;
import java.awt.*;

public class ReportsPanel extends JPanel {
    private final JButton genEnrollmentsBtn = new JButton("Student Enrollments");
    private final JButton genOfferingsBtn = new JButton("Offerings & Counts");

    // NEW for search
    private final JTextField searchIdField = new JTextField(10);
    private final JButton searchBtn = new JButton("Find Student by ID");

    private final JTextArea area = new JTextArea();

    public ReportsPanel() {
        setLayout(new BorderLayout(12,12));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(genEnrollmentsBtn);
        top.add(genOfferingsBtn);

        // search controls
        top.add(new JLabel("Search ID:"));
        top.add(searchIdField);
        top.add(searchBtn);

        add(top, BorderLayout.NORTH);

        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    public JButton getGenEnrollmentsBtn() { return genEnrollmentsBtn; }
    public JButton getGenOfferingsBtn() { return genOfferingsBtn; }

    // NEW getters
    public JButton getSearchBtn() { return searchBtn; }
    public String getSearchIdInput() { return searchIdField.getText().trim(); }

    public void setReport(String text) { area.setText(text); }
}
