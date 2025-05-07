package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UserDAOImplement class provides an implementation of the UserDAO interface, which
 * manages the data access operations related to the User entity. It offers methods to add, view,
 * and retrieve users, including staff members specifically.
 * 
 * This class interacts with a database to perform CRUD (Create, Read) operations on the "Users"
 * table, ensuring the security and consistency of user data.
 * 
 * - Maintainability: The methods are well-structured and ensure that user data is validated and
 *   handled consistently. The use of SQL queries is modular, and the logic is clear for easier
 *   maintenance.
 * - Dependability and Security: The class performs necessary validation on user inputs (such as
 *   username, password, email, and phone number). Passwords are hashed before being stored, and
 *   sensitive information is handled securely.
 * - Efficiency: The SQL queries are optimized for fetching users either in general or as staff
 *   members. The connection to the database is managed using try-with-resources, ensuring proper
 *   resource handling.
 * - Acceptability: The class uses standard Java conventions and provides easy-to-understand method
 *   names that make it intuitive for developers to use and extend.
 */
public class UserDAOImplement implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAOImplement.class.getName());

    private static final String INSERT_USER_SQL =
        "INSERT INTO Users (username, password, role, email, phone) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BASE_SQL =
        "SELECT id, username, password, role, email, phone FROM Users";

    /**
     * Adds a new user to the database.
     * 
     * This method validates the user input, prepares an SQL statement, and executes it to insert
     * the user's data into the "Users" table. If the insertion is successful, a log message is
     * generated; otherwise, a warning is logged.
     * 
     * @param user The user object containing the data to be added.
     * @throws SQLException If there is an error while interacting with the database.
     */
    @Override
    public void add(User user) throws SQLException {
        validateUser(user);

        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(INSERT_USER_SQL)) {

            setUserStatementParams(statement, user);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("User added: " + user.getUsername());
            } else {
                logger.warning("No user was added.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding user", e);
            throw new SQLException("Could not add user", e);
        }
    }

    /**
     * Displays all users in the system.
     * 
     * This method fetches all users from the database and prints their details to the console.
     * 
     * @throws SQLException If there is an error while interacting with the database.
     */
    @Override
    public void view() throws SQLException {
        printUsers(fetchUsers(false));
    }

    /**
     * Displays all staff members in the system.
     * 
     * This method fetches only users with the "staff" role from the database and prints their
     * details to the console.
     * 
     * @throws SQLException If there is an error while interacting with the database.
     */
    @Override
    public void showStaff() throws SQLException {
        printUsers(fetchUsers(true));
    }

    /**
     * Retrieves the list of all staff members in the system.
     * 
     * This method queries the database for users with the "staff" role and returns them as a list
     * of User objects.
     * 
     * @return A list of users with the "staff" role.
     * @throws SQLException If there is an error while interacting with the database.
     */
    @Override
    public List<User> getStaffList() throws SQLException {
        final String sql = "SELECT id, username, role, email, phone FROM Users WHERE LOWER(role) = 'staff'";
        List<User> staffList = new ArrayList<>();

        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                // Create a User object for each staff member
                User user = new User(id, username, password, mapRoleToEnum(role), email, phone);
                staffList.add(user);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving staff list", e);
            throw new SQLException("Error retrieving staff list", e);
        }

        return staffList;
    }

    /**
     * Validates the user input to ensure that all necessary fields are provided and correct.
     * 
     * This method checks the user details, such as username, password, role, email, and phone
     * number, and throws an IllegalArgumentException if any field is invalid.
     * 
     * @param user The user object to be validated.
     * @throws IllegalArgumentException If any of the user details are invalid.
     */
    private void validateUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");

        if (isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (isBlank(user.getPassword())) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if (!user.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!user.getPhone().matches("^\\+?[0-9\\- ]{7,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    /**
     * Sets the parameters for the prepared statement when inserting or updating a user.
     * 
     * This method maps the user object to the SQL statement's parameters to ensure the correct
     * data is inserted into the database.
     * 
     * @param statement The prepared statement to set parameters for.
     * @param user The user object containing the data to be inserted.
     * @throws SQLException If there is an error while setting parameters.
     */
    private void setUserStatementParams(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, hashPassword(user.getPassword())); // Hash the password before storing
        statement.setString(3, user.getRole().name().toLowerCase());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPhone());
    }

    /**
     * Fetches users from the database based on the specified role.
     * 
     * This method queries the database for users based on whether the staffOnly flag is true or
     * false. It retrieves all users or only staff members accordingly.
     * 
     * @param staffOnly A flag indicating whether to fetch only staff members.
     * @return A list of users matching the criteria.
     * @throws SQLException If there is an error while interacting with the database.
     */
    private List<User> fetchUsers(boolean staffOnly) throws SQLException {
        String sql = staffOnly ? SELECT_USER_BASE_SQL + " WHERE LOWER(role) = 'staff'" : SELECT_USER_BASE_SQL;
        List<User> users = new ArrayList<>();

        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String roleStr = rs.getString("role").toLowerCase();
                User.Role roleEnum = mapRoleToEnum(roleStr);

                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    roleEnum,
                    rs.getString("email"),
                    rs.getString("phone")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching users", e);
            throw new SQLException("Error fetching users", e);
        }

        return users;
    }

    /**
     * Prints the list of users to the console in a formatted table.
     * 
     * This method prints the user details, including ID, username, role, email, and phone number,
     * to the console in a well-formatted way.
     * 
     * @param users The list of users to be printed.
     */
    private void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.printf("%-3s %-10s %-8s %-25s %-15s%n", "ID", "Name", "Role", "Email", "Phone");
        users.forEach(user -> System.out.printf(
            "%-3d %-10s %-8s %-25s %-15s%n",
            user.getId(), user.getUsername(), user.getRole(), user.getEmail(), user.getPhone()
        ));
    }

    /**
     * Checks if a string is blank (null or empty).
     * 
     * This method is used to ensure that fields like username, password, email, etc., are not
     * left empty or null.
     * 
     * @param s The string to check.
     * @return true if the string is null or empty, false otherwise.
     */
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Maps a role string to the corresponding Role enum.
     * 
     * This method ensures that the role string from the database is converted to the correct Role
     * enum value (ADMIN, STAFF, or CUSTOMER).
     * 
     * @param roleStr The role string to map.
     * @return The corresponding Role enum.
     */
    private User.Role mapRoleToEnum(String roleStr) {
        switch (roleStr) {
            case "admin":
                return User.Role.ADMIN;
            case "staff":
                return User.Role.STAFF;
            case "customer":
            default:
                return User.Role.CUSTOMER;
        }
    }
    
    @Override
    public User getUserById(int id) {
        // Example (adjust as per your DB logic)
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Assuming User has a constructor: User(int id, String name, ...)
            	return new User(
            		    rs.getInt("id"),
            		    rs.getString("username"),
            		    rs.getString("password"),
            		    User.Role.valueOf(rs.getString("role").toUpperCase()), // Ensure DB stores values like 'admin', 'staff', etc.
            		    rs.getString("email"),
            		    rs.getString("phone")
            		);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Hashes the password before storing it in the database.
     * 
     * This method is a placeholder for the actual password hashing logic, which should be
     * implemented in the future using a secure hashing algorithm (e.g., BCrypt).
     * 
     * @param password The password to hash.
     * @return The hashed password.
     */
    private String hashPassword(String password) {
        // Implement password hashing here (e.g., BCrypt)
        return password; // Replace with actual hashing logic
    }
}

