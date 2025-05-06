package com.fantastic.restaurant;

import java.util.List;

/**
 * Interface representing the Data Access Object (DAO) for RestaurantTable entities.
 * This interface is designed to be flexible and extensible to adapt to future changes
 * in the business environment while ensuring reliability, security, and efficiency.
 * 
 * It inherits the generic DAO interface for CRUD operations on RestaurantTable objects.
 * 
 * Key aspects addressed:
 * - Maintainability: Designed to evolve as business requirements change, allowing new methods for future extensions without breaking existing functionality.
 * - Dependability: Robust error handling and documentation are ensured for reliable system behavior.
 * - Security: Proper error management and security considerations are implied in the structure of DAO.
 * - Efficiency: Data operations should be optimized within the implementing class to minimize resource usage.
 * - Acceptability: Clear interface contract for developers with documentation ensuring ease of use and understanding.
 * 
 * @param <RestaurantTable> The entity type this DAO manages.
 */
public interface RestaurantTableDAO extends DAO<RestaurantTable> {

    /**
     * Fetches a RestaurantTable by its unique identifier.
     * This method is designed to be used efficiently, with proper error handling 
     * to ensure system dependability. Implementing class should optimize for quick retrieval.
     *
     * @param id The unique identifier of the RestaurantTable to be fetched.
     * @return The RestaurantTable object or null if not found.
     * @throws DAOException If there is an error while fetching the table from the data source.
     */
    RestaurantTable getById(int id) throws DAOException;

    /**
     * Saves the given RestaurantTable to the data source.
     * The method is expected to be efficient, avoiding unnecessary resource consumption.
     *
     * @param restaurantTable The RestaurantTable to be saved.
     * @return true if the operation is successful, false otherwise.
     * @throws DAOException If there is an error while saving the table.
     */
    boolean save(RestaurantTable restaurantTable) throws DAOException;

    /**
     * Updates the details of an existing RestaurantTable.
     * This method is designed for flexibility, allowing the table to be updated 
     * without the need to rewrite code that interacts with this interface.
     *
     * @param restaurantTable The RestaurantTable object containing the updated data.
     * @return true if the update is successful, false otherwise.
     * @throws DAOException If there is an error while updating the table.
     */
    boolean update(RestaurantTable restaurantTable) throws DAOException;

    /**
     * Deletes a RestaurantTable from the data source.
     * Efficiency should be considered to ensure minimal resource usage while removing entries.
     *
     * @param id The unique identifier of the RestaurantTable to be deleted.
     * @return true if the deletion is successful, false otherwise.
     * @throws DAOException If there is an error while deleting the table.
     */
    boolean delete(int id) throws DAOException;

    /**
     * Returns all RestaurantTable objects from the data source.
     * This method is designed for scalability and efficiency, retrieving all data while
     * keeping resource usage in check.
     *
     * @return A list of all RestaurantTable objects.
     * @throws DAOException If there is an error while fetching the tables.
     */
    List<RestaurantTable> getAll() throws DAOException;
}

