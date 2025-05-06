package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantTableDAOImplement implements RestaurantTableDAO
{

	@Override
	public void add(RestaurantTable table) throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		//A sql query as a java string that will insert a new table into the Tables table 
		String sqlStatement = "INSERT INTO Tables (table_number, capacity, status) VALUES (?, ?, ?)";
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			statement.setInt(1, table.getTableNumber());
			statement.setInt(2, table.getTableCapacity());
			statement.setString(3, table.getTableStatus());
			statement.executeUpdate();
			
			System.out.println("Table Created!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
	}

	@Override
	public void view() throws SQLException 
	{
		Connection connect = Database.getConnection(); //Connects to database
		
		String sqlStatement = "SELECT * FROM Tables"; //SQL statement to Tables table
		
		PreparedStatement statement = connect.prepareStatement(sqlStatement);
		
		ResultSet rs = statement.executeQuery();
		
		//Prints out the column titles of the table
		System.out.printf("%-4s %-14s %-10s %-8s\n", "ID |", "Table_Number |", "Capacity |", "Status|");
		System.out.println("--------------------------------------");
		while(rs.next())//Runs as long as there are rows in the table
		{
			int id = rs.getInt("table_id");
			int num = rs.getInt("table_number");
			int capacity = rs.getInt("capacity");
			String status = rs.getString("status");

			System.out.printf("%-4d %-14d %-10d %-8s\n", id, num, capacity, status);
		}
		
		connect.close();
		statement.close();
	}

}
