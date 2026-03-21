package com.myproject.MavenExamProject.controller;

import com.myproject.MavenExamProject.model.*;
import com.myproject.MavenExamProject.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 * Handles interactions between MyList and MyListView, allowing
 * the user to import and export lists.
 *
 * @author MartinU
 */
public class MyListHandler {
    private MyList myList;
    private MyListView myListView;
    private DefaultTableModel tableModel;

    /**
     * Constructs a MyListHandler with the specified model and view.
     *
     * @param myList the MyList model
     * @param view the MyListView
     */
    public MyListHandler(MyList myList, MyListView view) {
        this.myList = myList;
        this.myListView = view;

        initializeView();
        registerListeners();
    }

    /**
     * Initializes the view by populating the table with the current MyList data.
     */
    private void initializeView() {
        myListView.displayMyListTable(myList.getMyListFilms().toArray(new String[0][]));
    }

    /**
     * Registers listeners for the export and import buttons.
     */
    private void registerListeners() {
        registerExportButtonListener();
        registerImportButtonListener();
        registerClearListener();
    }

    /**
     * Registers the listener for the export button.
     */
    private void registerExportButtonListener() {
        myListView.getExportListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExportAction();
            }
        });
    }

    /**
     * Handles the export action by exporting the current list to a CSV file.
     */
    private void handleExportAction() {
        myList.exportMyList();
    }

    /**
     * Registers the listener for the import button.
     */
    private void registerImportButtonListener() {
        myListView.getImportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleImportAction();
            }
        });
    }

    /**
     * Handles the import action by allowing the user to select a CSV file and loading its content into MyList.
     */
    private void handleImportAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV file to import");

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            myList.loadMyListFromCSV(fileToLoad);
            myList.saveMyListToCSV(new File("mylist.csv"));  // Save the updated list to "mylist.csv"
            JOptionPane.showMessageDialog(myListView, "Your list has been updated!");
            updateViewWithNewList();
        } else {
            JOptionPane.showMessageDialog(myListView, "Import canceled by user.");
        }
    }
    
    /**
     * Registers the listener for the delete button.
     */
    private void registerClearListener() {
        myListView.getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleClearAction();
            }
        });
    }

    /**
     * Handles the clearing action by clearing the list and updating the view.
     */
    private void handleClearAction() {
        // Confirm action with the user
        int confirmation = JOptionPane.showConfirmDialog(
            myListView,
            "Do you want to clear the list?",
            "Clear Mylist",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            myList.clearCSVFile();

            // Clear the in-memory list as well
            myListView.getTableModel().setRowCount(0);
            myListView.refreshTableData(); // Refresh the table view
        } else {
            JOptionPane.showMessageDialog(myListView, "Clearing canceled by user.");
        }
    }


    /**
     * Updates the view by refreshing the table with the new MyList data.
     */
    private void updateViewWithNewList() {
        myListView.displayMyListTable(myList.getMyListFilms().toArray(new String[0][]));
    }
}