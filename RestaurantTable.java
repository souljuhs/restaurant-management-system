package com.fantastic.restaurant;

public class RestaurantTable 
{
	private int tableNumber;
	private int tableCapacity;
	private String tableStatus;
	
	public RestaurantTable(int num, int cap, String status)
	{
		setTableNumber(num);
		setTableCapacity(cap);
		setTableStatus(status);
	}
	
	//Setters
	public void setTableNumber(int num)
	{
		this.tableNumber = num;
	}
	
	public void setTableCapacity(int cap)
	{
		this.tableCapacity = cap;
	}
	
	public void setTableStatus(String status)
	{
		this.tableStatus = status;
	}
	
	//Getters
	public int getTableNumber()
	{
		return tableNumber;
	}
	
	public int getTableCapacity()
	{
		return tableCapacity;
	}
	
	public String getTableStatus()
	{
		return tableStatus;
	}
	
}
