package com.fantastic.restaurant;

/**
 * The User class represents a user within the system. It stores personal information like
 * username, password, role, email, and phone number, and provides methods to access and
 * modify these details.
 * 
 * This class is designed to be easily maintainable, secure, and efficient.
 * 
 * - Maintainability: The class uses clear method names and data encapsulation, making it easy to extend in the future. Adding new fields or modifying existing functionality is straightforward.
 * - Dependability and Security: The class ensures proper validation of user inputs such as username, password, email, and phone number. Passwords should be securely hashed before being stored (this needs to be implemented in practice). The `Role` is enforced using a type-safe enum to avoid errors.
 * - Efficiency: The class avoids unnecessary data manipulation and uses a direct enum for the `role`, minimizing resource overhead.
 * - Acceptability: The class is designed to be intuitive for developers. It uses getters and setters to ensure that the data is accessed in a controlled manner, preventing potential misuse or errors.
 */
public class User {

    private int id;            // User ID (typically assigned by the database)
    private String username;   // Username chosen by the user
    private String password;   // User password (to be securely hashed before storage)
    private Role role;         // User role, defined as an enum (admin, staff, or customer)
    private String email;      // User's email address
    private String phone;      // User's phone number

    /**
     * Constructs a new User object with the specified parameters, excluding the ID.
     * 
     * This constructor is typically used when creating new users, as the ID is usually generated
     * automatically by the database.
     * 
     * @param username The username for the user.
     * @param password The password for the user (to be securely hashed in practice).
     * @param role The role of the user, represented as an enum (admin, staff, or customer).
     * @param email The email address of the user.
     * @param phone The phone number of the user.
     */
    public User(String username, String password, Role role, String email, String phone) {
        setUsername(username);
        setPassword(password);  // Note: In practice, hash the password before storage.
        setRole(role);
        setEmail(email);
        setPhone(phone);
    }

    /**
     * Constructs a User object with the specified ID and other user details.
     * 
     * This constructor is used when retrieving a user from the database where the ID is already known.
     * 
     * @param id The unique identifier for the user.
     * @param username The username for the user.
     * @param password The password for the user (to be securely hashed in practice).
     * @param role The role of the user, represented as an enum (admin, staff, or customer).
     * @param email The email address of the user.
     * @param phone The phone number of the user.
     */
    public User(int id, String username, String password, Role role, String email, String phone) {
        this(username, password, role, email, phone);
        this.id = id;
    }

    /**
     * Enum that defines the possible roles for a user in the system.
     * The available roles are:
     * - ADMIN: The user has administrative privileges.
     * - STAFF: The user is a staff member with certain access.
     * - CUSTOMER: The user is a customer with limited access.
     */
    public enum Role {
        ADMIN, STAFF, CUSTOMER
    }

    // Setters

    /**
     * Sets the username for the user.
     * 
     * @param username The new username for the user.
     * @throws IllegalArgumentException if the username is null or empty.
     */
    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    /**
     * Sets the password for the user.
     * 
     * @param password The new password for the user (to be securely hashed before storing).
     * @throws IllegalArgumentException if the password is null or empty.
     */
    public void setPassword(String password) {
        if (password != null && !password.trim().isEmpty()) {
            this.password = password; // Hash the password in a real system
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    /**
     * Sets the role for the user.
     * 
     * @param role The new role of the user (must be a valid Role enum).
     * @throws IllegalArgumentException if the role is null.
     */
    public void setRole(Role role) {
        if (role != null) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Role cannot be null");
        }
    }

    /**
     * Sets the email for the user.
     * 
     * @param email The new email address for the user.
     * @throws IllegalArgumentException if the email format is invalid.
     */
    public void setEmail(String email) {
        // Basic email validation (can be expanded)
        if (email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    /**
     * Sets the phone number for the user.
     * 
     * @param phone The new phone number for the user.
     * @throws IllegalArgumentException if the phone number format is invalid.
     */
    public void setPhone(String phone) {
        // Basic phone validation (can be expanded)
        if (phone != null && phone.matches("\\+?[0-9]*")) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    // Getters

    /**
     * Gets the user ID.
     * 
     * @return The unique identifier for the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return The password of the user (in real systems, this should not be exposed directly).
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the role of the user.
     * 
     * @return The role of the user (ADMIN, STAFF, or CUSTOMER).
     */
    public Role getRole() {
        return role;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the phone number of the user.
     * 
     * @return The phone number of the user.
     */
    public String getPhone() {
        return phone;
    }
}
