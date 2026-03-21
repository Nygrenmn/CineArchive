package com.myproject.MavenExamProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableViewer {

    public static void showActorTable(JPanel mainPanel, CardLayout cardLayout) {
        JPanel actorPanel = UIManager.createActorPanel();
        DefaultTableModel actorTableModel = new DefaultTableModel();
        JTable actorTable = new JTable(actorTableModel);
        JScrollPane actorScrollPane = new JScrollPane(actorTable);
        actorPanel.add(actorScrollPane, BorderLayout.CENTER);

        Database.fetchActorData(actorTableModel);

        addBackButton(actorPanel, mainPanel, cardLayout);
        mainPanel.add(actorPanel, "ActorTable");
        cardLayout.show(mainPanel, "ActorTable");
    }

    public static void showFilmTable(JPanel mainPanel, CardLayout cardLayout) {
        JPanel filmPanel = UIManager.createFilmPanel();
        DefaultTableModel filmTableModel = new DefaultTableModel();
        JTable filmTable = new JTable(filmTableModel);
        JScrollPane filmScrollPane = new JScrollPane(filmTable);
        filmPanel.add(filmScrollPane, BorderLayout.CENTER);

        Database.fetchFilmData(filmTableModel);

        addBackButton(filmPanel, mainPanel, cardLayout);
        mainPanel.add(filmPanel, "FilmTable");
        cardLayout.show(mainPanel, "FilmTable");
    }

    private static void addBackButton(JPanel panel, JPanel mainPanel, CardLayout cardLayout) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "StartScreen"));
        panel.add(backButton, BorderLayout.SOUTH);
    }
}
