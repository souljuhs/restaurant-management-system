package com.fantastic.restaurant;

/**
 * Represents a restaurant table with attributes such as table number,
 * capacity, and status. This class is designed with maintainability, 
 * dependability, security, efficiency, and acceptability in mind.
 * 
 * The class allows flexible changes, ensures valid data, and provides 
 * intuitive access to its attributes.
 */
public class RestaurantTable {
    
    // Table attributes
    private int tableNumber;
    private int tableCapacity;
    private String tableStatus;

    /**
     * Constructs a new RestaurantTable with the specified table number,
     * capacity, and status. Input values are validated to ensure they are valid.
     * 
     * @param num the table number
     * @param cap the table capacity (must be non-negative)
     * @param status the status of the table (e.g., "available", "occupied")
     * @throws IllegalArgumentException if the capacity is negative or the status is invalid
     */
    public RestaurantTable(int num, int cap, String status) {
        setTableNumber(num);
        setTableCapacity(cap);
        setTableStatus(status);
    }
    
    // Setters with validation

    /**
     * Sets the table number. A table number should be a positive integer.
     * 
     * @param num the table number
     * @throws IllegalArgumentException if the table number is invalid
     */
    public void setTableNumber(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Table number must be positive.");
        }
        this.tableNumber = num;
    }

    /**
     * Sets the table capacity. The capacity must be a non-negative integer.
     * 
     * @param cap the table capacity
     * @throws IllegalArgumentException if the capacity is negative
     */
    public void setTableCapacity(int cap) {
        if (cap < 0) {
            throw new IllegalArgumentException("Table capacity cannot be negative.");
        }
        this.tableCapacity = cap;
    }

    /**
     * Sets the status of the table. The status must be either "available" or "occupied".
     * 
     * @param status the table status
     * @throws IllegalArgumentException if the status is invalid
     */
    public void setTableStatus(String status) {
        if (status == null || (!status.equals("available") && !status.equals("occupied"))) {
            throw new IllegalArgumentException("Invalid status. Must be 'available' or 'occupied'.");
        }
        this.tableStatus = status;
    }

    // Getters

    /**
     * Gets the table number.
     * 
     * @return the table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Gets the table capacity.
     * 
     * @return the table capacity
     */
    public int getTableCapacity() {
        return tableCapacity;
    }

    /**
     * Gets the table status.
     * 
     * @return the table status
     */
    public String getTableStatus() {
        return tableStatus;
    }


}

