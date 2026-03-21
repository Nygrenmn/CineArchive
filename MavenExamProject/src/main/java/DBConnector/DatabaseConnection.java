package DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the database connection to the MySQL "sakila" database. The connection details (URL, username, password) are 
 * set as constants.
 * 
 * This class provides methods for establish and retrieve a database connection, as well as a private DatabaseConnection() 
 * to ensure one instance of the class and a close connection for good practice.
 * 
 * @author MartinN
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "student";
    /** 
     * Singleton pattern instance of the database connection. 
     * Ensures that only one connection is used throughout the application.
     */
    private static Connection connection; 

    /**
     * Private constructor to prevent instantiation of the class, as it only provides static methods. This is done 
     * by making it impossible for other classes to create an object of DatabaseConnection using new DatabaseConnection().
     * It ensures that only one instance of the class (the database connection) exists, which is managed via the 
     * static method getConnection()
     */
    private DatabaseConnection() {}

    /**
     * Retrieves the singleton database connection instance. 
     * Creates a new connection if none exists or if the existing connection is closed.
     *
     * @return The connection to the "sakila" database.
     * @throws SQLException If an error occurs while establishing the connection.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
        	// DriverManager is a class in java.sql, attempts to locate the database specified in the parameter.
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    /**
     * Closes the database connection if it is open. 
     * This method is not currently used but is included as a best practice to release database resources.
     *
     * @throws SQLException If an error occurs while closing the connection.
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
