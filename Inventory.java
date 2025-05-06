package com.fantastic.restaurant;

/* |---------------------------------------------|
 * |				Inventory  				     |
 * |---------------------------------------------|
 * | - itemName: String							 |
 * | - quantity: int							 |
 * | - unit: String								 |
 * | - threshold: int							 |			 
 * |---------------------------------------------|								 
 * | + setItemName(name: String): void		     |
 * | + setQuantity(quant: int): void             |
 * | + setUnit(unit: String): void				 |
 * | + setThreshold(thresh: int): void			 |
 * | + getItemName(): String					 |
 * | + getQuantity(): int	 					 |
 * | + getUnit(): String						 |
 * | + getThreshold(): int						 |
 * |---------------------------------------------|
 */ 

public class Inventory 
{
	private String itemName;
	private int quantity;
	private String unit;
	private int threshold;
	
	public Inventory(String name, int quant, String unit, int thresh)
	{
		setItemName(name);
		setQuantity(quant);
		setUnit(unit);
		setThreshold(thresh);
	}
	
	public void setItemName(String name)
	{
		this.itemName = name;
	}
	
	public void setQuantity(int quant)
	{
		this.quantity = quant;
	}
	
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	public void setThreshold(int thresh)
	{
		this.threshold = thresh;
	}
	
	public String getItemName()
	{
		return itemName;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public String getUnit()
	{
		return unit;
	}
	
	public int getThreshold()
	{
		return threshold;
	}
	
}
