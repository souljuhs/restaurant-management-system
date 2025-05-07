package com.fantastic.restaurant;
import java.sql.SQLException;
import java.util.List;

/**
 * UserDAO interface defines data access methods for managing users in the system.
 * 
 * The interface is designed to be easily maintainable, secure, and efficient.
 * 
 * - Maintainability: Methods are modular to accommodate future changes in business needs.
 * - Dependability and Security: Exceptions are handled appropriately, and implementation should ensure security measures like input validation, secure database connections, and protection from SQL injection.
 * - Efficiency: Method signatures are designed to optimize performance, and additional features like pagination can be added as needed.
 * - Acceptability: Methods are clear and designed for ease of use by developers.
 */
public interface UserDAO extends DAO<User> {

    /**
     * Displays a list of staff members in the system.
     * 
     * This method is designed to be extended with pagination or filtering features to handle large datasets efficiently.
     * 
     * @throws SQLException if a database error occurs while fetching the data.
     */
    void showStaff() throws SQLException;

    /**
     * Retrieves the list of staff members from the database.
     * 
     * This method should be optimized for performance. It could be extended to include pagination or filtering to avoid memory overuse and improve response times as the dataset grows.
     * 
     * @return A list of User objects representing the staff members.
     * @throws SQLException if a database error occurs while retrieving the data.
     */
    List<User> getStaffList() throws SQLException;
    
    User getUserById(int id);

}
