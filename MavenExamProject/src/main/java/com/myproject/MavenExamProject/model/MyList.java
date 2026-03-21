package com.myproject.MavenExamProject.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.myproject.MavenExamProject.view.MyListView;

/**
 * MyList class handles operations for managing and exporting a list of movies.
 * 
 * @author MartinU
 */

public class MyList {
	 private MyListView view;
	 
	/**
     * Returns the list of films.
     *
     * @return a list of string arrays, where each array represents a movie and its details
     */
    private List<String[]> myListFilms = new ArrayList<>();
    

    public List<String[]> getMyListFilms() {
        return myListFilms;
    }

    /**
     * Loads the list of films from the specified CSV file.
     * Each line in the file should represent a movie, with details separated by commas.
     *
     * @param csvFile the CSV file to load the list from
     */
    public void loadMyListFromCSV(File csvFile) {
        myListFilms.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                myListFilms.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error loading mylist from CSV: " + e.getMessage());
        }
    }

    /**
     * Saves the list to the specified CSV file.
     * Each movie is written as a line in the file, with details separated by commas.
     *
     * @param fileToSave the file to save the list to
     */
    public void saveMyListToCSV(File fileToSave) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSave))) {
            for (String[] movieData : myListFilms) {
                bw.write(String.join(",", movieData));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving mylist to CSV: " + e.getMessage());
        }
    }
    
    /**
     * Exports the list of films to a CSV file.
     * Prompts the user for a file name and location, and saves the list to the specified file.
     * If no file name is provided, the operation is canceled.
     */
    public void exportMyList() {
        String fileName = JOptionPane.showInputDialog(null, "Enter name for CSV file:", "Save MyList", JOptionPane.PLAIN_MESSAGE);
        if (fileName == null || fileName.trim().isEmpty()) {
        	JOptionPane.showMessageDialog(view, "Export canceled: No file name provided.");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        fileChooser.setSelectedFile(new File(fileName + ".csv"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            saveMyListToCSV(fileToSave);
            JOptionPane.showMessageDialog(view, fileName + " exported to: " + fileToSave.getAbsolutePath());
        }
    }
    
    public void clearCSVFile() {
        // Get the file path relative to the project folder
        File csvFile = new File("mylist.csv");

        if (csvFile.exists()) {
            try (FileWriter writer = new FileWriter(csvFile, false)) { // Open file in overwrite mode
                writer.write(""); // Write nothing to clear its content
                JOptionPane.showMessageDialog(null, "List cleared successfully.");
            } catch (IOException e) {
                System.err.println("Error clearing the CSV file: " + e.getMessage());
            }
        } else {
            System.err.println("CSV file does not exist: " + csvFile.getAbsolutePath());
        }
    }
    /**
     * Method that retrieves the film titles from the csv file for the combobox in reviewView
     * 
     * 
     * @return Returns an ArrayList containing all the film titles in the csv file
     * @author vendela
     */
    public static ArrayList<String> getFilmTitles(){
    	File csvFile = new File("mylist.csv");
    	ArrayList<String> filmTitles = new ArrayList<String>();
    	try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
    		String line;
    		while((line = br.readLine()) != null) {
    			String[] parts = line.split(",");
    			String title = parts[0];
    			filmTitles.add(title);
    		}
    		
    	} catch(IOException e) {
    		System.out.println("Error retrieving filmtitles from CSV: " + e.getMessage());
    	}
    	return filmTitles;
    }


}
