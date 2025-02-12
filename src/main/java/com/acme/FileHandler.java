package com.acme;

import java.io.*;
import java.util.*;

public class FileHandler {
    public static List<Map<String, String>> readCSV(String filePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        InputStream inputStream = FileHandler.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String[] headers = br.readLine().split(",");
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], i < values.length ? values[i] : "");
                }
                data.add(row);
            }
        }
        return data;
    }

    public static void writeCSV(String filePath, List<Map<String, String>> data, String[] headers) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(String.join(",", headers));
            bw.newLine();
            for (Map<String, String> row : data) {
                bw.write(String.join(",", row.values()));
                bw.newLine();
            }
        }
    }
}