package com.fantastic.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The StaffTaskDAOImplement class provides an implementation of the StaffTaskDAO interface,
 * enabling data access operations for managing staff tasks.
 *
 * This class supports creating, viewing, listing, and updating staff tasks, and it interacts
 * directly with the "Staff_Tasks" table in the database.
 *
 * Maintainability: SQL statements are defined as constants, methods are small and focused,
 * and reusable utility methods enhance code readability.
 *
 * Dependability and Security: All database connections use try-with-resources
 * to ensure proper resource management. Logging is used to capture errors and task activity,
 * which aids in monitoring and debugging.
 *
 * Efficiency: Queries are straightforward and optimized for task retrieval or updates.
 * Minimal resource waste is ensured by closing connections and avoiding unnecessary processing.
 *
 * Acceptability: The class follows clear Java conventions, and its method names
 * and behaviors are easy for developers to understand and extend.
 */
public class StaffTaskDAOImplement implements StaffTaskDAO {

    private static final Logger logger = LoggerFactory.getLogger(StaffTaskDAOImplement.class);

    // SQL statement constants
    private static final String SQL_INSERT_TASK =
        "INSERT INTO Staff_Tasks (user_id, task_description, status) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL_TASKS = "SELECT * FROM Staff_Tasks";
    private static final String SQL_SELECT_TASK_IDS = "SELECT task_id FROM Staff_Tasks";
    private static final String SQL_UPDATE_TASK_STATUS =
        "UPDATE Staff_Tasks SET status = ?, completed_at = ? WHERE task_id = ?";

    /**
     * Adds a new task assigned to a staff member.
     *
     * This method inserts a new record into the Staff_Tasks table with a user ID, task description,
     * and initial status.
     *
     * @param staff The staff task object containing user ID, task description, and status.
     * @throws SQLException If an error occurs during database interaction.
     */
    @Override
    public void add(StaffTask staff) throws SQLException {
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(SQL_INSERT_TASK)) {

            statement.setInt(1, staff.getUserId());
            statement.setString(2, staff.getDescription());
            statement.setString(3, staff.getStatus());
            statement.executeUpdate();

            logger.info("Assigned task to user ID {}: {}", staff.getUserId(), staff.getDescription());
        } catch (SQLException e) {
            logger.error("Error adding staff task", e);
            throw new SQLException("Failed to add task", e);
        }
    }

    /**
     * Displays all staff tasks currently in the system.
     *
     * This method retrieves all task entries from the Staff_Tasks table and prints them
     * in a structured format to the console.
     *
     * @throws SQLException If a database error occurs during retrieval.
     */
    @Override
    public void view() throws SQLException {
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(SQL_SELECT_ALL_TASKS);
             ResultSet rs = statement.executeQuery()) {

            System.out.printf("%-7s %-7s %-32s %-14s %-25s %-20s%n",
                "TaskID", "UserID", "Description", "Status", "Time_Assigned", "Time_Completed");

            while (rs.next()) {
                System.out.print(formatTaskForDisplay(rs));
            }
        } catch (SQLException e) {
            logger.error("Error viewing staff tasks", e);
            throw new SQLException("Failed to retrieve tasks", e);
        }
    }

    /**
     * Retrieves a list of all task IDs from the system.
     *
     * Useful for administrative tasks, this method queries and returns all task IDs.
     *
     * @return A list of task IDs.
     * @throws SQLException If there is an error while querying the database.
     */
    @Override
    public List<Integer> getTaskList() throws SQLException {
        List<Integer> taskList = new ArrayList<>();

        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(SQL_SELECT_TASK_IDS);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                taskList.add(rs.getInt("task_id"));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving task list", e);
            throw new SQLException("Failed to retrieve task IDs", e);
        }

        return taskList;
    }

    /**
     * Updates the status and completion timestamp of a task.
     *
     * This method updates an existing task's status and records the completion time.
     *
     * @param status The new status to apply to the task.
     * @param id The ID of the task to be updated.
     * @param time The timestamp of task completion.
     * @throws SQLException If there is an error during the update operation.
     */
    @Override
    public void updateTaskStatus(String status, int id, String time) throws SQLException {
        try (Connection connect = Database.getConnection();
             PreparedStatement statement = connect.prepareStatement(SQL_UPDATE_TASK_STATUS)) {

            statement.setString(1, status);
            statement.setString(2, time);
            statement.setInt(3, id);
            statement.executeUpdate();

            logger.info("Updated task ID {} to status '{}'", id, status);
        } catch (SQLException e) {
            logger.error("Error updating task status", e);
            throw new SQLException("Failed to update task status", e);
        }
    }

    /**
     * Formats a ResultSet row representing a task into a human-readable string.
     *
     * @param rs The ResultSet positioned at the task record.
     * @return A formatted string representing the task.
     * @throws SQLException If there is an error accessing the ResultSet.
     */
    private String formatTaskForDisplay(ResultSet rs) throws SQLException {
        int taskId = rs.getInt("task_id");
        int userId = rs.getInt("user_id");
        String description = rs.getString("task_description");
        String status = rs.getString("status");
        String assignedAt = rs.getString("assigned_at");
        String completedAt = rs.getString("completed_at");

        return String.format("%-7d %-7d %-32s %-14s %-25s %-20s%n",
            taskId, userId, description, status, assignedAt, completedAt);
    }
}

