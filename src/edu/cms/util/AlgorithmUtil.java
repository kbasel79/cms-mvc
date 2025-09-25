package edu.cms.util;

import edu.cms.model.Instructor;
import edu.cms.model.Student;
import edu.cms.model.Unit;
import edu.cms.model.UnitOffering;
import java.util.List;

public class AlgorithmUtil {

    // ---------------- Bubble Sorts ----------------

    /** Sort in-place by name A→Z. */
    public static void bubbleSortStudentsByName(List<Student> list) {
        boolean swapped; int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j).getName().compareToIgnoreCase(list.get(j + 1).getName()) > 0) {
                    Student t = list.get(j); list.set(j, list.get(j + 1)); list.set(j + 1, t); swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    /** Sort in-place by ID A→Z. */
    public static void bubbleSortStudentsById(List<Student> list) {
        boolean swapped; int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j).getId().compareToIgnoreCase(list.get(j + 1).getId()) > 0) {
                    Student t = list.get(j); list.set(j, list.get(j + 1)); list.set(j + 1, t); swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    /** Sort Units by code A→Z. */
    public static void bubbleSortUnitsByCode(List<Unit> list) {
        boolean swapped; int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j).getCode().compareToIgnoreCase(list.get(j + 1).getCode()) > 0) {
                    Unit t = list.get(j); list.set(j, list.get(j + 1)); list.set(j + 1, t); swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    /** Sort Offerings by ID A→Z. */
    public static void bubbleSortOfferingsById(List<UnitOffering> list) {
        boolean swapped; int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j).getId().compareToIgnoreCase(list.get(j + 1).getId()) > 0) {
                    UnitOffering t = list.get(j); list.set(j, list.get(j + 1)); list.set(j + 1, t); swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    /** Sort Instructors by name A→Z. */
    public static void bubbleSortInstructorsByName(List<Instructor> list) {
        boolean swapped; int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j).getName().compareToIgnoreCase(list.get(j + 1).getName()) > 0) {
                    Instructor t = list.get(j); list.set(j, list.get(j + 1)); list.set(j + 1, t); swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // ---------------- Binary Searches (used next step) ----------------

    /** Requires list pre-sorted by Student ID ascending. Returns index or -1. */
    public static int binarySearchStudentById(List<Student> sortedById, String id) {
        int l = 0, r = sortedById.size() - 1;
        while (l <= r) {
            int m = (l + r) >>> 1;
            int cmp = sortedById.get(m).getId().compareToIgnoreCase(id);
            if (cmp == 0) return m;
            if (cmp < 0) l = m + 1; else r = m - 1;
        }
        return -1;
    }
}
