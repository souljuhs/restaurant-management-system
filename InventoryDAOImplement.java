package com.fantastic.restaurant;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the InventoryDAO interface, providing data access methods for managing inventory items.
 * 
 * - Maintainability: Modular methods for adding, viewing, updating, and deleting inventory items ensure ease of future modifications and debugging.
 * - Dependability and Security: Proper exception handling and the use of prepared statements ensure security and reliability.
 * - Efficiency: The use of try-with-resources for automatic resource management and optimized database interactions ensures minimal overhead.
 * - Acceptability: Clear and descriptive method names and error messages improve the developer experience.
 */
public class InventoryDAOImplement implements InventoryDAO {

    private final Connection connection;

    /**
     * Constructor that accepts a Connection object.
     * 
     * @param connection the Connection object used for database interaction
     */
    public InventoryDAOImplement(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new inventory item to the database.
     * 
     * @param item the inventory item to add
     * @throws DAOException if a database error occurs while adding the item
     */
    @Override
    public void add(Inventory item) throws DAOException {
        String sqlStatement = "INSERT INTO Inventory (name, stock_quantity, unit, threshold) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, item.getItemName());
            statement.setInt(2, item.getQuantity());
            statement.setString(3, item.getUnit());
            statement.setInt(4, item.getThreshold());

            statement.executeUpdate();
            System.out.println("Item has been added to inventory!");
            
        } catch (SQLException e) {
            throw new DAOException("Error while adding inventory item: " + item.getItemName(), e);
        }
    }

    /**
     * Views all inventory items in the system.
     * 
     * @throws DAOException if a database error occurs while retrieving the inventory items
     */
    @Override
    public void view() throws DAOException {
        String sqlStatement = "SELECT * FROM Inventory";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement); 
             ResultSet rs = statement.executeQuery()) {

            System.out.printf("%-3s %-12s %-7s %-10s %-7s %-20s\n", "ID", "Name", "Quantity", "Unit", "Threshold", "Last Updated");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                int quantity = rs.getInt("stock_quantity");
                String unit = rs.getString("unit");
                int threshold = rs.getInt("threshold");
                Timestamp updated = rs.getTimestamp("last_updated");

                String timestamp = dateFormat.format(updated);

                System.out.printf("%-3d %-12s %-7d %-10s %-7d %-20s\n", id, name, quantity, unit, threshold, timestamp);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while viewing inventory items.", e);
        }
    }

    /**
     * Updates the quantity of an inventory item by its name.
     * 
     * @param itemName the name of the inventory item to update
     * @param quantity the new quantity for the inventory item
     * @throws DAOException if a database error occurs while updating the item
     */
    @Override
    public void updateQuantityByName(String itemName, int quantity) throws DAOException {
        String sqlStatement = "UPDATE Inventory SET stock_quantity = ? WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, quantity);
            statement.setString(2, itemName);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DAOException("No inventory item found with the name: " + itemName);
            }

            System.out.println("Inventory item quantity updated successfully.");
            
        } catch (SQLException e) {
            throw new DAOException("Error while updating inventory item: " + itemName, e);
        }
    }

    /**
     * Retrieves an inventory item by its name.
     * 
     * @param itemName the name of the inventory item to retrieve
     * @return the Inventory object representing the item, or null if not found
     * @throws DAOException if a database error occurs while retrieving the item
     */
    @Override
    public Inventory getInventoryByName(String itemName) throws DAOException {
        String sqlStatement = "SELECT * FROM Inventory WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, itemName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Inventory(
                        rs.getString("name"),
                        rs.getInt("stock_quantity"),
                        rs.getString("unit"),
                        rs.getInt("threshold")
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while retrieving inventory item: " + itemName, e);
        }
    }

    /**
     * Deletes an inventory item by its name.
     * 
     * @param itemName the name of the inventory item to delete
     * @throws DAOException if a database error occurs while deleting the item
     */
    @Override
    public void deleteInventoryByName(String itemName) throws DAOException {
        String sqlStatement = "DELETE FROM Inventory WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, itemName);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DAOException("No inventory item found with the name: " + itemName);
            }

            System.out.println("Inventory item deleted successfully.");
            
        } catch (SQLException e) {
            throw new DAOException("Error while deleting inventory item: " + itemName, e);
        }
    }

    /**
     * Retrieves all inventory items from the system.
     * 
     * @return a list of all inventory items
     * @throws DAOException if a database error occurs while retrieving the items
     */
    @Override
    public List<Inventory> getAllInventoryItems() throws DAOException {
        List<Inventory> inventoryList = new ArrayList<>();
        String sqlStatement = "SELECT * FROM Inventory";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement); 
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Inventory item = new Inventory(
                    rs.getString("name"),
                    rs.getInt("stock_quantity"),
                    rs.getString("unit"),
                    rs.getInt("threshold")
                );
                inventoryList.add(item);
            }

            return inventoryList;
        } catch (SQLException e) {
            throw new DAOException("Error while retrieving all inventory items.", e);
        }
    }
}


