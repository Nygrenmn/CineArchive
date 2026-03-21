package com.myproject.MavenExamProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIManager {
	
	 private static void addBackButton(JPanel panel, JPanel mainPanel, CardLayout cardLayout) {
	        JButton backButton = new JButton("Back");
	        backButton.addActionListener(e -> cardLayout.show(mainPanel, "StartScreen"));
	        panel.add(backButton, BorderLayout.SOUTH);
	        
	        // Revalidate and repaint to refresh the UI
	        panel.revalidate();
	        panel.repaint();
	    }

    public static JPanel createStartPanel(JPanel mainPanel, CardLayout cardLayout) {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(null);
        
        //Added login button at the top
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 20, 300, 40);
        startPanel.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 80, 300, 40);
        startPanel.add(registerButton);
        
        /*JButton actorButton = new JButton("Show Actor Table");
        actorButton.setBounds(150, 140, 300, 40);
        startPanel.add(actorButton);

        JButton filmButton = new JButton("Show Film Table");
        filmButton.setBounds(150, 210, 300, 40);
        startPanel.add(filmButton);*/

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPanel(mainPanel, cardLayout);  // Call method to display login form
            }
        });
        
     // Action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterPanel(mainPanel, cardLayout);  // Call method to display registration form
            }
        });
        
       /* actorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableViewer.showActorTable(mainPanel, cardLayout);
            }
        });

        filmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableViewer.showFilmTable(mainPanel, cardLayout);
            }
        });*/

        return startPanel;
    }

    public static JPanel createActorPanel() {
        return new JPanel(new BorderLayout());
    }

    public static JPanel createFilmPanel() {
        return new JPanel(new BorderLayout());
    }
    
    // Method to display the login form
    public static void showLoginPanel(JPanel mainPanel, CardLayout cardLayout) {
        // Create a new panel for login with BorderLayout
        JPanel loginPanel = new JPanel(new BorderLayout());

        // Create a panel for form fields with null layout
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 100, 100, 25);
        formPanel.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 100, 150, 25);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 150, 100, 25);
        formPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 150, 150, 25);
        formPanel.add(passwordField);

        JButton loginSubmitButton = new JButton("Submit");
        loginSubmitButton.setBounds(200, 200, 150, 40);
        formPanel.add(loginSubmitButton);

        // Add formPanel to the center of loginPanel
        loginPanel.add(formPanel, BorderLayout.CENTER);

        // Action listener for the submit button
        loginSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Call method from DatabaseHelper to validate credentials
                boolean isValid = Database.validateLogin(username, password);

                if (isValid) {
                    // Show success message and proceed to main application
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    cardLayout.show(mainPanel, "StartScreen");  // Navigate back to the start screen
                } else {
                    // Show error message
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        // Add back button to the bottom of loginPanel
        addBackButton(loginPanel, mainPanel, cardLayout);

        mainPanel.add(loginPanel, "LoginScreen");  // Add login panel to the card layout
        cardLayout.show(mainPanel, "LoginScreen");  // Show the login screen
    }
    
    public static void showRegisterPanel(JPanel mainPanel, CardLayout cardLayout) {
        // Create a new panel for registration with BorderLayout
        JPanel registrationPanel = new JPanel(new BorderLayout());
        
        // Create a panel for form fields with null layout
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);

        // Create and add input fields for registration
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(100, 30, 100, 25);
        formPanel.add(firstNameLabel);
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(200, 30, 150, 25);
        formPanel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(100, 70, 100, 25);
        formPanel.add(lastNameLabel);
        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(200, 70, 150, 25);
        formPanel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100, 110, 100, 25);
        formPanel.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(200, 110, 150, 25);
        formPanel.add(emailField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(100, 150, 100, 25);
        formPanel.add(usernameLabel);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 150, 150, 25);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 190, 100, 25);
        formPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 190, 150, 25);
        formPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(200, 230, 150, 40);
        formPanel.add(registerButton);

        // Action listener for the register button
        registerButton.addActionListener(e -> {
            // Retrieve the values from the input fields
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validate and insert into the database
            boolean success = Database.insertNewUser(firstName, lastName, email, username, password);

            if (success) {
                JOptionPane.showMessageDialog(null, "Registration successful!");
                cardLayout.show(mainPanel, "StartScreen");  // Navigate back to the start screen after registration
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
            }
        });

        // Add formPanel to the center of registrationPanel
        registrationPanel.add(formPanel, BorderLayout.CENTER);

        // Add back button to the bottom of registrationPanel
        addBackButton(registrationPanel, mainPanel, cardLayout);

        mainPanel.add(registrationPanel, "RegistrationScreen");  // Add registration panel to the card layout
        cardLayout.show(mainPanel, "RegistrationScreen");  // Show the registration screen
    }


}


