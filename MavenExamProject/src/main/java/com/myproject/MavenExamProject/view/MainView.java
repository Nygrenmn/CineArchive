package com.myproject.MavenExamProject.view;

import com.myproject.MavenExamProject.controller.*;
import com.myproject.MavenExamProject.model.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;

/**
 * MainView class defines the main application interface, including the main frame.
 * It initializes and manages the views and handlers of the application.
 * 
 * @author Atle
 *
 */
public class MainView {
	private JFrame mainFrame;        		// The main frame of the application.
	private JPanel panelContainer;   		// The container panel for holding views with CardLayout.
	private CardLayout cardLayout;   		// The layout manager for switching between views.
	private MyListHandler myListHandler; 	// Handles operations for managing the movie list.
	private MyList myList;          		// The model for the user's movie list.
	private MyListView myListPage;  		// The view for displaying the user's movie list.
	private ReviewHandler reviewHandler; 	// Manages review-related functionality.
	private FilmsView filmsPage;    		// The view for displaying films and searching.
	private ReviewView reviewPage;  		// The view for displaying and managing reviews.

    /**
     * Initializes the different methods within the class, creating the main view
     */
    public MainView() {
        initializeMyList();
        initializeMainFrame();
        initializePanelContainer();
        initializeViews();
        initializeMainPanel();
        initializeActionsMenu();
        initializeHandlers();
        setDefaultView();
    }

    /**
     * Initializes the user's movie list. 
     * Creates a new MyList instance and loads data from a CSV file.
     */
    private void initializeMyList() {
        myList = new MyList();
        myList.loadMyListFromCSV(new File("mylist.csv"));
    }

    /**
     * Sets up the main application frame. 
     * Configures the title, close operation, maximized state, and initial size.
     */
    private void initializeMainFrame() {
        mainFrame = new JFrame("CineArchive");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setSize(800, 600);
    }

    /**
     * Initializes the panel container for the application. 
     * Uses a CardLayout to enable switching between different panels.
     */
    private void initializePanelContainer() {
        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);
    }

    /**
     * Initializes the views used in the application. 
     * Creates and configures each view, setting their backgrounds and adding them to the panel container.
     */
    private void initializeViews() {
        FrontPage frontPage = new FrontPage();
        frontPage.setBackground(Color.decode("#E6E6FA"));

        filmsPage = new FilmsView();
        filmsPage.setBackground(Color.decode("#E6E6FA"));

        reviewPage = new ReviewView();
        reviewPage.setBackground(Color.decode("#F0F0F0"));

        myListPage = new MyListView(myList);
        myListPage.setBackground(Color.decode("#E6E6FA"));

        panelContainer.add(frontPage, "FrontPage");
        panelContainer.add(filmsPage, "FilmsPage");
        panelContainer.add(reviewPage, "ReviewPage");
        panelContainer.add(myListPage, "myListPage");

    }

    /**
     * Initializes the main panel of the application. 
     * Sets up the layout, background color, headline label, and navigation panel. 
     * Adds the main panel and panel container to the main application frame.
     */
    private void initializeMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.decode("#87CEFA"));

        JLabel headline = new JLabel("CineArchive", JLabel.CENTER);
        headline.setFont(new Font("Arial", Font.BOLD, 40));
        mainPanel.add(headline, BorderLayout.NORTH);

        JPanel navPanel = createNavPanel();
        mainPanel.add(navPanel, BorderLayout.CENTER);
        mainPanel.add(navPanel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel, BorderLayout.NORTH);
        mainFrame.add(panelContainer, BorderLayout.CENTER);
    }

    /**
     * Initializes the actions menu for the application. 
     * Creates the ActionsMenuView and retrieves its menu bar, then sets it on the main frame. 
     * Handles the creation of an ActionsHandler to link menu actions to their behavior.
     *
     * @throws SQLException If an error occurs while initializing the ActionsHandler.
     * @author Oda
     */
    private void initializeActionsMenu() {
        ActionsMenuView actionsMenuView = new ActionsMenuView();
        JMenuBar menuBar = actionsMenuView.getMenuBar();
        try {
			ActionsHandler actionsHandler = new ActionsHandler(actionsMenuView);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
        mainFrame.setJMenuBar(menuBar);
    }

    private void initializeHandlers() {
        myListHandler = new MyListHandler(myList, myListPage);
        reviewHandler = new ReviewHandler(reviewPage);
        
        FilmDAO filmDAO = new FilmDAO();
		new FilmHandler(filmDAO, filmsPage);
    }
    
    
    /**
     * Sets the default view of the application. 
     * Displays the front page and makes the main frame visible.
     */
    private void setDefaultView() {
        cardLayout.show(panelContainer, "FrontPage");
        mainFrame.setVisible(true);
    }

   

    /**
     * Helper method to create the navigation panel. 
     * Configures the panel's layout, background color, and border, then adds the navigation buttons to it.
     *
     * @return JPanel The navigation panel containing buttons for switching between views.
     */
    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        navPanel.setBackground(Color.decode("#87CEFA"));
        navPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

        JButton frontPageButton = createNavButton("Front Page", "FrontPage");
        JButton filmsButton = createNavButton("Films", "FilmsPage");
        JButton myListButton = createNavButton("My List", "myListPage");
        JButton reviewButton = createNavButton("Reviews", "ReviewPage");

        navPanel.add(frontPageButton);
        navPanel.add(filmsButton);
        navPanel.add(myListButton);
        navPanel.add(reviewButton);

        return navPanel;
    }

    /**
     * Creates a navigation button with the specified title and page association. 
     * Configures the button's appearance, sets its action listener, and returns the button.
     * When clicked, the button switches the displayed page, and refreshes the "My List" page if selected.
     *
     * @param title The text to display on the button.
     * @param page The name of the page to show when the button is clicked.
     * @return JButton The created navigation button.
     */
    private JButton createNavButton(String title, String page) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font(null, Font.BOLD, 14));
        button.addActionListener(e -> {
            if ("myListPage".equals(page)) {
                refreshMyListPage();
            }
            cardLayout.show(panelContainer, page);
        });
        return button;
    }
    
    /**
     * Refreshes the content of the "My List" page. 
     * If the myListPage is not null, it calls the refreshTableData method to update the data displayed on the page.
     * 
     * @MartinU
     */
    public void refreshMyListPage() {
        if (myListPage != null) {
            myListPage.refreshTableData();
        }
    }
}