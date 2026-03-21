package com.myproject.MavenExamProject.view;

import javax.swing.*;
import java.awt.*;

/**
 * The class represents the user interface for the front page of the application
 * It provides the user with the application name and its features.
 * Extends JPanel to provide the UI as a panel.
 * 
 * @author Vendela
 * @author Atle
 */

public class FrontPage extends JPanel {
	
    private JLabel welcomeLabel;	// Field for the welcome label
    private JLabel infoLabel;		// Field for the info label, explaining features

    /**
     * Constructor initializing the method in the class, creating the front page content
     */
    public FrontPage() {
        initializecontent();
    }

    /**
     * This method initializes the content of the panel. 
     * It sets up two JLabels and positions them in the center using GridBagLayout.
     */
    private void initializecontent() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        welcomeLabel = new JLabel("Welcome to CineArchive - The best place to explore the world of cinema!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        add(welcomeLabel, gbc);

        infoLabel = new JLabel("Browse and search films, customize your own film lists and write reviews!");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(infoLabel, gbc);
    }
}