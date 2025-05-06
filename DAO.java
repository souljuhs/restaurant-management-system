package com.fantastic.restaurant;

import java.sql.SQLException;


/**
 * Generic Data Access Object (DAO) Interface for CRUD operations.
 * This interface is designed to handle operations on database entities
 * with considerations for maintainability, security, efficiency, and user acceptability.
 * 
 * @param <T> The type of the entity to be managed by the DAO.
 */
public interface DAO <T> 
{
	 /**
     * Adds an entity to the database.
     * This method performs the insertion operation. The entity should meet all necessary constraints
     * (e.g., no null fields for required columns) to ensure data integrity.
     * 
     * @param entity The entity to be added.
     * @throws SQLException if there is an error with the database connection or SQL query execution.
     * @throws DAOException if there is a business logic violation or other operational error.
     */
	void add(T t) throws SQLException, DAOException;
	

    /**
     * Retrieves all entities from the database.
     * This method retrieves a list of all entities of type T.
     * It can be extended in the future to support filtering or pagination for larger datasets.
     * 
     * @return a list of entities of type T.
     * @throws SQLException if there is an error with the database connection or SQL query execution.
     * @throws DAOException if there is a business logic violation or other operational error.
     */
	void view() throws SQLException, DAOException; 

}
