package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database utility class responsible for providing database connections.
 * This class is designed to manage database connectivity in a secure, reliable, and efficient manner.
 */
public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/restaurant_db";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    private static final Logger logger = Logger.getLogger(Database.class.getName());

    // Private constructor to prevent instantiation
    private Database() {}

    /**
     * Returns a connection to the database. This method handles database connection 
     * creation in a secure and reliable manner.
     * 
     * @return a Connection object to interact with the database.
     * @throws SQLException if the database connection fails.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Attempt to establish a database connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Database connection established successfully.");
            return connection;
        } catch (SQLException e) {
            // Log the error and rethrow the exception
            logger.log(Level.SEVERE, "Failed to establish a database connection.", e);
            throw new SQLException("Unable to connect to the database", e);
        }
    }

    /**
     * Closes the provided database connection to release resources.
     * 
     * @param connection the Connection object to close.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed successfully.");
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Failed to close the database connection.", e);
            }
        }
    }
}
