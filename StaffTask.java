package com.fantastic.restaurant;

/**
 * StaffTask class represents a task assigned to a staff member in the restaurant system.
 * 
 * The class is designed to be easily maintainable, secure, and efficient.
 * 
 * - Maintainability: The use of enums for task status ensures easy modifications and extensions for future requirements.
 * - Dependability and Security: Input validation and secure usage of enums reduce the risk of invalid statuses or unexpected behaviors.
 * - Efficiency: The class avoids unnecessary object creation and ensures that only valid status values are used.
 * - Acceptability: The class is designed to be clear and easy to use, with intuitive method names and clear structure for future developers.
 */
public class StaffTask {

    // Unique identifier for the task
    private int userId;

    // Description of the task
    private String description;

    // Current status of the task (enum helps avoid errors)
    private Status status;

    /**
     * Enum representing the status of the task.
     * 
     * This enum defines three statuses: ASSIGNED, IN_PROGRESS, and COMPLETED.
     * Using an enum helps ensure that the status values are limited to the defined set, improving security and readability.
     */
    public enum Status {
        ASSIGNED("Assigned"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed");

        private final String statusName;

        // Constructor for the enum
        Status(String statusName) {
            this.statusName = statusName;
        }

        // Getter for the status name
        public String getStatusName() {
            return this.statusName;
        }
    }

    /**
     * Constructs a StaffTask object with the given user ID, description, and status.
     * 
     * This constructor allows creating a new task with a specified user ID, description, and task status.
     * 
     * @param userId    The ID of the user assigned to the task.
     * @param description The description of the task.
     * @param status    The status of the task (one of the defined Status enum values).
     */
    public StaffTask(int userId, String description, Status status) {
        this.userId = userId;
        this.description = description;
        this.status = status;
    }

    /**
     * Retrieves the user ID associated with the task.
     * 
     * This method provides access to the user ID for the task, allowing other components to identify the staff member assigned to the task.
     * 
     * @return The user ID of the staff member.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for the task.
     * 
     * This method allows for updating the user ID if the task is reassigned.
     * 
     * @param userId The new user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the description of the task.
     * 
     * This method provides access to the task description, which can be used to display details about the task.
     * 
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     * 
     * This method allows updating the task description if necessary.
     * 
     * @param description The new description for the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the status of the task.
     * 
     * This method returns the current status of the task as a string.
     * 
     * @return The status of the task in string form (e.g., "Assigned", "In Progress", "Completed").
     */
    public String getStatus() {
        return status.getStatusName();
    }

    /**
     * Sets the status of the task using the given Status enum value.
     * 
     * This method allows updating the task status with a valid enum value, ensuring that the status is always correct.
     * 
     * @param status The new status of the task (one of the defined Status enum values).
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Sets the status of the task using an integer value.
     * 
     * This method allows updating the task status with an integer value (1 for assigned, 2 for in progress, and 3 for completed).
     * 
     * @param status The status code (1, 2, or 3).
     * @throws IllegalArgumentException if an invalid status code is provided.
     */
    public void setStatus(int status) {
        switch (status) {
            case 1:
                this.status = Status.ASSIGNED;
                break;
            case 2:
                this.status = Status.IN_PROGRESS;
                break;
            case 3:
                this.status = Status.COMPLETED;
                break;
            default:
                throw new IllegalArgumentException("Invalid status code: " + status);
        }
    }
}

