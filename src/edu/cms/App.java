package edu.cms;

import edu.cms.controller.*;
import edu.cms.model.DataStore;
import edu.cms.view.MainFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataStore.get().loadAll(); // <-- load CSVs on startup
            MainFrame frame = new MainFrame();
            new StudentsController(frame.getStudentsPanel());
            new UnitsController(frame.getUnitsPanel());
            new InstructorsController(frame.getInstructorsPanel());
            new OfferingsController(frame.getOfferingsPanel());
            new EnrollmentController(frame.getEnrollmentPanel());
            new ReportsController(frame.getReportsPanel());
            frame.setVisible(true);
        });
    }
}
