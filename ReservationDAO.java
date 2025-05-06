package com.fantastic.restaurant;

import java.sql.SQLException;

/**
 * ReservationDAO interface defines the data access methods for managing reservations in the system.
 * This interface is designed to ensure maintainability, dependability, security, efficiency, and acceptability.
 * 
 * The methods in this interface should provide a clear and efficient contract for interacting with the reservation data.
 * 
 * - Maintainability: 
 *     - This interface is designed to be easily extended or modified to accommodate future requirements, such as adding new data access methods.
 *     - The methods are defined with clear parameters and return types, ensuring easy understanding and usage.
 * 
 * - Dependability and Security:
 *     - Each method is designed with appropriate exception handling. SQLException is thrown for database access issues, ensuring reliable communication with the database.
 *     - Security measures should be enforced within the implementing class, ensuring only authorized users interact with sensitive reservation data.
 * 
 * - Efficiency:
 *     - Methods are kept focused on specific tasks with minimal resource usage, ensuring that database interactions are efficient and do not lead to unnecessary overhead.
 * 
 * - Acceptability:
 *     - The interface is designed to be intuitive and simple, adhering to common conventions to ensure ease of use for developers.
 *     - The use of clear method names and parameter types ensures that interacting with this interface is straightforward and clear.
 */
public interface ReservationDAO extends DAO<Reservation> {

    /**
     * Updates the status of a reservation based on its ID.
     * This method is designed to be efficient and secure by ensuring that only valid status updates are processed.
     * 
     * @param status the new status to set for the reservation (e.g., "booked", "cancelled", "completed")
     * @param id the ID of the reservation to update
     * @throws SQLException if there is an issue executing the update query in the database
     * @throws IllegalArgumentException if the provided status is invalid
     */
    void update(String status, int id) throws SQLException, IllegalArgumentException;
}
