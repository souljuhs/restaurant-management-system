package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implements the MenuDAO interface to manage menu items in the system.
 * This class handles the interaction with the database to add, view, 
 * update, and delete menu items.
 * 
 * - Maintainability: The code is modular and easy to maintain with clear exception handling and resource management.
 * - Dependability and Security: The database interactions are handled with proper exception handling, and connections/statements are closed appropriately to avoid resource leaks.
 * - Efficiency: The database access methods are efficient, using prepared statements for querying and updating the database.
 * - Acceptability: The methods are clear and provide a simple interface for interacting with the menu data.
 */
public class MenuDAOImplement implements MenuDAO {

    /**
     * Adds a new menu item to the database.
     * 
     * @param item the menu item to add to the database.
     * @throws SQLException if an error occurs while adding the item to the database.
     */
    @Override
    public void add(Menu item) throws SQLException {
        String sqlStatement = "INSERT INTO Menu_Items (name, description, price, category, availability) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sqlStatement)) {

            // Set values for the prepared statement
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getCost());
            statement.setString(4, item.getCategory());
            statement.setInt(5, item.getAvailability());

            // Execute the update
            statement.executeUpdate();
            System.out.println("Menu item has been added to the Menu_Items table!");

        } catch (SQLException e) {
            // Log and rethrow SQLException for calling methods to handle
            System.err.println("Error while adding menu item: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves and displays all menu items from the database.
     * 
     * @throws SQLException if an error occurs while retrieving the menu items.
     */
    @Override
    public void view() throws SQLException {
        String sqlStatement = "SELECT * FROM Menu_Items";
        
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sqlStatement);
             ResultSet rs = statement.executeQuery()) {

            // Print the column titles
            System.out.printf("%-3s %-8s %-55s %-8s %-15s %-10s\n", "ID", "Name", "Description", "Price", "Category", "Availability");

            // Print the rows from the result set
            while (rs.next()) {
                int id = rs.getInt("menu_item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double cost = rs.getDouble("price");
                String category = rs.getString("category");
                int available = rs.getInt("availability");

                System.out.printf("%-3d %-8s %-55s %-8.2f %-15s %-10d\n", id, name, description, cost, category, available);
            }
        } catch (SQLException e) {
            // Log and rethrow SQLException for calling methods to handle
            System.err.println("Error while viewing menu items: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves a menu item by its unique identifier.
     * 
     * @param id the unique identifier of the menu item.
     * @return the menu item corresponding to the given id, or null if not found.
     * @throws DAOException if an error occurs while accessing the database.
     */
    @Override
    public Menu getMenuItemById(int id) throws DAOException {
        // Placeholder for future implementation
        throw new UnsupportedOperationException("Method not implemented yet.");
    }

    /**
     * Updates the details of an existing menu item in the database.
     * 
     * @param menu the updated menu item to save.
     * @return true if the update was successful, false otherwise.
     * @throws DAOException if an error occurs while updating the item.
     */
    @Override
    public boolean updateMenuItem(Menu menu) throws DAOException {
        // Placeholder for future implementation
        throw new UnsupportedOperationException("Method not implemented yet.");
    }

    /**
     * Deletes a menu item from the database by its unique identifier.
     * 
     * @param id the unique identifier of the menu item to delete.
     * @return true if the deletion was successful, false otherwise.
     * @throws DAOException if an error occurs while deleting the item.
     */
    @Override
    public boolean deleteMenuItem(int id) throws DAOException {
        // Placeholder for future implementation
        throw new UnsupportedOperationException("Method not implemented yet.");
    }
}

