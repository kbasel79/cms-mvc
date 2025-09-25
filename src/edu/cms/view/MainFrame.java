package edu.cms.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final StudentsPanel studentsPanel = new StudentsPanel();
    private final UnitsPanel unitsPanel = new UnitsPanel();
    private final InstructorsPanel instructorsPanel = new InstructorsPanel();
    private final OfferingsPanel offeringsPanel = new OfferingsPanel();
    private final EnrollmentPanel enrollmentPanel = new EnrollmentPanel();
    private final ReportsPanel reportsPanel = new ReportsPanel(); // NEW

    public MainFrame() {
        super("College Management System (MVC)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", studentsPanel);
        tabs.addTab("Units", unitsPanel);
        tabs.addTab("Instructors", instructorsPanel);
        tabs.addTab("Offerings", offeringsPanel);
        tabs.addTab("Enrollment", enrollmentPanel);
        tabs.addTab("Reports", reportsPanel); // NEW
        add(tabs, BorderLayout.CENTER);
    }

    public StudentsPanel getStudentsPanel() { return studentsPanel; }
    public UnitsPanel getUnitsPanel() { return unitsPanel; }
    public InstructorsPanel getInstructorsPanel() { return instructorsPanel; }
    public OfferingsPanel getOfferingsPanel() { return offeringsPanel; }
    public EnrollmentPanel getEnrollmentPanel() { return enrollmentPanel; }
    public ReportsPanel getReportsPanel() { return reportsPanel; } // NEW
}
