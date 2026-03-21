package com.myproject.MavenExamProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Class that provides a GUI for "Actions" and "About" in the menubar
 * Implements the interface to provide genre selection functionality.
 * 
 * @author Oda
 * @author Martin U
 */

public class ActionsMenuView implements GenreComboBoxProvider {

    private JMenuBar menuBar;
    private JMenu actionsMenu;
    private JMenu aboutMenu;

    private JMenuItem testDBConnection;
    private JMenuItem sqlQuery;
    private JMenuItem uploadCSV;
    private JMenuItem downloadCSV;
    private JMenuItem addSingularFilm;

    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField releaseYearField;
    
    private JComboBox<String> genreComboBox;  // The combo box for selecting a film genre
    private JComboBox<String> ratingComboBox;  // The combo box for selecting a film rating
    
    /** 
     * Constructor that initializes and assembles the components for the Actions menu view. 
     * @author Oda
     **/
    public ActionsMenuView() {
        initializeFields();
        initializeMenus();
        initializeMenuItems();
        addMenuItemsToMenus();
        assembleMenuBar();
    }

    /** 
     * Method that initializes fields in the UI
     * @author Oda 
     **/
    private void initializeFields() {
        menuBar = new JMenuBar();

        titleField = new JTextField(50);
        descriptionField = new JTextField(50);
        releaseYearField = new JTextField(4);
    }

    /** 
     * Method that initializes menus ("Actions" and "About") 
     * @author Oda
     **/
    private void initializeMenus() {
        actionsMenu = new JMenu("Actions");
        aboutMenu = new JMenu("About");
    }

    /** 
     * Method that creates and initializes menu items in "Actions" menu
     * @author Oda
     **/
    private void initializeMenuItems() {
        testDBConnection = new JMenuItem("Test database connection");
        sqlQuery = new JMenuItem("Enter SQL query");
        uploadCSV = new JMenuItem("Upload film CSV into database");
        downloadCSV = new JMenuItem("Download film CSV");
        addSingularFilm = new JMenuItem("Add film to database");
    }

    /** 
     * Method that adds menu items to the "Actions" menu 
     * @author Oda
     **/
    private void addMenuItemsToMenus() {
        actionsMenu.add(testDBConnection);
        actionsMenu.add(sqlQuery);
        actionsMenu.add(uploadCSV);
        actionsMenu.add(downloadCSV);
        actionsMenu.add(addSingularFilm);
    }

    /** 
     * Method that assembles the menu bar by adding menus to it. 
     * @author Oda
     */
    private void assembleMenuBar() {
        menuBar.add(actionsMenu);
        menuBar.add(aboutMenu);
    }

    /**
     * Method that displays a dialog for adding a new film to the database, with fields for entering
     * film details such as title, description, release year, genre, and rating.
     * 
     * @return An integer indicating whether the dialog was confirmed or cancelled.
     * @author Oda
     * @author Martin U
     */
    public int createFilmDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Film title:"));
        panel.add(titleField);
        panel.add(new JLabel("Film description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Release year:"));
        panel.add(releaseYearField);
        panel.add(new JLabel("Genre:"));
        genreComboBox = createGenreComboBox();
        panel.add(genreComboBox);
        panel.add(new JLabel("Rating:"));
        ratingComboBox = createRatingComboBox();
        panel.add(ratingComboBox);

        panel.setPreferredSize(new Dimension(300, 150));
        
        return JOptionPane.showConfirmDialog(
                null, 
                panel, 
                "Add film to database", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
        );}         
    
    /**
     * Method that displays a dialog for entering custom SQL queries.
     * 
     * @return A string containing the SQL query entered by the user, or null if cancelled.
     * @author Oda
     */
    public String createSqlInputDialog() {
        ImageIcon sqlIcon = new ImageIcon(getClass().getResource("db.png"));
        Image scaledImage = sqlIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedSQLIcon = new ImageIcon(scaledImage);

        JTextArea sqlTextArea = new JTextArea(6, 40);
        sqlTextArea.setLineWrap(true);
        sqlTextArea.setWrapStyleWord(true);
        sqlTextArea.setBorder(BorderFactory.createEtchedBorder());
        JScrollPane scrollPane = new JScrollPane(sqlTextArea);

        int result = JOptionPane.showConfirmDialog(
                null,
                scrollPane,
                "Enter SQL Query",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                resizedSQLIcon
        );

        if (result == JOptionPane.OK_OPTION) {
            return sqlTextArea.getText().trim();
        }
        return null; 
    }
    
    /**
     * Creates a table to display SQL query results.
     * 
     * @param tableModel The table model containing the data to display.
     * @author Oda
     */
    public void createQueryResultsTable(DefaultTableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 400));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "SQL Query Results",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Creates an informational message about the application.
     * 
     * @return A string containing details about the application's features.
     * @author Oda
     */
    public String createAboutMessage() {
        return "The CineArchive App is your go-to solution for managing and organizing your favorite films.\n"
                + "On the Films page, you can browse the entire movie database and add new titles to your collection.\n"
                + "The My List page lets you view your personalized movie list, export it as a CSV file for backup,\n"
                + "or update it by uploading an existing CSV file.\n"
                + "With its versatile features, CineArchive keeps your collection well-organized.";
    }
    
    /**
     * Provides the implementation for the GenreComboBoxProvider interface
     * 
     * @return the genre combo box
     * @author Martin U
     */
    @Override
    public JComboBox<String> getGenreComboBox() {
        return genreComboBox; // Return the genre combo box
    }
    
    /**
     * Creates and configures the genre combo box with predefined genres. 
     * Sets its preferred size and tooltip for usability.
     * 
     * @return a JComboBox containing a list of predefined genres.
     * 
     * @author Martin U
     */
    private JComboBox<String> createGenreComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{
                "Action", "Animation", "Children", "Classics", "Comedy", "Documentary", "Drama",
                "Family", "Foreign", "Games", "Horror", "Music", "New", "Sci-Fi", "Sports", "Travel"
        });
        comboBox.setPreferredSize(new Dimension(100, 25));
        comboBox.setToolTipText("Select a genre");
        return comboBox;
    }

    
    /**
     * Creates and configures the rating combo box with predefined rating values. 
     * Sets its preferred size and tooltip for usability.
     * 
     * @return a JComboBox containing a list of predefined ratings.
     * 
     * @author Martin U
     */
    private JComboBox<String> createRatingComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{
                "G", "PG", "PG-13", "R", "NC-17"
        });
        comboBox.setPreferredSize(new Dimension(100, 25));
        comboBox.setToolTipText("Select a rating");
        return comboBox;
    }

    /** 
     * Getters for menu bar and menu items 
     * @author Oda
     * @author Martin U 
     **/
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JMenuItem getTestDBConnection() {
        return testDBConnection;
    }

    public JMenuItem getSqlQuery() {
        return sqlQuery;
    }

    public JMenuItem getImportCsv() {
        return uploadCSV;
    }

    public JMenuItem getExportCsv() {
        return downloadCSV;
    }

    public JMenuItem getAddSingularFilm() {
        return addSingularFilm;
    }

    public JTextField getFilmTitle() {
        return titleField;
    }

    public JTextField getFilmDescription() {
        return descriptionField;
    }

    public JTextField getReleaseYear() {
        return releaseYearField;
    }

    public JMenu getAboutMenu() {
        return aboutMenu;
    }
    
    public String getSelectedGenre() {
        return genreComboBox.getSelectedItem().toString();
    }

    public String getSelectedRating() {
        return ratingComboBox.getSelectedItem().toString();
    }
}
