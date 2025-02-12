package com.acme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // Read employees.csv (mandatory)
            List<Map<String, String>> employees = new ArrayList<>();
            if (Main.class.getClassLoader().getResource("employees.csv") != null) {
                System.out.println("Employees List found. Loading...");
                employees = FileHandler.readCSV("employees.csv");
            } else {
                throw new IllegalArgumentException("Error: employees.csv is required but not found.");
            }

            // Check if previous_assignments.csv exists (optional)
            List<Map<String, String>> previousAssignments = new ArrayList<>();
            if (Main.class.getClassLoader().getResource("previous_assignments.csv") != null) {
                System.out.println("Previous assignments found. Loading...");
                previousAssignments = FileHandler.readCSV("previous_assignments.csv");
            } else {
                System.out.println("No previous assignments found. Proceeding without them...");
            }

            // Validate input
            Validator.validateEmployees(employees);
            Validator.validateAssignments(employees, previousAssignments);

            // Assign secret children
            SecretSanta secretSanta = new SecretSanta(employees, previousAssignments);
            List<Map<String, String>> assignments = secretSanta.assignSecretChildren();

            // Create the "result" directory if it doesn't exist
            Path resultDir = Paths.get("result");
            if (!Files.exists(resultDir)) {
                Files.createDirectory(resultDir);
            }

            // Write output to result/assignments.csv
            String outputFilePath = "result/assignments.csv";
            FileHandler.writeCSV(outputFilePath, assignments, new String[] {
                    "Employee_Name", "Employee_EmailID", "Secret_Child_Name", "Secret_Child_EmailID"
            });

            System.out.println("Secret Santa assignments generated successfully at: "
                    + Paths.get(outputFilePath).toAbsolutePath());
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
