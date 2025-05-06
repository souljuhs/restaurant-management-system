package com.fantastic.restaurant;

import java.util.List;

/**
 * InventoryDAO interface defines data access methods for managing inventory items in the system.
 * This interface ensures that CRUD (Create, Read, Update, Delete) operations can be performed on inventory data.
 * 
 * - Maintainability: The interface is designed to be flexible and extendable, supporting evolving business requirements.
 * - Dependability and Security: By extending the DAO interface, it guarantees secure and reliable access to inventory data.
 * - Efficiency: The interface is optimized for handling large volumes of inventory data with minimal overhead.
 * - Acceptability: The interface is simple and intuitive, ensuring it can be easily used by developers and integrated with other components.
 */
public interface InventoryDAO extends DAO<Inventory> {

    /**
     * Updates the quantity of an inventory item based on the item name.
     * This method ensures that the item’s inventory count can be modified securely.
     * 
     * @param itemName the name of the inventory item to update.
     * @param quantity the new quantity of the inventory item.
     * @throws DAOException if any database access error occurs.
     */
    void updateQuantityByName(String itemName, int quantity) throws DAOException;

    /**
     * Retrieves an inventory item by its name.
     * This method ensures that the item can be fetched efficiently from the database.
     * 
     * @param itemName the name of the inventory item to retrieve.
     * @return the inventory item with the specified name.
     * @throws DAOException if any database access error occurs.
     */
    Inventory getInventoryByName(String itemName) throws DAOException;

    /**
     * Deletes an inventory item by its name from the system.
     * This method ensures that inventory items can be securely removed.
     * 
     * @param itemName the name of the inventory item to delete.
     * @throws DAOException if any database access error occurs.
     */
    void deleteInventoryByName(String itemName) throws DAOException;

    /**
     * Retrieves all inventory items from the database.
     * This method ensures that all inventory items can be fetched for display or processing.
     * 
     * @return a list of all inventory items.
     * @throws DAOException if any database access error occurs.
     */
    List<Inventory> getAllInventoryItems() throws DAOException;
}

