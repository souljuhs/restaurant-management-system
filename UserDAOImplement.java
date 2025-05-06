package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImplement implements UserDAO
{

	@Override
	public void add(User user) throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "INSERT INTO Users (username, password_hash, role, email, phone) VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getRole());
			statement.setString(4, user.getEmail());
			statement.setString(5, user.getPhone());
			statement.executeUpdate();
			
			System.out.println("New User Created!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
	}

	@Override
	public void view() throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "SELECT * FROM Users";
		
		ResultSet rs = null;
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			rs = statement.executeQuery();
			
			System.out.printf("%-3s %-10s %-12s %-8s %-25s %-10s\n", "ID", "Name", "Password", "Role", "Email", "Phone");
			
			while(rs.next())//Runs as long as there are rows in the table
			{
				int id = rs.getInt("user_id");
				String username = rs.getString("username");
				String password = rs.getString("password_hash");
				String role = rs.getString("role");
				String email = rs.getString("email");
				String phone = rs.getString("phone");

				System.out.printf("%-3d %-10s %-12s %-8s %-25s %-10s\n", id, username, password, role, email, phone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
	}

	@Override
	public void showStaff() throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "SELECT * FROM Users";
		
		ResultSet rs = null;
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			rs = statement.executeQuery();
			
			System.out.printf("%-3s %-10s %-12s %-8s %-25s %-10s\n", "ID", "Name", "Password", "Role", "Email", "Phone");
			
			while(rs.next())//Runs as long as there are rows in the table
			{
				if(rs.getString("role").equalsIgnoreCase("staff"))
				{
					int id = rs.getInt("user_id");
					String username = rs.getString("username");
					String password = rs.getString("password_hash");
					String role = rs.getString("role");
					String email = rs.getString("email");
					String phone = rs.getString("phone");
	
					System.out.printf("%-3d %-10s %-12s %-8s %-25s %-10s\n", id, username, password, role, email, phone);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
	}

	@Override
	public ArrayList<Integer> getStaffList() throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "SELECT * FROM Users";
		
		ResultSet rs = null;
		
		ArrayList<Integer> userList = new ArrayList<>();
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			rs = statement.executeQuery();
			
			while(rs.next())//Runs as long as there are rows in the table
			{
				if(rs.getString("role").equalsIgnoreCase("staff"))
				{
					int id = rs.getInt("user_id");
					userList.add(id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
		return userList;
	}


}
