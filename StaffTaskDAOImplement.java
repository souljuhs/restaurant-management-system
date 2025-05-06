package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffTaskDAOImplement implements StaffTaskDAO
{

	@Override
	public void add(StaffTask staff) throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "INSERT INTO Staff_Tasks (user_id, task_description, status) VALUES (?, ?, ?)";
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			statement.setInt(1, staff.getUserid());
			statement.setString(2, staff.getDescription());
			statement.setString(3, staff.getStatus());
			statement.executeUpdate();
			
			System.out.println("Staff Member whose id = " + staff.getUserid() + " has been given assigned a task!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
	}

	@Override
	public void view() throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "SELECT * FROM Staff_Tasks";
		
		ResultSet rs = null;
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			rs = statement.executeQuery();
			
			System.out.printf("%-7s %-7s %-32s %-14s %-25s %-20s\n", "TaskID", "UserID", "Description", "Status", "Time_Assigned", "Time_Completed");
			
			while(rs.next())//Runs as long as there are rows in the table
			{
				int taskid = rs.getInt("task_id");
				int userid = rs.getInt("user_id");
				String description = rs.getString("task_description");
				String status = rs.getString("status");
				String assigned = rs.getString("assigned_at");
				String completed = rs.getString("completed_at");

				System.out.printf("%-7d %-7d %-32s %-14s %-25s %-20s\n", taskid, userid, description, status, assigned, completed);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
	}

	public ArrayList<Integer> getTaskList() throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "SELECT * FROM Staff_Tasks";
		
		ResultSet rs = null;
		
		ArrayList<Integer> taskList = new ArrayList<>();
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			rs = statement.executeQuery();
			
			while(rs.next())//Runs as long as there are rows in the table
			{
				int id = rs.getInt("task_id");
				taskList.add(id);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
		return taskList;
	}

	
	@Override
	public void update(String status, int id, String time) throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		String sqlStatement = "UPDATE Staff_Tasks SET status = ?, completed_at = ? WHERE task_id = ?";
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{			
			statement.setString(1, status);
			statement.setString(2, time);
			statement.setInt(3, id);
			statement.executeUpdate();

			System.out.println("Staff Task where id = " + id + " updated to " + status + "!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
	}
}
