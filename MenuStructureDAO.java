package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * MenuStructureDAO is responsible for handling database operations related to menus and menu subgroups.
 * It allows for adding a new menu and its associated subgroups to the database.
 */
public class MenuStructureDAO {

    private static final String MENU_INSERT_SQL = "INSERT INTO menus (name, pos_name) VALUES (?, ?)";
    private static final String SUBGROUP_INSERT_SQL = "INSERT INTO menu_subgroups (menu_id, subgroup_name) VALUES (?, ?)";

    /**
     * Adds a new menu and its subgroups to the database.
     * 
     * @param name the name of the menu
     * @param posName the POS name of the menu
     * @param subgroups the list of subgroup names associated with the menu
     * @throws SQLException if any database error occurs during the operation
     */
    public void addMenuWithSubgroups(String name, String posName, List<String> subgroups) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            // Insert menu into the database and retrieve generated menu ID
            int menuId = insertMenu(conn, name, posName);

            // Insert subgroups into the database for the newly created menu
            insertSubgroups(conn, menuId, subgroups);
        } catch (SQLException e) {
            // Log the error for further analysis and rethrow to propagate the exception
            System.err.println("Error saving menu and subgroups: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Inserts a new menu into the database and returns the generated menu ID.
     * 
     * @param conn the database connection to be used
     * @param name the name of the menu
     * @param posName the POS name of the menu
     * @return the generated menu ID
     * @throws SQLException if any database error occurs
     */
    private int insertMenu(Connection conn, String name, String posName) throws SQLException {
        try (PreparedStatement menuStmt = conn.prepareStatement(MENU_INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            menuStmt.setString(1, name);
            menuStmt.setString(2, posName);
            menuStmt.executeUpdate();

            // Retrieve generated menu ID
            try (var generatedKeys = menuStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated menu ID
                } else {
                    throw new SQLException("Failed to retrieve menu ID.");
                }
            }
        }
    }

    /**
     * Inserts multiple subgroups into the database for a specific menu ID.
     * 
     * @param conn the database connection to be used
     * @param menuId the ID of the menu to which the subgroups belong
     * @param subgroups the list of subgroups to be inserted
     * @throws SQLException if any database error occurs
     */
    private void insertSubgroups(Connection conn, int menuId, List<String> subgroups) throws SQLException {
        try (PreparedStatement subgroupStmt = conn.prepareStatement(SUBGROUP_INSERT_SQL)) {
            for (String subgroup : subgroups) {
                subgroupStmt.setInt(1, menuId);
                subgroupStmt.setString(2, subgroup);
                subgroupStmt.addBatch(); // Add subgroup to batch for batch processing
            }

            // Execute all insertions in a batch for efficiency
            subgroupStmt.executeBatch();
        }
    }
}

