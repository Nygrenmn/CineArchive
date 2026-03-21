package com.myproject.MavenExamProject.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.myproject.MavenExamProject.model.MyList;

/**
 * This class represents the view for the user's movie list.
 * It displays a table of movies that the user has added to their list.
 * 
 * @author Oda 
 * @author MartinU 
 */
public class MyListView extends JPanel {
    private JButton exportListBt;
    private JButton importBt;
    private JButton clearBt;
    private JTable myListTable;
    private DefaultTableModel tableModel;
    private MyList myList;

    /**
     * Constructs a new MyListView.
     * Initializes the view layout and components, and loads the list data from the CSV file.
     * 
     * @param myList the MyList object that this view will display
     */
    public MyListView(MyList myList) {
        this.myList = myList;
        setLayout(new BorderLayout());
        
        initializeComponents();
        loadAndDisplayData();
    }

    /**
     * Initializes the components and layout of the view.
     * 
     * @author MartinU
     */
    private void initializeComponents() {
        add(createHeaderLabel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
    }

    /**
     * Creates and returns a header label for the view.
     * 
     * @author Oda 
     */
    private JLabel createHeaderLabel() {
        JLabel label = new JLabel("My List", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        return label;
    }
    
    /**
     * Creates and returns the center panel with the button panel and the table scroll pane.
     * 
     * @return the center panel containing buttons and the table scroll pane
     * @author MartinU
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(createButtonPanel(), BorderLayout.NORTH);
        centerPanel.add(createTableScrollPane(), BorderLayout.CENTER);
        return centerPanel;
    }

    /**
     * Creates and returns the button panel with export, import, and save buttons.
     * 
     * @return the button panel with functionality buttons for the list view
     * 
     * @author Oda 
     * @author MartinU
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.decode("#E6E6FA"));

        exportListBt = new JButton("Export list");
        exportListBt.setPreferredSize(new Dimension(180, 60));

        importBt = new JButton("Import list");
        importBt.setPreferredSize(new Dimension(180, 60));

        
        clearBt = new JButton("Clear list");
        clearBt.setPreferredSize(new Dimension(180, 60));

        buttonPanel.add(exportListBt);
        buttonPanel.add(importBt);
        buttonPanel.add(clearBt);
        
        return buttonPanel;
    }

    /**
     * Creates and returns a scroll pane containing the mylist table.
     * 
     * @return the scroll pane containing the movie list table
     * @author MartinU
     */
    private JScrollPane createTableScrollPane() {
        tableModel = new DefaultTableModel(new Object[]{"Title", "Genre", "Year", "Rating"}, 0);
        myListTable = new JTable(tableModel);
        customizeTableAppearance();
        
        JScrollPane scrollPane = new JScrollPane(myListTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        return scrollPane;
    }

    /**
     * Customizes the appearance of the mylist table.
     * 
     * @author MartinU
     */
    private void customizeTableAppearance() {
        myListTable.setRowHeight(30);
        myListTable.setBackground(Color.decode("#eeeeee"));
        myListTable.setGridColor(Color.BLACK);
        myListTable.setFont(new Font("Arial", Font.PLAIN, 12));

        JTableHeader tableHeader = myListTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#E6E6FA"));
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * Loads data from the CSV file and displays it in the table.
     * Uses the mylist model to retrieve the movie data.
     * 
     * @author MartinU
     */
    private void loadAndDisplayData() {
        myList.loadMyListFromCSV(new File("mylist.csv"));
        displayMyListTable(myList.getMyListFilms().toArray(new String[0][]));
    }

    /**
     * Displays the given data in the mylist table.
     *
     * @param data a 2D array of strings representing the table data
     * 
     * @author MartinU
     */
    public void displayMyListTable(String[][] data) {
        tableModel.setRowCount(0);
        for (String[] rowData : data) {
            tableModel.addRow(rowData);
        }
        revalidate();
        repaint();
    }

    /**
     * Returns the export list button.
     *
     * @return the export list button
     * @author Oda 
     */
    public JButton getExportListButton() {
        return exportListBt;
    }

    /**
     * Returns the import button.
     *
     * @return the import button
     * @author Oda 
     */
    public JButton getImportButton() {
        return importBt;
    }

    /**
     * Returns the delete button.
     *
     * @return the delete button
     * @author MartinU
     */
    public JButton getClearButton() {
        return clearBt;
    }
    
    /**
     * Returns the table model used for the "My List" table.
     *
     * @return the table model used to manage table data
     * 
     * @author MartinU
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Refreshes the mylist table data.
     * Uses the mylist model to update the displayed movies.
     * 
     * @author MartinU
     */
    public void refreshTableData() {
        myList.loadMyListFromCSV(new File("mylist.csv"));
        displayMyListTable(myList.getMyListFilms().toArray(new String[0][]));
    }
}
