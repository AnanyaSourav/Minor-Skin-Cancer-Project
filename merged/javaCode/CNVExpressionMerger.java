package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CNVExpressionMerger {
    public static void main(String[] args) {
        String cnaFolderPath = "D:/Skin Cancer-prj"; // Change this
        String expFolderPath = "D:/Skin Cancer-prj"; // Change this
        String mergedFolderPath = "D:/Skin Cancer-prj/merged"; // Change this

        // Create merged data folder if it doesn't exist
        File mergedFolder = new File(mergedFolderPath);
        if (!mergedFolder.exists()) {
            mergedFolder.mkdir();
        }

        // Get all CNA files
        File cnaFolder = new File(cnaFolderPath);
        File[] cnaFiles = cnaFolder.listFiles((dir, name) -> name.endsWith("-cna.csv"));
        if (cnaFiles == null || cnaFiles.length == 0) {
            System.out.println("No CNA files found in " + cnaFolderPath);
            return;
        }

        for (File cnaFile : cnaFiles) {
            String patientID = cnaFile.getName().replace("-cna.csv", ""); // Extract patient ID
            File expFile = new File(expFolderPath + "/" + patientID + "-exp.csv");

            if (expFile.exists()) {
                processAndMergeFiles(cnaFile, expFile, mergedFolderPath);
            } else {
                System.out.println("Expression file missing for: " + patientID);
            }
        }
    }

    public static void processAndMergeFiles(File cnaFile, File expFile, String mergedFolderPath) {
        Map<String, String> cnaData = new HashMap<>();
        Map<String, String> expData = new HashMap<>();

        // Read CNA file and store relevant columns
        try (BufferedReader br = new BufferedReader(new FileReader(cnaFile))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (values.length >= 2) {
                    cnaData.put(values[0].trim(), values[1].trim()); // HGNC_Symbol → log2R_CNA
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CNA file: " + cnaFile.getName() + " - " + e.getMessage());
            return;
        }

        // Read Expression file and store relevant columns
        try (BufferedReader br = new BufferedReader(new FileReader(expFile))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (values.length >= 2) {
                    expData.put(values[0].trim(), values[1].trim()); // HGNC_Symbol → Z_Score
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Expression file: " + expFile.getName() + " - " + e.getMessage());
            return;
        }

        // Merge Data
        String mergedFilePath = mergedFolderPath + "/" + cnaFile.getName().replace("-cna.csv", "-merged.csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(mergedFilePath))) {
            bw.write("HGNC_Symbol,log2R_CNA,Z_Score");
            bw.newLine();

            for (String gene : cnaData.keySet()) {
                String log2R_CNA = cnaData.get(gene);
                String zScore = expData.getOrDefault(gene, "NA"); // If missing, set NA
                bw.write(gene + "," + log2R_CNA + "," + zScore);
                bw.newLine();
            }

            System.out.println("Merged file created: " + mergedFilePath);
        } catch (IOException e) {
            System.err.println("Error writing merged file: " + mergedFilePath + " - " + e.getMessage());
        }
    }
}
