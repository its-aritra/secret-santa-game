package com.acme;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class SecretSantaTest {

    @Test
    void testAssignSecretChildren_BasicFunctionality() {
        List<Map<String, String>> employees = List.of(
                Map.of("Employee_Name", "Alice", "Employee_EmailID", "alice@example.com"),
                Map.of("Employee_Name", "Bob", "Employee_EmailID", "bob@example.com"),
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie@example.com"));
        SecretSanta secretSanta = new SecretSanta(employees, null);
        List<Map<String, String>> assignments = secretSanta.assignSecretChildren();

        assertEquals(employees.size(), assignments.size());
    }

    @Test
    void testNoSelfAssignment() {
        List<Map<String, String>> employees = List.of(
                Map.of("Employee_Name", "Alice", "Employee_EmailID", "alice@example.com"),
                Map.of("Employee_Name", "Bob", "Employee_EmailID", "bob@example.com"),
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie@example.com"));
        SecretSanta secretSanta = new SecretSanta(employees, null);
        List<Map<String, String>> assignments = secretSanta.assignSecretChildren();

        for (Map<String, String> assignment : assignments) {
            String giver = assignment.get("Employee_EmailID");
            String receiver = assignment.get("Secret_Child_EmailID");
            assertNotEquals(giver, receiver, "An employee was assigned themselves as Secret Child!");
        }
    }

    @Test
    void testUniqueAssignments() {
        List<Map<String, String>> employees = List.of(
                Map.of("Employee_Name", "Alice", "Employee_EmailID", "alice@example.com"),
                Map.of("Employee_Name", "Bob", "Employee_EmailID", "bob@example.com"),
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie@example.com"));
        SecretSanta secretSanta = new SecretSanta(employees, null);
        List<Map<String, String>> assignments = secretSanta.assignSecretChildren();

        Set<String> assignedChildren = assignments.stream()
                .map(a -> a.get("Secret_Child_EmailID"))
                .collect(Collectors.toSet());

        assertEquals(employees.size(), assignedChildren.size(), "Not all assignments are unique!");
    }

    @Test
    void testNoRepetitionFromLastYear() {
        List<Map<String, String>> employees = List.of(
                Map.of("Employee_Name", "Alice", "Employee_EmailID", "alice@example.com"),
                Map.of("Employee_Name", "Bob", "Employee_EmailID", "bob@example.com"),
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie@example.com"));
        List<Map<String, String>> lastYearAssignments = List.of(
                Map.of("Employee_Name", "Alice", "Employee_EmailID", "alice@example.com", "Secret_Child_Name", "Bob",
                        "Secret_Child_EmailID", "bob@example.com"),
                Map.of("Employee_Name", "Bob", "Employee_EmailID", "bob@example.com", "Secret_Child_Name", "Charlie",
                        "Secret_Child_EmailID", "charlie@example.com"),
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie@example.com", "Secret_Child_Name",
                        "Alice", "Secret_Child_EmailID", "alice@example.com"));

        SecretSanta secretSanta = new SecretSanta(employees, lastYearAssignments);
        List<Map<String, String>> assignments = secretSanta.assignSecretChildren();

        for (Map<String, String> assignment : assignments) {
            String giver = assignment.get("Employee_EmailID");
            String receiver = assignment.get("Secret_Child_EmailID");

            for (Map<String, String> lastYear : lastYearAssignments) {
                if (lastYear.get("Employee_EmailID").equals(giver)) {
                    assertNotEquals(lastYear.get("Secret_Child_EmailID"), receiver,
                            "An employee was assigned the same Secret Child as last year!");
                }
            }
        }
    }

    @Test
    void testSingleEmployee() {
        List<Map<String, String>> employees = List.of(
                Map.of("Employee_Name", "OnlyOne", "Employee_EmailID", "onlyone@example.com"));
        SecretSanta secretSanta = new SecretSanta(employees, null);

        Exception exception = assertThrows(IllegalArgumentException.class, secretSanta::assignSecretChildren);
        assertEquals("No valid assignments possible due to constraints.", exception.getMessage());
    }

    @Test
    void testNoEmployees() {
        List<Map<String, String>> employees = List.of();
        SecretSanta secretSanta = new SecretSanta(employees, null);
        List<Map<String, String>> assignments = secretSanta.assignSecretChildren();

        assertTrue(assignments.isEmpty(), "There should be no assignments when no employees are present.");
    }

    @Test
    void testHandlingDuplicates() {
        List<Map<String, String>> employees = List.of(
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie@example.com"),
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie.ross@example.com"),
                Map.of("Employee_Name", "Charlie", "Employee_EmailID", "charlie.jr@example.com"));
        SecretSanta secretSanta = new SecretSanta(employees, null);
        List<Map<String, String>> assignments = secretSanta.assignSecretChildren();

        assertEquals(employees.size(), assignments.size(), "All employees should get unique assignments.");

        Set<String> assignedChildren = assignments.stream()
                .map(a -> a.get("Secret_Child_EmailID"))
                .collect(Collectors.toSet());

        assertEquals(employees.size(), assignedChildren.size(), "All Secret Children should be unique.");
    }
}
