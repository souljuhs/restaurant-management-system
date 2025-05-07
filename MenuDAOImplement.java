package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	 private Connection connection;

	 public MenuDAOImplement(Connection connection) {
	     this.connection = connection;
	 }
    /**
     * Adds a new menu item to the database, including menu_id and subgroup_id.
     * 
     * @param item the menu item to add to the database.
     * @throws SQLException if an error occurs while adding the item to the database.
     */
    @Override
    public void add(Menu item) throws SQLException {
        String sqlStatement = "INSERT INTO Menu_Items (name, description, price, category, availability, menu_id, subgroup_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sqlStatement)) {

            // Set values for the prepared statement
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getCost());
            statement.setString(4, item.getCategory());
            statement.setInt(5, item.getAvailability());
            statement.setInt(6, item.getMenuId()); // Set menu ID
            statement.setInt(7, item.getSubgroupId()); // Set subgroup ID

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
            System.out.printf("%-3s %-8s %-55s %-8s %-15s %-10s %-10s %-10s\n", "ID", "Name", "Description", "Price", "Category", "Availability", "Menu ID", "Subgroup ID");

            // Print the rows from the result set
            while (rs.next()) {
                int id = rs.getInt("menu_item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double cost = rs.getDouble("price");
                String category = rs.getString("category");
                int available = rs.getInt("availability");
                int menuId = rs.getInt("menu_id"); // Get menu ID
                int subgroupId = rs.getInt("subgroup_id"); // Get subgroup ID

                System.out.printf("%-3d %-8s %-55s %-8.2f %-15s %-10d %-10d %-10d\n", id, name, description, cost, category, available, menuId, subgroupId);
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
        String sqlStatement = "SELECT * FROM Menu_Items WHERE menu_item_id = ?";

        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sqlStatement)) {
             
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                double cost = rs.getDouble("price");
                String category = rs.getString("category");
                int availability = rs.getInt("availability");
                int menuId = rs.getInt("menu_id");
                int subgroupId = rs.getInt("subgroup_id");
                
                // Return a new Menu object populated with the retrieved values
                return new Menu(name, description, cost, category, availability, menuId, subgroupId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while retrieving menu item by ID: " + e.getMessage(), e);
        }

        return null; // Return null if not found
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
        String sqlStatement = "UPDATE Menu_Items SET name = ?, description = ?, price = ?, category = ?, availability = ?, menu_id = ?, subgroup_id = ? WHERE menu_item_id = ?";

        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sqlStatement)) {
             
            statement.setString(1, menu.getName());
            statement.setString(2, menu.getDescription());
            statement.setDouble(3, menu.getCost());
            statement.setString(4, menu.getCategory());
            statement.setInt(5, menu.getAvailability());
            statement.setInt(6, menu.getMenuId());
            statement.setInt(7, menu.getSubgroupId());
            statement.setInt(8, menu.getMenuId()); // Using menu_id as identifier; you might want to adapt this logic based on your application

            return statement.executeUpdate() > 0; // Returns true if at least one row was updated
        } catch (SQLException e) {
            throw new DAOException("Error while updating menu item: " + e.getMessage(), e);
        }
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
        String sqlStatement = "DELETE FROM Menu_Items WHERE menu_item_id = ?";

        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sqlStatement)) {
             
            statement.setInt(1, id);
            return statement.executeUpdate() > 0; // Returns true if at least one row was deleted
        } catch (SQLException e) {
            throw new DAOException("Error while deleting menu item: " + e.getMessage(), e);
        }
    }
    
    public int addFullMenu(String name, String posName, List<String> subgroups) throws SQLException {
        String menuSql = "INSERT INTO menus (name, pos_name) VALUES (?, ?)";
        String subgroupSql = "INSERT INTO menu_subgroups (menu_id, subgroup_name) VALUES (?, ?)";
        
        // Use the instance's connection, not a new one
        connection.setAutoCommit(false);

        try (
            PreparedStatement menuStmt = connection.prepareStatement(menuSql, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement subgroupStmt = connection.prepareStatement(subgroupSql)
        ) {
            // Insert menu
            menuStmt.setString(1, name);
            menuStmt.setString(2, posName);
            menuStmt.executeUpdate();

            ResultSet rs = menuStmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new SQLException("Failed to retrieve menu ID.");
            }
            int menuId = rs.getInt(1);

            // Insert subgroups
            for (String subgroupName : subgroups) {
                subgroupStmt.setInt(1, menuId);
                subgroupStmt.setString(2, subgroupName);
                subgroupStmt.addBatch();
            }

            subgroupStmt.executeBatch();
            connection.commit();
            System.out.println("Menu and subgroups added successfully!");

            return menuId;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
