package com.myproject.MavenExamProject.model;

 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Class that handles CSV file I/O operations, including parsing, exporting, 
 * and selecting CSV files.
 * 
 * @author Oda
 */


public class CsvFileProcessor {
	
	
	/**
     * Method that parses a CSV file and converts it into a list of string arrays, where each array 
     * represents a record from the file.
     * 
     * @param file The CSV file to parse.
     * @return A list of records, where each record is a string array.
     * @throws IOException If there is an error reading the file.
     */
    public static List<String[]> parse(File file) throws IOException {
        List<String[]> csvRecords = new ArrayList<>();  // List to store the records from the CSV file.
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skips header
            while ((line = br.readLine()) != null) {
            	csvRecords.add(line.split(","));
            }
        }
        return csvRecords; //The array is added to the csvRecords list.
    }
    
    /**
     * Method that exports a CSV file (films.csv) to a destination chosen by the user using a file chooser dialog.
     * 
     * @param sourceFile The source CSV file to be exported.
     */
    public static void exportCsvFile(File sourceFile) {
    	File csvFile = new File("films.csv");
    	
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Save CSV file");
    	fileChooser.setSelectedFile(new File("films.csv"));
    	
    	int result = fileChooser.showSaveDialog(null);
    	
    	if (result == JFileChooser.APPROVE_OPTION) {
    		File destinationFile = fileChooser.getSelectedFile();
    		
    		try {
    			Files.copy(csvFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "CSV file downloaded successfully to: " + destinationFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while downloading the CSV file.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Download cancelled.");
        }
    		
    }
    
    /**
     * Method that opens a file chooser dialog to let the user select a CSV file for insertion to database.
     * 
     * @param dialogTitle the title of the dialog window.
     * @return The selected CSV file or null if the selection was cancelled.
     */    
    public static File selectCsvFile(String dialogTitle) {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Please select the film.csv file to import to database");
	    int returnValue = fileChooser.showOpenDialog(null);
	    
	    if (returnValue == JFileChooser.APPROVE_OPTION) {
	        return fileChooser.getSelectedFile();
	    } else {
	        System.out.println("File selection cancelled.");
	        return null;
	    }
	}   
}