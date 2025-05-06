package com.fantastic.restaurant;

/**
 * Represents a menu item in a restaurant.
 * This class encapsulates the details of an item including its name, cost,
 * description, category, and availability.
 * 
 * The class is designed with maintainability, dependability, security, efficiency,
 * and acceptability in mind.
 */
public class Menu {

    // Item attributes
    private String itemName;
    private double itemCost;
    private String itemDescription;
    private String itemCategory;
    private int itemAvailability;

    /**
     * Constructs a new Menu item with the specified details.
     * 
     * @param name the name of the menu item
     * @param description the description of the menu item
     * @param cost the cost of the menu item
     * @param category the category of the menu item
     * @param available the availability of the menu item
     * @throws IllegalArgumentException if any input is invalid (e.g., negative cost or availability)
     */
    public Menu(String name, String description, double cost, String category, int available) {
        setName(name);
        setDescription(description);
        setCost(cost);
        setCategory(category);
        setAvailability(available);
    }

    // Setters with validation

    /**
     * Sets the name of the menu item.
     * 
     * @param name the name of the menu item
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        this.itemName = name;
    }

    /**
     * Sets the cost of the menu item.
     * 
     * @param cost the cost of the menu item
     * @throws IllegalArgumentException if the cost is negative
     */
    public void setCost(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Item cost cannot be negative.");
        }
        this.itemCost = cost;
    }

    /**
     * Sets the description of the menu item.
     * 
     * @param description the description of the menu item
     * @throws IllegalArgumentException if the description is null or empty
     */
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Item description cannot be null or empty.");
        }
        this.itemDescription = description;
    }

    /**
     * Sets the category of the menu item.
     * 
     * @param category the category of the menu item
     * @throws IllegalArgumentException if the category is null or empty
     */
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Item category cannot be null or empty.");
        }
        this.itemCategory = category;
    }

    /**
     * Sets the availability of the menu item.
     * 
     * @param available the availability of the menu item
     * @throws IllegalArgumentException if the availability is negative
     */
    public void setAvailability(int available) {
        if (available < 0) {
            throw new IllegalArgumentException("Item availability cannot be negative.");
        }
        this.itemAvailability = available;
    }

    // Getters

    /**
     * Gets the name of the menu item.
     * 
     * @return the name of the menu item
     */
    public String getName() {
        return itemName;
    }

    /**
     * Gets the cost of the menu item.
     * 
     * @return the cost of the menu item
     */
    public double getCost() {
        return itemCost;
    }

    /**
     * Gets the description of the menu item.
     * 
     * @return the description of the menu item
     */
    public String getDescription() {
        return itemDescription;
    }

    /**
     * Gets the category of the menu item.
     * 
     * @return the category of the menu item
     */
    public String getCategory() {
        return itemCategory;
    }

    /**
     * Gets the availability of the menu item.
     * 
     * @return the availability of the menu item
     */
    public int getAvailability() {
        return itemAvailability;
    }
}

