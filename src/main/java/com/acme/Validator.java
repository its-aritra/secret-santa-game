package com.acme;

import java.util.List;
import java.util.Map;

public class Validator {
    public static void validateEmployees(List<Map<String, String>> employees) throws IllegalArgumentException {
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Employee list is empty.");
        }
        if (employees.size() < 2) {
            throw new IllegalArgumentException("At least two employees are required.");
        }
    }

    public static void validateAssignments(List<Map<String, String>> employees, List<Map<String, String>> previousAssignments) throws IllegalArgumentException {
        if (previousAssignments != null && !previousAssignments.isEmpty()) {
            for (Map<String, String> emp : employees) {
                boolean found = false;
                for (Map<String, String> prev : previousAssignments) {
                    if (emp.get("Employee_EmailID").equals(prev.get("Employee_EmailID"))) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new IllegalArgumentException("Previous assignments do not match current employees.");
                }
            }
        }
    }
}