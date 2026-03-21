package com.myproject.MavenExamProject;

//New TEST

import javax.swing.*;
import java.awt.*;

public class AppTest {


    static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
    static final String USER = "student"; // replace with your MySQL username
    static final String PASS = "student"; // replace with your MySQL password

    public static void main(String[] args) {
        JFrame frame = new JFrame("Movie App");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use CardLayout for switching between different screens (panels)
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Initialize panels using UIManager
        JPanel startPanel = UIManager.createStartPanel(mainPanel, cardLayout);
        JPanel actorPanel = UIManager.createActorPanel();
        JPanel filmPanel = UIManager.createFilmPanel();

        // Add panels to the main panel (CardLayout)
        mainPanel.add(startPanel, "StartScreen");
        mainPanel.add(actorPanel, "ActorTable");
        mainPanel.add(filmPanel, "FilmTable");

/*
        // Action listeners for buttons to switch panels
        actorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showActorTable(actorPanel, cardLayout, mainPanel);
                cardLayout.show(mainPanel, "ActorTable"); 
            }
        });

        filmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilmTable(filmPanel, cardLayout, mainPanel);
                cardLayout.show(mainPanel, "FilmTable");
            }
        });
*/
        // Add the main panel to the frame and set the frame visible
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
