package com.myproject.MavenExamProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.myproject.MavenExamProject.model.Film;

/**
 * The class represents the user interface for displaying and managing a collection of films.
 * Provides a search panel, filters, and a table to display film information.
 * Extends JPanel to provide the UI as a panel.
 * 
 * @author Atle
 */
public class FilmsView extends JPanel implements GenreComboBoxProvider {

	private JTextField searchField;              // The text field for entering a search term
	private JComboBox<String> genreComboBox;     // The combo box for selecting a film genre
	private JCheckBox childCheckBox;             // The check box for filtering films suitable for children
	private JCheckBox adultCheckBox;             // The check box for filtering films suitable for adults
	private JButton searchButton;                // The button for triggering the search
	private JButton clearResultButton;           // The button for clearing the search result
	private JButton displayAllButton;            // The button for displaying all films
	private JButton addToListButton;             // The button for adding a film to the user's list
	private JTable filmsTable;                   // The table for displaying the films
	private DefaultTableModel tableModel;		 //
    private JComboBox<String> tableComboBox;	 // The comboBox for selecting a specific table or category of data to display

    /**
     * Constructs the FilmsView panel with all UI elements.
     * Sets the layout and initializes panels and table.
     */
    public FilmsView() {
        setLayout(new BorderLayout(10, 10));
        initializePanels();
        initializeTable();
    }

    /**
     * Creates film, left and right panels by initializing the methods for these.
     * Adds the left and right panels to the film main panel, and setting their position
     */
    private void initializePanels() {
        JPanel filmsPanel = createFilmsPanel();
        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        filmsPanel.add(leftPanel, BorderLayout.WEST);
        filmsPanel.add(rightPanel, BorderLayout.EAST);

        add(filmsPanel, BorderLayout.NORTH);
    }

    /**
     * Creates the main films panel with border layout and background color.
     * Sets the preferred size of the panel.
     * @return the main films panel.
     */
    private JPanel createFilmsPanel() {
        JPanel filmsPanel = new JPanel(new BorderLayout());
        filmsPanel.setBackground(Color.decode("#E6E6FA"));
        filmsPanel.setPreferredSize(new Dimension(800, 50));
        return filmsPanel;
    }

    /**
     * Creates and configures the left panel with search and filter components.
     * Adds the components to the panel.
     * @return the left panel.
     */
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.decode("#E6E6FA"));

        searchField = new JTextField(15);
        genreComboBox = createGenreComboBox();
        childCheckBox = new JCheckBox("Child");
        adultCheckBox = new JCheckBox("Adult");
        searchButton = createSearchButton();

        displayAllButton = new JButton("Display all films");
        clearResultButton = new JButton("Clear result");

        leftPanel.add(new JLabel(" Film name: "));
        leftPanel.add(searchField);
        leftPanel.add(new JLabel(" Genre: "));
        leftPanel.add(genreComboBox);
        leftPanel.add(new JLabel(" Rating: "));
        leftPanel.add(childCheckBox);
        leftPanel.add(adultCheckBox);
        leftPanel.add(searchButton);
        leftPanel.add(displayAllButton);
        leftPanel.add(clearResultButton);

        return leftPanel;
    }

    // Implementing the method from GenreComboBoxProvider interface
    @Override
    public JComboBox<String> getGenreComboBox() {
        return genreComboBox; // Return the genre combo box
    }
    
    /**
     * Creates and configures the genre combo box.
     * Sets the preferred size and tool tip text of the combo box.
     * @return the genre combo box.
     */
    private JComboBox<String> createGenreComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{
                "All", "Action", "Animation", "Children", "Classics", "Comedy", "Documentary", "Drama",
                "Family", "Foreign", "Games", "Horror", "Music", "New", "Sci-Fi", "Sports", "Travel"
        });
        comboBox.setPreferredSize(new Dimension(100, 25));
        comboBox.setToolTipText("Select a genre");
        return comboBox;
    }

    /**
     * Creates and configures the search button with an icon.
     * Sets the preferred size of the button.
     * @return the search button.
     */
    private JButton createSearchButton() {
        ImageIcon searchImage = new ImageIcon(FilmsView.class.getResource("search.png"));
        Image scaledImage = searchImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setPreferredSize(new Dimension(35, 25));
        return button;
    }

    /**
     * Creates and configures the right panel with action buttons.
     * Adds the buttons to the panel.
     * @return the right panel.
     */
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightPanel.setBackground(Color.decode("#E6E6FA"));

        addToListButton = new JButton("Add film to my list");
        addToListButton.setPreferredSize(new Dimension(180, 40));
        addToListButton.setFont(new Font("Arial", Font.BOLD, 14));
        rightPanel.add(addToListButton);

        return rightPanel;
    }

    /**
     * Initializes the films table and adds it to the center of the layout with a scroll pane.
     * Creates the table model and the films table.
     */
    private void initializeTable() {
    	 tableModel = new DefaultTableModel(new Object[]{"Select", "Title", "Genre", "Description", "Year", "Rating"}, 0) {
    	        @Override
    	        public Class<?> getColumnClass(int column) {
    	            switch (column) {
    	                case 0:
    	                    return Boolean.class;
    	                default:
    	                    return String.class;
    	            }
    	        }
    	    };        
    	filmsTable = createFilmsTable();
        add(new JScrollPane(filmsTable), BorderLayout.CENTER);
    }

    /**
     * Creates and configures the films table.
     * Sets the row height, background color, grid color, font, and column widths.
     * Configures the table header.
     * @return the films table.
     */
    private JTable createFilmsTable() {
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setBackground(Color.decode("#eeeeee"));
        table.setGridColor(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // Checkbox column
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(15);
        table.getColumnModel().getColumn(3).setPreferredWidth(700);
        table.getColumnModel().getColumn(4).setPreferredWidth(15);
        table.getColumnModel().getColumn(5).setPreferredWidth(15);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#E6E6FA"));
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(0).setCellEditor(table.getDefaultEditor(Boolean.class));

        return table;
    }

    /**
     * Returns the text from the search field.
     * @return a String representing the film name entered by the user.
     */
    public String getFilmName() {
        return searchField.getText();
    }

    /**
     * Returns the selected genre from the combo box.
     * @return a String representing the selected genre.
     */
    public String getSelectedGenre() {
        return genreComboBox.getSelectedItem().toString();
    }

    /**
     * Checks if the "child" rating checkbox is selected.
     * @return true if the checkbox is selected, false otherwise.
     */
    public boolean isChildRatingSelected() {
        return childCheckBox.isSelected();
    }

    /**
     * Checks if the "adult" rating checkbox is selected.
     * @return true if the checkbox is selected, false otherwise.
     */
    public boolean isAdultRatingSelected() {
        return adultCheckBox.isSelected();
    }

    /**
     * Returns a list of the selected films in the table, for when the user wants to add these sto "My List".
     * The films are represented as Film objects, constructed using the values from each table row.
     * @return a list of Film objects representing the selected films.
     */
    public List<Film> getSelectedFilms() {
        List<Film> selectedFilms = new ArrayList<>();
        int rowCount = filmsTable.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            Boolean isSelected = (Boolean) filmsTable.getValueAt(row, 0);
            if (isSelected != null && isSelected) {
                String title = (String) filmsTable.getValueAt(row, 1);
                String genre = (String) filmsTable.getValueAt(row, 2);
                String description = (String) filmsTable.getValueAt(row, 3);
                int releaseYear = (int) filmsTable.getValueAt(row, 4);
                String rating = (String) filmsTable.getValueAt(row, 5);
                selectedFilms.add(new Film(title, genre, description, releaseYear, rating));
            }
        }
        return selectedFilms;
    }

    /**
     * Displays a list of films in the JTable.
     * Each film is represented as a row in the table.
     * @param allFilms from the FilmHandler, which is a list of Film objects representing the films to display.
     */
    public void showFilmsInTable(List<Film> films) {
        tableModel.setRowCount(0);
        for (Film film : films) {
            tableModel.addRow(new Object[]{
            	false, // Default value for the checkbox
                film.getTitle(),
                film.getGenre(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getRating()
            });
        }
    }

    /**
     * Adds action listeners to the "search" button, "display all" button, "add to list" button and "clear result" button.
     * @param listener, which is the name of each ActionListener to add. Created as inner classes in FilmHandler
     */
    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addDisplayAllButtonListener(ActionListener listener) {
        displayAllButton.addActionListener(listener);
    }

    public void addAddToListButtonListener(ActionListener listener) {
        addToListButton.addActionListener(listener);
    }

    public void addClearResultButtonListener(ActionListener listener) {
        clearResultButton.addActionListener(listener);
    }

    /**
     * Clears all the rows in the table.
     */
    public void clearTable() {
        tableModel.setRowCount(0);
    }
}