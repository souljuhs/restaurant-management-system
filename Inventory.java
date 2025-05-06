package com.fantastic.restaurant;

/**
 * Represents an inventory item in the restaurant's system.
 * Each item in the inventory has a name, quantity, unit, and threshold.
 * 
 * - Maintainability: The class is designed to be easily extendable, with clear method names and appropriate encapsulation.
 * - Dependability and Security: The class is structured to handle valid data and can be easily extended for additional validation or security checks.
 * - Efficiency: The class provides direct access to the attributes and ensures minimal overhead for standard operations.
 * - Acceptability: The design is simple and intuitive, making it easy for developers and users to interact with inventory data.
 */
public class Inventory {

    private String itemName;  // The name of the inventory item.
    private int quantity;     // The current quantity of the item in stock.
    private String unit;      // The unit of measurement for the item (e.g., kg, liters).
    private int threshold;    // The quantity threshold that triggers reordering.

    /**
     * Constructs an Inventory object with the specified item name, quantity,
     * unit of measurement, and threshold value.
     * 
     * @param name the name of the inventory item.
     * @param quant the quantity of the item in stock.
     * @param unit the unit of measurement for the item.
     * @param thresh the threshold quantity for reordering.
     * 
     * @throws IllegalArgumentException if any parameter is invalid (e.g., negative values).
     */
    public Inventory(String name, int quant, String unit, int thresh) {
        setItemName(name);
        setQuantity(quant);
        setUnit(unit);
        setThreshold(thresh);
    }

    /**
     * Sets the name of the inventory item. Item names should not be null or empty.
     * 
     * @param name the name of the item.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    public void setItemName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        this.itemName = name;
    }

    /**
     * Sets the quantity of the item in stock. The quantity must be a non-negative integer.
     * 
     * @param quant the quantity of the item in stock.
     * @throws IllegalArgumentException if the quantity is negative.
     */
    public void setQuantity(int quant) {
        if (quant < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quant;
    }

    /**
     * Sets the unit of measurement for the inventory item.
     * 
     * @param unit the unit of measurement (e.g., "kg", "liter").
     * @throws IllegalArgumentException if the unit is null or empty.
     */
    public void setUnit(String unit) {
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit cannot be null or empty.");
        }
        this.unit = unit;
    }

    /**
     * Sets the threshold quantity for the item. The threshold should be a non-negative integer.
     * 
     * @param thresh the threshold quantity for reordering.
     * @throws IllegalArgumentException if the threshold is negative.
     */
    public void setThreshold(int thresh) {
        if (thresh < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative.");
        }
        this.threshold = thresh;
    }

    /**
     * Gets the name of the inventory item.
     * 
     * @return the name of the inventory item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the quantity of the inventory item in stock.
     * 
     * @return the quantity of the inventory item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the unit of measurement for the inventory item.
     * 
     * @return the unit of measurement for the item.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Gets the threshold quantity that triggers reordering for the item.
     * 
     * @return the threshold quantity for reordering.
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * Checks if the current quantity of the item is below the threshold, indicating a need for reorder.
     * 
     * @return true if the quantity is below the threshold, false otherwise.
     */
    public boolean isReorderNeeded() {
        return quantity < threshold;
    }

    /**
     * Provides a string representation of the inventory item, displaying its key attributes.
     * 
     * @return a string representation of the inventory item.
     */
    @Override
    public String toString() {
        return String.format("Item: %s, Quantity: %d %s, Threshold: %d", itemName, quantity, unit, threshold);
    }
}
