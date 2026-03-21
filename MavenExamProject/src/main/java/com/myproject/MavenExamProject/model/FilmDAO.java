package com.myproject.MavenExamProject.model;

import DBConnector.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class that provides data access logic. Used for accessing and performing CRUD operations 
 * on Film data with database queries for searching and retrieving films based on various criteria, and ensures secure 
 * SQL queries by using a prepared statement for user-defined search.
 * 
 * @author MartinN
 */
public class FilmDAO {
    /**
     * Searches for films based on the provided title, genre, and rating filters.
     * 
     * The method dynamically constructs SQL queries to include child- and adult-appropriate ratings based on the 
     * boolean parameters.
     * 
     * @param title   The title of the film to search for (partial match).
     * @param genre   The genre of the film (exact match or "All" for any genre).
     * @param isChild Boolean option to indicate if child appropriate ratings should be included.
     * @param isAdult Boolean option to indicate if adult ratings should be included.
     * @return A list of Film objects that match the search criteria.
     * @throws SQLException if a database access error occurs.
     */
    public List<Film> searchFilms(String title, String genre, boolean isChild, boolean isAdult) throws SQLException {
        List<Film> films = new ArrayList<>();
        
        // Initial SQL query with dynamic WHERE clause based on selected filters to be used in the PreparedStatement by using ?.
        // Inner joins film, film_category and category tables
        String sql = "SELECT f.title, c.name AS genre, f.description, f.release_year, f.rating " +
                     "FROM film f " +
                     "JOIN film_category fc ON f.film_id = fc.film_id " +
                     "JOIN category c ON fc.category_id = c.category_id " +
                     "WHERE f.title LIKE ? AND c.name LIKE ? ";

        // Define rating filters based on isChild and isAdult checkboxes, by also using the initial SQL query in the search
        // sql = sql + parameters
        if (isChild && !isAdult) {
            sql += "AND f.rating IN ('G', 'PG', 'PG-13') ";
        } else if (!isChild && isAdult) {
            sql += "AND f.rating IN ('R', 'NC-17') ";
        } else if (isChild && isAdult) {
            sql += "AND (f.rating IN ('G', 'PG', 'PG-13') OR f.rating IN ('R', 'NC-17')) ";
        }

        // Connect to the DB using method from DatabaseConnection class. Make a stmt with connection to the DB using 
        // SQL query above as parameters with a try catch block to pick up errors.
        // Use the PreparedStatement to prevent SQL injection by seperating SQL codes from data, example "%"+title+"%" will 
        // become/be treated as a value, and not as executable SQL codes.
        // Set dynamic parameters using setString() to allow user-provided search criterias.
        try (Connection connection = DatabaseConnection.getConnection();
        	 PreparedStatement stmt = connection.prepareStatement(sql)) {
        	
        	// Set the title parameter with wildcards for partial matching
            stmt.setString(1, "%" + title + "%");
            // Set the genre parameter; if "All" is selected, use "%" to match any genre
            stmt.setString(2, genre.equals("All") ? "%" : genre);

            // Execute the query and process results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	// Map result set row to Film object and add it to the list
                Film film = new Film(
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("description"),
                    rs.getInt("release_year"),
                    rs.getString("rating")
                );
                films.add(film);
            }
        } catch (SQLException e) {
        	// Catch block to pick up the errors and print an error message using the err. for diagnostics
            // no printStackTrace to avoid the log to be clogged, since search may be called several times
            System.err.println("Something wrong happened with the querying: " + e.getMessage());
        }
        return films;
    }

    /**
     * Retrieves all films from the database without criterias from user inputs.
     *
     * @return A list of all "Film" objects in the database.
     * @throws SQLException If a database access error occurs during query execution.
     */
    public List<Film> getAllFilms() throws SQLException {
        List<Film> films = new ArrayList<>();

        // Query to retrieve all films with their details
        String query = "SELECT f.title, c.name AS genre, f.description, f.release_year, f.rating " + // Add f.rating here
                       "FROM film f " +
                       "JOIN film_category fc ON f.film_id = fc.film_id " +
                       "JOIN category c ON fc.category_id = c.category_id "+
        			   "ORDER BY c.name ASC, f.title ASC";

        // Use a Statement because no user input is needed, to execute the query with a try catch block
        try (Connection connection = DatabaseConnection.getConnection();
        	 Statement stmt = connection.createStatement()) {
        	
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	// Map result set row to Film object and add it to the list
                Film film = new Film(
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("description"),
                    rs.getInt("release_year"),
                    rs.getString("rating")
                );
                films.add(film);
            }
        } catch (SQLException e) {
        	//catch block to pick up error, with printStackTrace to help identifying possible errors easier
        	System.err.println("An error occurred while retrieving all films: " + e.getMessage());
        	e.printStackTrace();
        }
        return films;
    }
}
