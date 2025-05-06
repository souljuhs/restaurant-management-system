package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class InventoryDAOImplement implements InventoryDAO
{

	@Override
	public void add(Inventory item) throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		//A sql query as a java string that will insert a new item into the Inventory table 
		String sqlStatement = "INSERT INTO Inventory (name, stock_quantity, unit, threshold) VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			statement.setString(1, item.getItemName());
			statement.setInt(2, item.getQuantity());
			statement.setString(3, item.getUnit());
			statement.setInt(4, item.getThreshold());
			statement.executeUpdate();
			
			System.out.println("Item has been added to inventory!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
	}

	@Override
	public void view() throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		ResultSet rs = null;//will hold the MenuItem table data once the query is used
		
		//A sql query as a java string that will show the items in the Inventory table 
		String sqlStatement = "SELECT * FROM Inventory";
		
		try
		{	
			//Runs the sql query using the statement object
			PreparedStatement statement = connect.prepareStatement(sqlStatement);
			rs = statement.executeQuery();
			
			//Prints out the column titles of the table
			//System.out.printf("%-3s %-8s %-5s %-8s %-15s %-10s\n", "ID", "Name", "Quantity", "Unit", "Threshold", "Last Updated");
			System.out.printf("%-3s %-9s %-10s %-6s %-14s %-20s\n", "ID", "Name", "Quantity", "Unit", "Threshold", "Last Updated");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			while(rs.next())//Runs as long as rows in the table are there
			{
				int id = rs.getInt("item_id");
				String name = rs.getString("name");
				int quantity = rs.getInt("stock_quantity");
				String unit = rs.getString("unit");
				int threshold = rs.getInt("threshold");
				Timestamp updated = rs.getTimestamp("last_updated");

				String timestamp = dateFormat.format(updated);
				
				//System.out.printf("%-3d %-8s %-5d %-10s %-15d %-20s\n", id, name, quantity, unit, threshold, timestamp);
				System.out.printf("%-3d %-12s %-7d %-10s %-7d %-20s\n", id, name, quantity, unit, threshold, timestamp);
				
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		connect.close();
	}

}
