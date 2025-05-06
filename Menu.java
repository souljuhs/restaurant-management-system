package com.fantastic.restaurant;

/* |----------------------------------------------|
 * |				   Menu					  	  |
 * |----------------------------------------------|
 * | - itemName: String							  |
 * | - itemCost: double							  |
 * | - itemDescription: String					  |
 * | - itemCategory: String						  |
 * | - itemAvailability: int					  |
 * |----------------------------------------------|
 * | + setName(name: String): void				  |
 * | + setCost(cost: String): void				  |
 * | + setDescription(description: String): void  |
 * | + setCategory(category: String): void		  |
 * | + setAvailability(availability: int): void   |
 * | + getName(): String						  |
 * | + getCost(): double						  |
 * | + getDescription(): String					  |
 * | + getCategory(): String					  |
 * | + getAvailability(): int					  |
 * |----------------------------------------------|
*/

public class Menu 
{
	private String itemName;//Holds the item's name
	private double itemCost;//Holds the item's cost
	private String itemDescription;
	private String itemCategory;
	private int itemAvailability;
	
	//Parameterized constructor
	public Menu(String name, String description, double cost, String category, int available)	
	{
		this.itemName = name;
		this.itemDescription = description;
		this.itemCost = cost;
		this.itemCategory = category;
		this.itemAvailability = available;
	}
	
	//Method to set the item's name
	public void setName(String name)
	{
		this.itemName = name;
	}
	
	//Method to set the item's cost
	public void setCost(double cost)
	{	
		this.itemCost = cost;
	}	
	
	//Method to set the item's description
	public void setDescription(String description)
	{
		this.itemDescription = description;
	}
	
	//Method to set the item's category
	public void setCategory(String category)
	{
		this.itemCategory = category;
	}
	
	//Method to set the item's availablity
	public void setAvailability(int available)
	{
		this.itemAvailability = available;
	}
	
	//Method to get the item's name
	public String getName()
	{
		return itemName;
	}
	
	//Method to get the item's cost
	public double getCost()
	{
		return itemCost;
	}
	
	//Method to get the item's description
	public String getDescription()
	{
		return itemDescription;
	}
	
	//Method to get the item's category
	public String getCategory()
	{
		return itemCategory;
	}
	
	//Method to show if item is available
	public int getAvailability()
	{
		return itemAvailability;
	}
	
}
