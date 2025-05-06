package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the RestaurantTableDAO interface to manage restaurant tables
 * in the database.
 * This class provides methods to create, read, update, and delete restaurant table records.
 * It interacts with the database using SQL statements.
 * 
 * Features:
 * Maintainability: Modular design, easy to extend with additional features such as table availability checks.
 * Security: SQL injection is prevented through the use of prepared statements.
 * Efficiency: Efficient handling of database connections and queries using try-with-resources for automatic cleanup.
 * Acceptability: Clear method names and consistent use of Java conventions ensure ease of use.
 */
public class RestaurantTableDAOImplement implements RestaurantTableDAO {

    /**
     * Adds a new RestaurantTable to the database.
     * This method inserts a new table record with its table number, capacity, and status.
     * 
     * @param table the RestaurantTable object to be added.
     * @return true if the operation is successful, false otherwise.
     * @throws DAOException If there is an error while interacting with the database.
     */
    @Override
    public boolean save(RestaurantTable table) throws DAOException {
        try (Connection connect = Database.getConnection()) {
            String sqlStatement = "INSERT INTO Tables (table_number, capacity, status) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connect.prepareStatement(sqlStatement)) {
                statement.setInt(1, table.getTableNumber());
                statement.setInt(2, table.getTableCapacity());
                statement.setString(3, table.getTableStatus());
                statement.executeUpdate();
                System.out.println("Table Created!");
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while adding table to the database", e);
        }
    }

    /**
     * Deletes a RestaurantTable from the database by its table number.
     * This method removes the table from the database based on the table number.
     * 
     * @param tableNumber the table number of the table to be deleted.
     * @return true if the deletion is successful, false otherwise.
     * @throws DAOException If an error occurs while interacting with the database.
     */
    @Override
    public boolean delete(int tableNumber) throws DAOException {
        try (Connection connect = Database.getConnection()) {
            String sqlStatement = "DELETE FROM Tables WHERE table_number = ?";
            try (PreparedStatement statement = connect.prepareStatement(sqlStatement)) {
                statement.setInt(1, tableNumber);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Table Deleted!");
                    return true;
                } else {
                    System.out.println("Table not found!");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while deleting table from the database", e);
        }
    }

    /**
     * Adds a new RestaurantTable to the database (alternative method).
     * This method inserts a new table using the provided RestaurantTable object.
     * 
     * @param t the RestaurantTable object to be added.
     * @throws SQLException If there is an error during the SQL query execution.
     */
    @Override
    public void add(RestaurantTable t) throws SQLException {
        Connection connect = null;
        PreparedStatement statement = null;

        try {
            connect = Database.getConnection();
            String sql = "INSERT INTO Tables (table_number, capacity, status) VALUES (?, ?, ?)";
            statement = connect.prepareStatement(sql);
            statement.setInt(1, t.getTableNumber());
            statement.setInt(2, t.getTableCapacity());
            statement.setString(3, t.getTableStatus());

            statement.executeUpdate();
            System.out.println("Table added via add() method.");
        } catch (SQLException e) {
            System.err.println("Error occurred while adding table: " + e.getMessage());
            throw e;  // Rethrow so the calling method is aware of the failure
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.err.println("Failed to close statement: " + ex.getMessage());
                }
            }
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException ex) {
                    System.err.println("Failed to close connection: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Displays all RestaurantTable records from the database.
     * This method retrieves all the tables from the database and prints their details (table number, capacity, status).
     * 
     * @throws SQLException If there is an error while executing the SQL query.
     */
    @Override
    public void view() throws SQLException {
        String sql = "SELECT * FROM Tables";
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int tableNumber = resultSet.getInt("table_number");
                int capacity = resultSet.getInt("capacity");
                String status = resultSet.getString("status");
                System.out.println("Table Number: " + tableNumber + ", Capacity: " + capacity + ", Status: " + status);
            }
        }
    }

    /**
     * Retrieves a RestaurantTable by its ID.
     * This method is not implemented yet and returns null.
     * 
     * @param id The ID of the table to retrieve.
     * @return null (functionality not yet implemented).
     * @throws DAOException If there is an error during database interaction.
     */
    @Override
    public RestaurantTable getById(int id) throws DAOException {
        // TODO: Implement this method to retrieve a table by its ID.
        return null;
    }

    /**
     * Updates an existing RestaurantTable record in the database.
     * This method modifies the table's capacity and status based on its table number.
     * 
     * @param restaurantTable the RestaurantTable object containing updated information.
     * @return true if the update was successful, false otherwise.
     * @throws DAOException If there is an error while interacting with the database.
     */
    @Override
    public boolean update(RestaurantTable restaurantTable) throws DAOException {
        String sql = "UPDATE Tables SET capacity = ?, status = ? WHERE table_number = ?";
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, restaurantTable.getTableCapacity());
            statement.setString(2, restaurantTable.getTableStatus());
            statement.setInt(3, restaurantTable.getTableNumber());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            throw new DAOException("Error while updating table", e);
        }
    }

    /**
     * Retrieves all RestaurantTable records from the database.
     * This method fetches all the tables and returns them as a list of RestaurantTable objects.
     * 
     * @return a list of RestaurantTable objects containing all table records.
     * @throws DAOException If there is an error while interacting with the database.
     */
    @Override
    public List<RestaurantTable> getAll() throws DAOException {
        String sql = "SELECT * FROM Tables";
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<RestaurantTable> tables = new ArrayList<>();
            while (resultSet.next()) {
                int tableNumber = resultSet.getInt("table_number");
                int capacity = resultSet.getInt("capacity");
                String status = resultSet.getString("status");
                tables.add(new RestaurantTable(tableNumber, capacity, status));
            }
            return tables;
        } catch (SQLException e) {
            throw new DAOException("Error while fetching all tables", e);
        }
    }
}
