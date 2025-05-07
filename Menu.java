package com.fantastic.restaurant;

/**
 * Represents a menu item in a restaurant.
 * This class encapsulates the details of an item including its name, cost,
 * description, category, availability, and menu/subgroup relationship.
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
    private int menuId;        // ID of the menu
    private int subgroupId;    // ID of the subgroup

    /**
     * Constructs a new Menu item with the specified details.
     * 
     * @param name        the name of the menu item
     * @param description the description of the menu item
     * @param cost       the cost of the menu item
     * @param category    the category of the menu item
     * @param available    the availability of the menu item (0 or 1)
     * @param menuId      the ID of the menu associated with this item
     * @param subgroupId   the ID of the subgroup associated with this item
     * @throws IllegalArgumentException if any input is invalid (e.g., negative cost or availability)
     */
    public Menu(String name, String description, double cost, String category, int available, int menuId, int subgroupId) {
        setName(name);
        setDescription(description);
        setCost(cost);
        setCategory(category);
        setAvailability(available);
        this.menuId = menuId;
        this.subgroupId = subgroupId;
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
     * @param available the availability of the menu item (0 for not available, 1 for available)
     * @throws IllegalArgumentException if the availability is invalid (anything other than 0 or 1)
     */
    public void setAvailability(int available) {
        if (available < 0 || available > 1) { // Restrict availability to binary values
            throw new IllegalArgumentException("Item availability can only be 0 (not available) or 1 (available).");
        }
        this.itemAvailability = available;
    }
    
    // Getters

    public String getName() {
        return itemName;
    }

    public double getCost() {
        return itemCost;
    }

    public String getDescription() {
        return itemDescription;
    }

    public String getCategory() {
        return itemCategory;
    }

    public int getAvailability() {
        return itemAvailability;
    }
    
    public int getMenuId() {
        return menuId;
    }
    
    public int getSubgroupId() {
        return subgroupId;
    }
}
