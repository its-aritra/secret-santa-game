package com.acme;

import java.util.*;

public class SecretSanta {
    private List<Map<String, String>> employees;
    private List<Map<String, String>> previousAssignments;

    public SecretSanta(List<Map<String, String>> employees, List<Map<String, String>> previousAssignments) {
        this.employees = employees;
        this.previousAssignments = previousAssignments;
    }

    public List<Map<String, String>> assignSecretChildren() throws IllegalArgumentException {
        List<Map<String, String>> assignments = new ArrayList<>();
        Map<String, String> previousEmployeeChildMap = new HashMap<>();
        Map<String, Set<String>> invalidEmailMap = new HashMap<>();
        LinkedHashSet<Map<String, String>> remainingEmployees = new LinkedHashSet<>(employees); // Maintain order and
                                                                                                // O(1) removals

        // Populate previousEmployeeChildMap
        if (previousAssignments != null && !previousAssignments.isEmpty()) {
            for (Map<String, String> prev : previousAssignments) {
                previousEmployeeChildMap.put(prev.get("Employee_EmailID"), prev.get("Secret_Child_EmailID"));
            }
        }

        // Pre-calculate invalid emails for each employee
        for (Map<String, String> employee : employees) {
            Set<String> invalidEmails = new HashSet<>();
            invalidEmails.add(employee.get("Employee_EmailID"));

            String previousChild = previousEmployeeChildMap.get(employee.get("Employee_EmailID"));
            if (previousChild != null) {
                invalidEmails.add(previousChild);
            }

            invalidEmailMap.put(employee.get("Employee_EmailID"), invalidEmails);
        }

        Random random = new Random();

        // Assign Secret Children
        for (Map<String, String> employee : employees) {
            Set<String> invalidEmails = invalidEmailMap.get(employee.get("Employee_EmailID"));

            // Filter valid options once
            List<Map<String, String>> validOptions = remainingEmployees.stream()
                    .filter(e -> !invalidEmails.contains(e.get("Employee_EmailID")))
                    .toList();

            if (validOptions.isEmpty()) {
                throw new IllegalArgumentException("No valid assignments possible due to constraints.");
            }

            // Randomly assign a secret child
            Map<String, String> secretChild = validOptions.get(random.nextInt(validOptions.size()));
            Map<String, String> assignment = new LinkedHashMap<>();
            assignment.put("Employee_Name", employee.get("Employee_Name"));
            assignment.put("Employee_EmailID", employee.get("Employee_EmailID"));
            assignment.put("Secret_Child_Name", secretChild.get("Employee_Name"));
            assignment.put("Secret_Child_EmailID", secretChild.get("Employee_EmailID"));

            assignments.add(assignment);
            remainingEmployees.remove(secretChild); // O(1) time complexity with LinkedHashSet
        }

        return assignments;
    }
}