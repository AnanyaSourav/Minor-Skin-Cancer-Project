package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ExpressionZScoreConverter {
    public static void main(String[] args) {
        String folderPath = "D:/Skin Cancer-prj/exp"; // Change this to your folder
        File folder = new File(folderPath);

        // Get all CSV files in the folder
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.out.println("No CSV files found in the specified folder.");
            return;
        }

        for (File file : files) {
            processExpressionFile(file);
        }
    }

    public static void processExpressionFile(File file) {
        List<String[]> data = new ArrayList<>();
        String[] headers = null;
        List<Double> expressionValues = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    headers = new String[]{"HGNC_Symbol", "Z_Score"}; // New headers
                    isHeader = false;
                } else {
                    if (values.length < 2) continue; // Skip invalid rows
                    String hgncSymbol = values[0].trim();
                    String expStr = values[1].trim(); // Assuming RNAseq_TPM is the second column

                    if (!expStr.isEmpty()) {
                        try {
                            double expValue = Double.parseDouble(expStr);
                            expressionValues.add(expValue);
                            data.add(new String[]{hgncSymbol, expStr});
                        } catch (NumberFormatException ignored) {
                            data.add(new String[]{hgncSymbol, "NA"}); // Handle invalid values
                        }
                    } else {
                        data.add(new String[]{hgncSymbol, "NA"}); // Missing value
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName() + " - " + e.getMessage());
            return;
        }

        // Compute mean and standard deviation
        double mean = computeMean(expressionValues);
        double stdDev = computeStdDev(expressionValues, mean);

        // Compute Z-score and update the data
        for (String[] row : data) {
            if (!row[1].equals("NA")) {
                try {
                    double expValue = Double.parseDouble(row[1]);
                    double zScore = (expValue - mean) / stdDev;
                    row[1] = String.format("%.4f", zScore);
                } catch (NumberFormatException ignored) {
                    row[1] = "NA";
                }
            }
        }

        // Write back to the same file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.join(",", headers));
            bw.newLine();
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
            System.out.println("Processed file: " + file.getName());
        } catch (IOException e) {
            System.err.println("Error writing file: " + file.getName() + " - " + e.getMessage());
        }
    }

    // Compute mean of expression values
    private static double computeMean(List<Double> values) {
        double sum = 0;
        for (double v : values) sum += v;
        return sum / values.size();
    }

    // Compute standard deviation of expression values
    private static double computeStdDev(List<Double> values, double mean) {
        double sum = 0;
        for (double v : values) sum += Math.pow(v - mean, 2);
        return Math.sqrt(sum / values.size());
    }
}
