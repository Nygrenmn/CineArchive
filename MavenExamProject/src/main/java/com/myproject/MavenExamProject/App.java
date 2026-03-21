package com.myproject.MavenExamProject;

import com.myproject.MavenExamProject.controller.ReviewHandler;
import com.myproject.MavenExamProject.view.MainView;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws IOException {
        // Define the file path
        File file = new File("reviews.txt");
        // Check if the file exists and is empty; if so, generate dummy reviews
        if (!file.exists() || file.length() == 0) {
            // Generate the file and dummy reviews if it doesn't exist or is empty
			ReviewHandler.generateDummyReviews();
        }
        // Launch the MainView using Swing's event dispatch thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainView();  // Create and show the main window
            }
        });
    }
}