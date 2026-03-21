package com.myproject.MavenExamProject;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Database {

    static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
    static final String USER = "root"; // replace with your MySQL username
    static final String PASS = "2003"; // replace with your MySQL password

    public static void fetchActorData(DefaultTableModel tableModel) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT actor_id, first_name, last_name FROM actor";
            ResultSet rs = stmt.executeQuery(sql);

            tableModel.addColumn("Actor ID");
            tableModel.addColumn("First Name");
            tableModel.addColumn("Last Name");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("actor_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchFilmData(DefaultTableModel tableModel) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT film_id, title, description FROM film";
            ResultSet rs = stmt.executeQuery(sql);

            tableModel.addColumn("Film ID");
            tableModel.addColumn("Title");
            tableModel.addColumn("Description");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("film_id"),
                        rs.getString("title"),
                        rs.getString("description")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Added login from staff table
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT username, password FROM staff WHERE username = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();  // If a match is found, login is valid

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertNewUser(String firstName, String lastName, String email, String username, String password) {
        String sql = "INSERT INTO staff (first_name, last_name, email, username, password, address_id, store_id, active, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password); // Ensure you hash the password before storing
            pstmt.setInt(6, 1); // Default address_id (change to the desired value)
            pstmt.setInt(7, 1); // Default store_id (change to the desired value)
            pstmt.setBoolean(8, true); // Active (1 for true)
            pstmt.setTimestamp(9, new java.sql.Timestamp(System.currentTimeMillis())); // Set current timestamp

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if an error occurred
        }
    }


}

