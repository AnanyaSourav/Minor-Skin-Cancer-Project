package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class TsvToCsvConverter {

    public static void convertTsvToCsv(String tsvFilePath, String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(tsvFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                // Split using tab (\t) as delimiter
                String[] values = line.split("\t");
                writer.writeNext(values);
            }

            System.out.println("Conversion completed: " + csvFilePath);

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Set the file paths (Modify these paths according to your system)
        String tsvFilePath = "D:/Downloads/CancerModelsOrg_HCM-BROD-0332-C43_expression_HCM-BROD-0332-C43-85M-01R-A78V-41_Not-provided.tsv";

        String csvFilePath = "D:/Skin Cancer-prj/HCM-BROD-0332-C43-exp.csv";

        convertTsvToCsv(tsvFilePath, csvFilePath);
    }
}
