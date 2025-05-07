package com.fantastic.restaurant;

/**
 * The MenuDAO interface defines the necessary data access methods for managing
 * menu items in the restaurant's system. This interface ensures that the system
 * can interact with menu data in a consistent and efficient manner.
 * 
 * The interface is designed to be extendable, secure, efficient, and easy to use.
 * 
 * - Maintainability: The interface is designed to be easily extendable to support new methods as needed.
 * - Dependability and Security: Ensures data access is reliable and follows secure access patterns.
 * - Efficiency: Methods in this interface are designed to interact with data sources efficiently.
 * - Acceptability: The interface provides a simple, clear contract for interacting with menu data, ensuring ease of use for developers.
 */
public interface MenuDAO extends DAO<Menu> {

    /**
     * Retrieves a menu item by its unique identifier.
     * 
     * @param id the unique identifier of the menu item
     * @return the menu item corresponding to the given id, or null if not found
     * @throws DAOException if an error occurs while accessing the data source
     */
    Menu getMenuItemById(int id) throws DAOException;

    /**
     * Updates the details of an existing menu item.
     * 
     * @param menu the updated menu item to save
     * @return true if the update was successful, false otherwise
     * @throws DAOException if an error occurs while updating the data
     */
    boolean updateMenuItem(Menu menu) throws DAOException;

    /**
     * Deletes a menu item based on its unique identifier.
     * 
     * @param id the unique identifier of the menu item to delete
     * @return true if the deletion was successful, false otherwise
     * @throws DAOException if an error occurs while deleting the menu item
     */
    boolean deleteMenuItem(int id) throws DAOException;
    
    
}

