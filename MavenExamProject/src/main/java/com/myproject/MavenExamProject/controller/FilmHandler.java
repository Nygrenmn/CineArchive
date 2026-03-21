	package com.myproject.MavenExamProject.controller;
	
	import com.myproject.MavenExamProject.model.Film;
	import com.myproject.MavenExamProject.model.FilmDAO;
	import com.myproject.MavenExamProject.view.FilmsView;
	
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.sql.SQLException;
	import java.util.List;
	import javax.swing.JOptionPane;
	
	/**
	 * The FilmHandler class acts as a controller in an MVC architecture, handling interactions between the FilmsView (UI) 
	 * and FilmDAO (data access) classes. It listens to user actions, queries the database through the FilmDAO, and 
	 * updates the FilmsView with results.
	 *
	 *@author Atle
	 *@author MartinN
	 */
	public class FilmHandler {
	    private FilmDAO model; // Data Access Object for films
	    private FilmsView view; // User Interface view for films
	
	    /**
	     * Constructs a FilmHandler object and sets up listeners for user actions in the FilmsView.
	     *
	     * @param model The FilmDAO instance for interacting with the film database
	     * @param view 	The FilmsView instance representing the UI
	     */
	    public FilmHandler(FilmDAO model, FilmsView view) {
	        this.model = model;
	        this.view = view;
	
	        // Adding listeners to view buttons using methods from the FilmsView class
	        this.view.addSearchButtonListener(new SearchButtonListener());
	        this.view.addDisplayAllButtonListener(new DisplayAllButtonListener());
	        this.view.addAddToListButtonListener(new AddToListButtonListener());
	        this.view.addClearResultButtonListener(new ClearResultButtonListener());
	    }
	
	    /**
	     * Inner class that handles the "Search" button action. Retrieves user input for search filters from the FilmsView, 
	     * queries the FilmDAO for matching films, and displays the results in the FilmsView. 
	     * Catches and prints any SQLException thrown during the database query.
	     */
	    class SearchButtonListener implements ActionListener {
	    	// The @Override indicates that this method is implementing the actionPerformed method defined in 
	    	// the ActionListener interface.
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                // Retrieve search filters from view using methods from FilmsView
	                String title = view.getFilmName();
	                String genre = view.getSelectedGenre();
	                boolean isChild = view.isChildRatingSelected();
	                boolean isAdult = view.isAdultRatingSelected();
	
	                // Search films with specified parameters using method from FilmDAO by querying the database for films
	                // matching the search criteria
	                List<Film> films = model.searchFilms(title, genre, isChild, isAdult);
	
	                // Display search results in view using method from FilmsView
	                view.showFilmsInTable(films);
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	
	    /**
	     * Inner class that handles the "Display all films" button action. Fetches all films from the FilmDAO and 
	     * displays them in the FilmsView.
	     */
	    class DisplayAllButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                // Retrieve all films from the model using method from FilmDAO
	                List<Film> allFilms = model.getAllFilms();
	
	                // Display all films in view using method from FilmsView
	                view.showFilmsInTable(allFilms);
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	
	    /**
	     * Inner class that handles the "Add film to my list" button action. Retrieves selected films from the FilmsView 
	     * and appends their details to a CSV file (mylist.csv). Displays a message if no films are selected.
	     */
	    class AddToListButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                // Retrieve selected films from view
	                List<Film> selectedFilms = view.getSelectedFilms();
	
	                // Add selected films to mylist CSV file using method below
	                addFilmsToMyList(selectedFilms);
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    /**
	     * Inner class that handles the "Clear result" button action. Clears the films displayed in the FilmsView table.
	     */
	    class ClearResultButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            view.clearTable();
	        }
	    }
	
	    /**
	     * Adds the given list of films to the mylist file (mylist.csv).
	     * If the file does not exist, it will be created. Each film's details are written as a new line in CSV format:
	     * <title>,<genre>,<release year>,<rating>.
	     *
	     * @param selectedFilms The list of films to add to the mylist, the return from a method in FilmsView
	     * @throws IOException 	If an I/O error occurs while writing to the file
	     */
	    private void addFilmsToMyList(List<Film> selectedFilms) throws IOException {
	        // Check if any films are selected, otherwise show a message
	        if (selectedFilms.isEmpty()) {
	            JOptionPane.showMessageDialog(view, "Please select a movie to add to My List.");
	            return;
	        }
	
	        String filePath = "mylist.csv";
	        
	        // Creates a FileWriter object to append, using the parameter true, selected films to the filepath "mylist.csv".
	        try (FileWriter writer = new FileWriter(filePath, true)) {
	            // Write each selected film's details to the file with a for-each loop
	            for (Film film : selectedFilms) {
	            	// Inside the loop, for each film in selectedFilms, the write method of the FileWriter is called.
	            	// Creates a formatted string with the film details. Placeholder for String=%s, integer=%d and newline=%n.
	                writer.write(String.format("%s,%s,%d,%s%n", film.getTitle(), film.getGenre(), film.getReleaseYear(), film.getRating()));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(view, "Error writing to list: " + e.getMessage());
	        }
	
	        // Notify the user that the films were added successfully
	        JOptionPane.showMessageDialog(view, "The selected movies were added to your list");
	    }
	}