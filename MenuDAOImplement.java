package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuDAOImplement implements MenuDAO
{
	@Override
	public void add(Menu item) throws SQLException 
	{
		Connection connect = Database.getConnection(); //Connects to database
		
		String sqlStatement = "INSERT INTO Menu_Items (name, description, price, category, availability) VALUES (?, ?, ?, ?, ?)";
	
		PreparedStatement statement = connect.prepareStatement(sqlStatement);
		
		statement.setString(1, item.getName());
		statement.setString(2, item.getDescription());
		statement.setDouble(3, item.getCost());
		statement.setString(4, item.getCategory());
		statement.setInt(5, item.getAvailability());
		statement.executeUpdate();
		
		System.out.println("Menu item has been added to the Menu_Items table!");

		connect.close();
		statement.close();
	}
	
	@Override
	public void view() throws SQLException 
	{
		Connection connect = Database.getConnection(); //Connects to database
		
		String sqlStatement = "SELECT * FROM Menu_Items"; //SQL statement to view menu
		
		PreparedStatement statement = connect.prepareStatement(sqlStatement);
		
		ResultSet rs = statement.executeQuery();
		
		//Prints out the column titles of the table
		System.out.printf("%-3s %-8s %-55s %-8s %-15s %-10s\n", "ID", "Name", "Description", "Price", "Category", "Availability");
		
		while(rs.next())//Runs as long as there are rows in the table
		{
			int id = rs.getInt("menu_item_id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			double cost = rs.getDouble("price");
			String category = rs.getString("category");
			int available = rs.getInt("availability");

			System.out.printf("%-3d %-8s %-55s %-8.2f %-15s %-10d\n", id, name, description, cost, category, available);
		}
		
		connect.close();
		statement.close();
		
	}

}
