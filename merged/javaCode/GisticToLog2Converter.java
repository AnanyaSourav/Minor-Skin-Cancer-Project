package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GisticToLog2Converter {
    public static void main(String[] args) {
        String folderPath = "D:/Skin Cancer-prj/cna"; // Change this to your folder
        File folder = new File(folderPath);

        // Get all CSV files in the folder
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.out.println("No CSV files found in the specified folder.");
            return;
        }

        for (File file : files) {
            processCSV(file);
        }
    }

    public static void processCSV(File file) {
        List<String[]> data = new ArrayList<>();
        String[] headers = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    headers = Arrays.copyOf(values, values.length + 1);
                    headers[headers.length - 1] = "log2R_CNA"; // Add new column
                    isHeader = false;
                } else {
                    String gisticStr = values.length > 1 ? values[1].trim() : ""; // GISTIC column
                    double log2Value;
                    
                    if (gisticStr.isEmpty()) {
                        log2Value = Double.NaN; // Mark as missing
                    } else {
                        try {
                            double gisticValue = Double.parseDouble(gisticStr);
                            log2Value = convertGisticToLog2(gisticValue);
                            log2Value = normalize(log2Value, -2, 2);
                        } catch (NumberFormatException e) {
                            log2Value = Double.NaN; // Handle invalid values
                        }
                    }

                    String[] newRow = Arrays.copyOf(values, values.length + 1);
                    newRow[newRow.length - 1] = Double.isNaN(log2Value) ? "NA" : String.format("%.4f", log2Value);
                    data.add(newRow);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName() + " - " + e.getMessage());
            return;
        }

        // Write back to the same file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.join(",", headers));
            bw.newLine();
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
            System.out.println("Updated file: " + file.getName());
        } catch (IOException e) {
            System.err.println("Error writing file: " + file.getName() + " - " + e.getMessage());
        }
    }

    // Convert GISTIC to Log2R_CNA using an approximate mapping
    public static double convertGisticToLog2(double gistic) {
        if (gistic <= -2) return -1.5 + Math.random() * 0.5; // Deep deletion
        if (gistic > -2 && gistic <= -1) return -1.0 + Math.random() * 0.5; // Shallow deletion
        if (gistic > -1 && gistic < 1) return -0.5 + Math.random() * 1.0; // Neutral
        if (gistic >= 1 && gistic < 2) return 0.5 + Math.random() * 0.5; // Low gain
        if (gistic >= 2 && gistic < 5) return 1.0 + Math.random() * 1.0; // Amplification
        return 2.0 + Math.random() * 1.0; // High-level amplification
    }

    // Normalize values between min and max range
    public static double normalize(double value, double min, double max) {
        double norm = (value + 3) / (3 + 3); // Normalizing between 0 and 1 (assuming max GISTIC ~3)
        return min + norm * (max - min);
    }
}
