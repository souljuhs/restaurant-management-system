package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles database operations related to Menus and Menu Subgroups.
 */
public class MenuStructureDAO {

    /**
     * Adds a new menu and its subgroups to the database.
     * 
     * @param name the name of the menu
     * @param posName the POS name of the menu
     * @param subgroups the list of subgroup names
     * @throws SQLException if any database error occurs
     */
    public void addMenuWithSubgroups(String name, String posName, List<String> subgroups) throws SQLException {
        String menuInsertSQL = "INSERT INTO menus (name, pos_name) VALUES (?, ?)";
        String subgroupInsertSQL = "INSERT INTO menu_subgroups (menu_id, subgroup_name) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement menuStmt = conn.prepareStatement(menuInsertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            // Insert into menus
            menuStmt.setString(1, name);
            menuStmt.setString(2, posName);
            menuStmt.executeUpdate();

            // Get generated menu_id
            try (var generatedKeys = menuStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int menuId = generatedKeys.getInt(1);

                    // Insert each subgroup
                    try (PreparedStatement subgroupStmt = conn.prepareStatement(subgroupInsertSQL)) {
                        for (String subgroup : subgroups) {
                            subgroupStmt.setInt(1, menuId);
                            subgroupStmt.setString(2, subgroup);
                            subgroupStmt.addBatch();
                        }
                        subgroupStmt.executeBatch();
                    }
                } else {
                    throw new SQLException("Failed to retrieve menu ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving menu and subgroups: " + e.getMessage());
            throw e;
        }
    }
}

