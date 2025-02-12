package com.acme;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void testValidateEmployees_EmptyList_ShouldThrowException() {
        List<Map<String, String>> employees = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Validator.validateEmployees(employees);
        });

        assertEquals("Employee list is empty.", exception.getMessage());
    }

    @Test
    public void testValidateEmployees_OnlyOneEmployee_ShouldThrowException() {
        List<Map<String, String>> employees = new ArrayList<>();
        employees.add(Map.of("Employee_Name", "Hamish Murray", "Employee_EmailID", "hamish.murray@acme.com"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Validator.validateEmployees(employees);
        });

        assertEquals("At least two employees are required.", exception.getMessage());
    }

    @Test
    public void testValidateEmployees_ValidList_ShouldPass() {
        List<Map<String, String>> employees = new ArrayList<>();
        employees.add(Map.of("Employee_Name", "Hamish Murray", "Employee_EmailID", "hamish.murray@acme.com"));
        employees.add(Map.of("Employee_Name", "Layla Graham", "Employee_EmailID", "layla.graham@acme.com"));

        assertDoesNotThrow(() -> Validator.validateEmployees(employees));
    }

    @Test
    public void testValidateAssignments_NullPreviousAssignments_ShouldPass() {
        List<Map<String, String>> employees = new ArrayList<>();
        employees.add(Map.of("Employee_Name", "Hamish Murray", "Employee_EmailID", "hamish.murray@acme.com"));
        employees.add(Map.of("Employee_Name", "Layla Graham", "Employee_EmailID", "layla.graham@acme.com"));

        assertDoesNotThrow(() -> Validator.validateAssignments(employees, null));
    }

    @Test
    public void testValidateAssignments_EmptyPreviousAssignments_ShouldPass() {
        List<Map<String, String>> employees = new ArrayList<>();
        employees.add(Map.of("Employee_Name", "Hamish Murray", "Employee_EmailID", "hamish.murray@acme.com"));
        employees.add(Map.of("Employee_Name", "Layla Graham", "Employee_EmailID", "layla.graham@acme.com"));

        List<Map<String, String>> previousAssignments = new ArrayList<>();

        assertDoesNotThrow(() -> Validator.validateAssignments(employees, previousAssignments));
    }

    @Test
    public void testValidateAssignments_ValidPreviousAssignments_ShouldPass() {
        List<Map<String, String>> employees = new ArrayList<>();
        employees.add(Map.of("Employee_Name", "Hamish Murray", "Employee_EmailID", "hamish.murray@acme.com"));
        employees.add(Map.of("Employee_Name", "Layla Graham", "Employee_EmailID", "layla.graham@acme.com"));
        employees.add(Map.of("Employee_Name", "Charlie Ross", "Employee_EmailID", "charlie.ross@acme.com"));
        employees.add(Map.of("Employee_Name", "Matthew King", "Employee_EmailID", "matthew.king@acme.com"));

        List<Map<String, String>> previousAssignments = new ArrayList<>();
        previousAssignments.add(Map.of(
                "Employee_Name", "Hamish Murray",
                "Employee_EmailID", "hamish.murray@acme.com",
                "Secret_Child_Name", "Charlie Ross",
                "Secret_Child_EmailID", "charlie.ross@acme.com"
        ));
        previousAssignments.add(Map.of(
                "Employee_Name", "Layla Graham",
                "Employee_EmailID", "layla.graham@acme.com",
                "Secret_Child_Name", "Matthew King",
                "Secret_Child_EmailID", "matthew.king@acme.com"
        ));
        previousAssignments.add(Map.of(
                "Employee_Name", "Charlie Ross",
                "Employee_EmailID", "charlie.ross@acme.com",
                "Secret_Child_Name", "Hamish Murray",
                "Secret_Child_EmailID", "hamish.murray@acme.com"
        ));
        previousAssignments.add(Map.of(
                "Employee_Name", "Matthew King",
                "Employee_EmailID", "matthew.king@acme.com",
                "Secret_Child_Name", "Layla Graham",
                "Secret_Child_EmailID", "layla.graham@acme.com"
        ));

        assertDoesNotThrow(() -> Validator.validateAssignments(employees, previousAssignments));
    }

    @Test
    public void testValidateAssignments_MismatchedPreviousAssignments_ShouldThrowException() {
        List<Map<String, String>> employees = new ArrayList<>();
        employees.add(Map.of("Employee_Name", "Hamish Murray", "Employee_EmailID", "hamish.murray@acme.com"));
        employees.add(Map.of("Employee_Name", "Layla Graham", "Employee_EmailID", "layla.graham@acme.com"));
        employees.add(Map.of("Employee_Name", "Charlie Ross", "Employee_EmailID", "charlie.ross@acme.com"));
        employees.add(Map.of("Employee_Name", "Matthew King", "Employee_EmailID", "matthew.king@acme.com"));

        List<Map<String, String>> previousAssignments = new ArrayList<>();
        previousAssignments.add(Map.of(
                "Employee_Name", "Hamish Murray",
                "Employee_EmailID", "hamish.murray@acme.com",
                "Secret_Child_Name", "Charlie Ross",
                "Secret_Child_EmailID", "charlie.ross@acme.com"
        ));
        previousAssignments.add(Map.of(
                "Employee_Name", "Layla Graham",
                "Employee_EmailID", "layla.graham@acme.com",
                "Secret_Child_Name", "Matthew King",
                "Secret_Child_EmailID", "matthew.king@acme.com"
        ));
        previousAssignments.add(Map.of(
                "Employee_Name", "Charlie Ross",
                "Employee_EmailID", "charlie.ross@acme.com",
                "Secret_Child_Name", "Isabella Scott",
                "Secret_Child_EmailID", "isabella.scott@acme.com"
        )); // This one should cause the exception because Isabella isn't in employees

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Validator.validateAssignments(employees, previousAssignments);
        });

        assertEquals("Previous assignments do not match current employees.", exception.getMessage());
    }
}
