package com.myproject.MavenExamProject.view;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * ReviewView is a JPanel that provides a graphical interface for displaying and
 * creating movie reviews. It includes a main panel for recent reviews, and an
 * option to open a new window for creating a new review.
 * 
 * @author Marius
 * @author Vendela
 */
public class ReviewView extends JPanel {
	private JButton addReviewButton;
	private JButton saveButton;
	private JButton cancelButton;
	private JComboBox<String> titleBox;
	private JTextField usernameField;
	private JTextArea reviewArea;
	private JTextArea reviewContent;
	private JFrame newReviewFrame;

	/**
	 * Constructs the ReviewView, initializing the layout and components.
	 * 
	 * @author Marius
	 */
	public ReviewView() {

		initializeContent();
	}

	/**
	 * Initializes the components and layout of the view.
	 * 
	 * @author Marius
	 */
	private void initializeContent() {
		setLayout(new BorderLayout(10, 5));

		// Creates a panel for the top elements (JLabel and JButton)
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout()); // Places the components to the left and right side

		// JLabel for "Recent reviews"
		JLabel reviewsText = new JLabel("Recent reviews");
		reviewsText.setFont(new Font("Arial", Font.BOLD, 35));
		topPanel.add(reviewsText, BorderLayout.WEST); // Places the panel on the left side

		// JButton for "Create new review"
		addReviewButton = new JButton("Create new review");
		addReviewButton.setPreferredSize(new Dimension(175, 40)); // Adjusts the size for a better fit
		topPanel.add(addReviewButton, BorderLayout.EAST);

		// Adds topPanel at the top in BorderLayout
		add(topPanel, BorderLayout.NORTH);

		// Creates a JTextArea with a JScrollPanel.
		reviewContent = new JTextArea();
		reviewContent.setEditable(false); // Sets the textarea to non-editable
		reviewContent.setLineWrap(true);
		reviewContent.setWrapStyleWord(true);

		JScrollPane scroll = new JScrollPane(reviewContent);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Sets a border and a background color for JTextArea
		reviewContent.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		reviewContent.setBackground(new Color(240, 240, 240));

		// Adds scroll-panel in the middle of BorderLayout
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Method to fill out the review textArea
	 * 
	 * @param reviews Takes in a string containing all the reviews
	 * @author Vendela
	 */
	public void fillOutReviewArea(String reviews) {
		reviewContent.setText(""); // Clear the existing text
		reviewContent.append(reviews); // Display updated reviews
		reviewContent.setCaretPosition(0);
	}

	/**
	 * Closes the new review window if it is open.
	 * 
	 * @author Marius
	 */
	public void closeNewReviewWindow() {
		if (newReviewFrame != null) {
			newReviewFrame.dispose();
		}
	}

	/**
	 * Opens a new JFrame window for creating a review, which includes fields for
	 * username, film title, and review content, along with a save button.
	 * 
	 * @author Marius
	 */
	public void openNewReviewWindow() {
		newReviewFrame = new JFrame("Create New Review");
		newReviewFrame.setSize(500, 500);
		newReviewFrame.setLayout(new GridBagLayout());
		newReviewFrame.setLocationRelativeTo(null);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

		// JLabel and JTextField for "Username"
		JLabel usernameLabel = new JLabel("Username:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		newReviewFrame.add(usernameLabel, gbc);

		usernameField = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		usernameField.setText("MovieLover123");
		usernameField.setEditable(false);
		newReviewFrame.add(usernameField, gbc);

		// JLabel and JTextField for "Film Title"
		JLabel titleLabel = new JLabel("Film Title:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		newReviewFrame.add(titleLabel, gbc);

		titleBox = new JComboBox<>();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		newReviewFrame.add(titleBox, gbc);

		// JLabel and JTextArea for "Review"
		JLabel reviewLabel = new JLabel("Review:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		newReviewFrame.add(reviewLabel, gbc);

		reviewArea = new JTextArea(10, 20);
		reviewArea.setLineWrap(true);
		reviewArea.setWrapStyleWord(true);
		JScrollPane reviewScroll = new JScrollPane(reviewArea);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		newReviewFrame.add(reviewScroll, gbc);

		cancelButton = new JButton("Cancel");
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		newReviewFrame.add(cancelButton, gbc);

		// Add a button for saving the review
		saveButton = new JButton("Save Review");
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		newReviewFrame.add(saveButton, gbc);

		// Show the new review window
		newReviewFrame.setVisible(true);
		newReviewFrame.setResizable(false);
	}

	/**
	 * Method for setting the film titles as items in the JComboBox
	 * 
	 * @param filmTitles Takes in an ArrayList of FilmTitles
	 * @author vendela
	 */
	public void setFilmTitlesForComboBox(ArrayList<String> filmTitles) {
		titleBox.removeAllItems();
		for (String title : filmTitles) {
			titleBox.addItem(title);
		}
		titleBox.revalidate();
		titleBox.repaint();
	}

	/**
	 * Method for getting the selected film title from the comboBox
	 * 
	 * @return the title selected in the comboBox
	 * @author vendela
	 */
	public String getFilmTitleInput() {
		if (titleBox.getSelectedItem() != null) {
			return titleBox.getSelectedItem().toString();
		} else {
			return "";
		}
	}

	/**
	 * Returns the JButton used to cancel creating a new review
	 * 
	 * @return the cancelButton button
	 * @author Marius
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}

	/**
	 * Returns the JButton used to add a new review.
	 * 
	 * @return the addReviewButton JButton.
	 * @author Marius
	 */
	public JButton getAddReviewButton() {
		return addReviewButton;
	}

	/**
	 * Returns the JButton used to save a new review.
	 * 
	 * @return the saveButton JButton.
	 * @author Marius
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * Retrieves the input from the username field in the new review window.
	 * 
	 * @return the username input as a String.
	 * @author Marius
	 */
	public String getUsernameInput() {
		return usernameField.getText();
	}

	/**
	 * Retrieves the input from the review text area in the new review window.
	 * 
	 * @return the review text input as a String.
	 * @author Marius
	 */
	public String getReviewTextInput() {
		return reviewArea.getText();
	}

}
