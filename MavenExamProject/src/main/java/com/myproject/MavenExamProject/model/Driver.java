package com.myproject.MavenExamProject.model;

/**
 * Class responsible for database communication and operations (model)
 * 
 * @author Oda
 * @author MartinU 
 */

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import DBConnector.DatabaseConnection;

/**
 * Class responsible for database communication and operations (model). This
 * includes operations for querying, inserting, and bulk importing data into the
 * database.
 * 
 * @author Oda
 * @author MartinU 
 */

public class Driver {

	/**
	 * Method that checks if the database connection is active and valid.
	 * 
	 * @return boolean, true if the database connection is active and valid, false
	 *         otherwise.
	 * @author Oda
	 */
	public boolean checkConnection() {
		try {
			Connection connection = DatabaseConnection.getConnection();
			return connection != null && connection.isValid(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method that executes an SQL SELECT query and returns a ResultSet containing
	 * the query results.
	 * 
	 * @param sqlQuery The SQL query to execute.
	 * @return A ResultSet containing the results of the query.
	 * @throws SQLException If an error occurs while executing the query.
	 * @author Oda
	 */
	public ResultSet executeSelectQuery(String sqlQuery) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		Statement stmt = connection.createStatement();
		return stmt.executeQuery(sqlQuery);
	}

	/**
	 * Executes an SQL UPDATE query and returns the number of rows affected.
	 * 
	 * @param sqlQuery The SQL query to execute.
	 * @return The number of rows affected by the query.
	 * @throws SQLException If an error occurs while executing the query.
	 * @author Oda
	 */
	public int executeUpdateQuery(String sqlQuery) throws SQLException {
		Connection connection = DatabaseConnection.getConnection();
		Statement stmt = connection.createStatement();
		return stmt.executeUpdate(sqlQuery);
	}

	/**
	 * Inserts a single film record into the database.
	 * 
	 * @param filmId         The film ID.
	 * @param title          The title of the film.
	 * @param description    The description of the film.
	 * @param releaseYear    The release year of the film.
	 * @param languageId     The language ID for the film.
	 * @param ogLanguageId   The original language ID (nullable).
	 * @param rentalDuration The rental duration in days.
	 * @param rentalRate     The rental rate.
	 * @param length         The length of the film in minutes.
	 * @param replaceCost    The replacement cost.
	 * @param rating         The rating of the film.
	 * @param features       The special features of the film.
	 * @param dateTime       The last update timestamp.
	 * @throws SQLException If an error occurs while inserting the record.
	 * @author Oda
	 */
	public void insertFilmToDb(int filmId, String title, String description, int releaseYear, int languageId,
			Integer ogLanguageId, int rentalDuration, double rentalRate, int length, double replaceCost, String rating,
			String features, String dateTime) throws SQLException {

		String query = "INSERT INTO sakila.film(film_id, title, description, release_year, language_id, "
				+ "original_language_id, rental_duration, rental_rate, length, replacement_cost,"
				+ "rating, special_features, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preStmt = connection.prepareStatement(query)) {
			preStmt.setInt(1, filmId);
			preStmt.setString(2, title);
			preStmt.setString(3, description);
			preStmt.setInt(4, releaseYear);
			preStmt.setInt(5, languageId);
			preStmt.setNull(6, java.sql.Types.INTEGER);
			preStmt.setInt(7, rentalDuration);
			preStmt.setDouble(8, rentalRate);
			preStmt.setInt(9, length);
			preStmt.setDouble(10, replaceCost);
			preStmt.setString(11, rating);
			preStmt.setNull(12, java.sql.Types.VARCHAR);
			preStmt.setString(13, dateTime);

			preStmt.executeUpdate();

		}
	}

	/**
	 * Inserts a mapping between a film and a category into the film_category table.
	 * The method establishes a connection to the database, prepares the SQL query,
	 * and executes it to associate the given film ID with the specified category ID.
	 * 
	 * @param filmId     the ID of the film to be associated with a category.
	 * @param categoryId the ID of the category to be associated with the film.
	 * @throws SQLException if a database access error occurs.
	 * @author Martin U
	 */
	public void insertFilmCategory(int filmId, int categoryId) throws SQLException {
		String query = "INSERT INTO sakila.film_category (film_id, category_id, last_update) VALUES (?, ?, ?)";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preStmt = connection.prepareStatement(query)) {
			preStmt.setInt(1, filmId);
			preStmt.setInt(2, categoryId);
			preStmt.setString(3, java.time.LocalDateTime.now().toString());
			preStmt.executeUpdate();
		}
	}

	/**
	 * Retrieves the next available film ID by querying the maximum film ID in the
	 * database and incrementing 1.
	 * 
	 * @return The next available film ID.
	 * @throws SQLException If an error occurs while querying the database.
	 * @author Oda
	 */
	public int getNextFilmId() throws SQLException {
		String filmIdQuery = "SELECT MAX(film_id) FROM sakila.film";
		try (Connection connection = DatabaseConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(filmIdQuery)) {

			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
		}
		return 1;
	}

	/**
	 * Imports film data from a CSV file into the database. Each record in the CSV
	 * is inserted into the film and film_category tables.
	 * 
	 * @param csvFile The CSV file containing the film data.
	 * @throws IOException  If an error occurs while reading the file.
	 * @throws SQLException If an error occurs while inserting data into the
	 *                      database.
	 * @author Oda
	 * @author Martin U
	 */
	public void importCsvToDatabase(File csvFile) throws IOException, SQLException {
		List<String[]> records = CsvFileProcessor.parse(csvFile); // Use CsvReader for parsing
		for (String[] record : records) {
			try {

				insertFilmToDb(Integer.parseInt(record[0]), record[1], record[2], Integer.parseInt(record[3]),
						Integer.parseInt(record[4]),
						record[5].equalsIgnoreCase("NULL") ? null : Integer.parseInt(record[5]),
						Integer.parseInt(record[6]), Double.parseDouble(record[7]), Integer.parseInt(record[8]),
						Double.parseDouble(record[9]), record[10], record[11], record[12]);
						int categoryId = Integer.parseInt(record[13]);
						insertFilmCategory(Integer.parseInt(record[0]), categoryId);

			} catch (NumberFormatException e) {
				// Handle NumberFormatException locally (e.g., log and skip the record)
				System.err.println("Error parsing record: " + String.join(", ", record));
				e.printStackTrace();
			} catch (SQLException e) {
				// Log the error, but propagate the exception so the caller can handle it
				System.err.println("Error inserting record: " + String.join(", ", record));
				e.printStackTrace();
				throw e; // Rethrow SQLException to let the caller handle it
			}
		}
		System.out.println("CSV file imported successfully.");
	}

}
