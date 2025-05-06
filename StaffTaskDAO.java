package com.fantastic.restaurant;

import java.sql.SQLException;
import java.util.List;

/**
 * StaffTaskDAO interface defines data access methods for managing tasks assigned to staff in the system.
 * 
 * The interface is designed to be maintainable, secure, efficient, and easily extendable.
 * 
 * - Maintainability: Methods are modular to allow for easy modification and addition of features as business needs evolve.
 * - Dependability and Security: Exceptions are handled appropriately, ensuring the system remains stable and secure. Implementation should ensure data integrity and protection against malicious activities like SQL injection.
 * - Efficiency: Method signatures are optimized for performance. The use of general `List` interface allows for flexible future improvements and optimizations, including memory and response time optimizations as the system scales.
 * - Acceptability: Methods are clear, easy to understand, and designed for ease of use by developers working with the system.
 */
public interface StaffTaskDAO extends DAO<StaffTask> {

    /**
     * Retrieves a list of task IDs assigned to staff.
     * 
     * This method is designed to be efficient and can be extended to support pagination or filtering for large datasets if necessary.
     * 
     * @return A list of task IDs.
     * @throws SQLException if an error occurs while querying the database.
     */
    List<Integer> getTaskList() throws SQLException;

    /**
     * Updates the status and time of a specific task assigned to a staff member.
     * 
     * This method allows updating the task status and timestamp. It can be extended to handle more complex task updates or to integrate with other systems.
     * 
     * @param status The new status of the task.
     * @param id The ID of the task to update.
     * @param time The time the status was updated.
     * @throws SQLException if an error occurs while updating the task in the database.
     */
    void updateTaskStatus(String status, int id, String time) throws SQLException;
}
