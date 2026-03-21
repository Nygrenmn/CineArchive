package com.myproject.MavenExamProject.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.myproject.MavenExamProject.model.CsvFileProcessor;
import com.myproject.MavenExamProject.model.Driver;
import com.myproject.MavenExamProject.view.ActionsMenuView;
import java.sql.ResultSetMetaData;

/**
 * Controller class managing the interaction between the ActionsMenuView and
 * Driver
 * 
 * @author Oda
 * @author Martin U
 **/

public class ActionsHandler {

	private ActionsMenuView actionsMenuView;
	private Driver driver;

	/**
	 * Constructor for initializing the ActionsHandler.
	 * 
	 * @param actionsMenuView the view instance representing the actions menu.
	 * @throws SQLException if there is an error during database initialization.
	 * @author Oda
	 */
	public ActionsHandler(ActionsMenuView actionsMenuView) throws SQLException {
		this.actionsMenuView = actionsMenuView;
		actionsMenuListener();
		initializeDatabaseDriver();

	}

	/**
	 * Method that initializes the database driver with default configuration.
	 * 
	 * @author Oda
	 */
	private void initializeDatabaseDriver() {
		driver = new Driver();
	}

	/**
	 * Method that tests the current database connection and shows the result in a
	 * pop-up message.
	 * 
	 * @author Oda
	 */
	private void testDatabaseConnection() {
		if (driver != null && driver.checkConnection()) {
			JOptionPane.showMessageDialog(null, "Database connection is active!");
		} else {
			JOptionPane.showMessageDialog(null, "Database connection is not active.");
		}
	}

	/**
	 * Method that executes an SQL query entered by the user. Handles SELECT and
	 * non-SELECT queries differently, displaying either results or affected rows.
	 * 
	 * @author Oda
	 */
	private void executeSqlQuery() {

		String sqlQuery = actionsMenuView.createSqlInputDialog();

		if (sqlQuery == null) {
			return;
		}

		if (sqlQuery.isEmpty()) {
			JOptionPane.showMessageDialog(null, "SQL query cannot be empty.", "Query execution error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			if (sqlQuery.toUpperCase().startsWith("SELECT")) {
				ResultSet resultSet = driver.executeSelectQuery(sqlQuery);
				DefaultTableModel tableModel = buildTableModel(resultSet);
				actionsMenuView.createQueryResultsTable(tableModel);
				resultSet.close();
			} else {
				int affectedRows = driver.executeUpdateQuery(sqlQuery);
				JOptionPane.showMessageDialog(null, "Query successfully executed. Rows affected: " + affectedRows,
						"Success", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Could not execute SQL query: " + e.getMessage());
		}
	}

	/**
	 * Method that converts a ResultSet into a DefaultTableModel that can be used in
	 * a JTable.
	 * 
	 * @param resultSet the ResultSet containing the data to display
	 * @return a DefaultTableModel populated with the ResultSet data
	 * @throws SQLException if an error occurs while processing the ResultSet
	 * @author Oda
	 */
	private DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
		DefaultTableModel tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();

		for (int column = 1; column <= columnCount; column++) {
			tableModel.addColumn(metaData.getColumnName(column));
		}

		while (resultSet.next()) {
			Object[] row = new Object[columnCount];
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				row[columnIndex - 1] = resultSet.getObject(columnIndex);
			}

			tableModel.addRow(row);
		}

		return tableModel;
	}

	/**
	 * Method that exports CSV file (films.csv).
	 * 
	 * @author Oda
	 */
	private void exportCsvFile() {
		File csvFile = new File("films.csv");
		CsvFileProcessor.exportCsvFile(csvFile);
	}

	/**
	 * Method that inserts data from a CSV file into the database. Displays error
	 * messages for issues during file reading or database insertion.
	 * 
	 * @author Oda
	 */
	private void importCsvFile() {
		if (driver == null || !driver.checkConnection()) {
			JOptionPane.showMessageDialog(null, "Error: Not connected to the database.", "Database Connection Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		File selectedFile = CsvFileProcessor.selectCsvFile("Please select the film.csv file to import to the database");
		if (selectedFile == null)
			return;

		try {

			driver.importCsvToDatabase(selectedFile);
			JOptionPane.showMessageDialog(null, "CSV file imported successfully!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error reading CSV file: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error inserting data into database: " + e.getMessage());
		}
	}

	/**
	 * Method that inserts a single film into the database using data provided by
	 * the user via the UI.
	 * 
	 * @author Oda
	 * @author Martin U
	 */
	public void insertSingleFilmFromUI() {

		try {

			String title = actionsMenuView.getFilmTitle().getText();
			String description = actionsMenuView.getFilmDescription().getText();
			String releaseYearText = actionsMenuView.getReleaseYear().getText();
			String genre = actionsMenuView.getSelectedGenre(); // Get selected genre
			String rating = actionsMenuView.getSelectedRating(); // Get selected rating

			if (title.isEmpty() || description.isEmpty() || releaseYearText.isEmpty()) {
				JOptionPane.showMessageDialog(null, "All fields are required!");
				return;
			}

			int releaseYear;
			try {
				releaseYear = Integer.parseInt(releaseYearText);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Release year must be a valid number!");
				return;
			}

			try {
				int nextId = driver.getNextFilmId();
				driver.insertFilmToDb(nextId, title, description, releaseYear, 1, null, 3, 4.99, 120, 19.99, rating,
						null, java.time.LocalDateTime.now().toString());
				int categoryId = mapGenreToCategoryId(genre);
				driver.insertFilmCategory(nextId, categoryId);

				JOptionPane.showMessageDialog(null, "Film added successfully!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error adding film: " + e.getMessage());
				e.printStackTrace();
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid number format: " + e.getMessage());
		}
	}

	
	/**
	 * Maps a given genre to its corresponding category ID.
	 * The genre is matched to a predefined list of categories, and the corresponding ID is returned.
	 * If the genre does not match any predefined category, the method returns 0 as a default.
	 *
	 * @param genre the genre of the film as a String
	 * @return the corresponding category ID as an integer, or 0 if the genre doesn't match any category
	 * @author Martin U
	 */
	private int mapGenreToCategoryId(String genre) {
		switch (genre) {
		case "Action":
			return 1;
		case "Animation":
			return 2;
		case "Children":
			return 3;
		case "Classics":
			return 4;
		case "Comedy":
			return 5;
		case "Documentary":
			return 6;
		case "Drama":
			return 7;
		case "Family":
			return 8;
		case "Foreign":
			return 9;
		case "Games":
			return 10;
		case "Horror":
			return 11;
		case "Music":
			return 12;
		case "New":
			return 13;
		case "Sci-Fi":
			return 14;
		case "Sports":
			return 15;
		case "Travel":
			return 16;
		default:
			return 0; // Default to 0 if the genre doesn't match any category
		}
	}

	/**
	 * Action listeners for ActionsMenuView components.
	 * 
	 * @author Oda
	 **/
	private void actionsMenuListener() {
		actionsMenuView.getTestDBConnection().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				testDatabaseConnection();
			}
		});

		actionsMenuView.getSqlQuery().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				executeSqlQuery();
			}
		});

		actionsMenuView.getExportCsv().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exportCsvFile();
			}
		});

		actionsMenuView.getImportCsv().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importCsvFile();
			}
		});

		actionsMenuView.getAddSingularFilm().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = actionsMenuView.createFilmDialog();
				if (result == JOptionPane.OK_OPTION) {
					insertSingleFilmFromUI();
				}
			}
		});

		actionsMenuView.getAboutMenu().addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				String aboutMessage = actionsMenuView.createAboutMessage();
				JOptionPane.showMessageDialog(null, aboutMessage, "About the App", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

}