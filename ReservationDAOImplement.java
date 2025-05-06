package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationDAOImplement implements ReservationDAO
{

	@Override
	public void add(Reservation reservation) throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		//A sql query as a java string that will insert a new reservation into the Reservation table 
		String sqlStatement = "INSERT INTO Reservations (customer_name, email, phone, table_id, date_time, status) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			statement.setString(1, reservation.getCustomerName());
			statement.setString(2, reservation.getCustomerEmail());
			statement.setString(3, reservation.getCustomerPhoneNumber());
			statement.setInt(4, reservation.getTableId());
			statement.setString(5, reservation.getReservationTime());
			statement.setString(6, reservation.getStatus());
			statement.executeUpdate();
			
			System.out.println("Reservation Made!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
	}

	@Override
	public void view() throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		ResultSet rs = null;//will hold the Reservations table data once the query is used
		
		//A sql query as a java string that will show the items in the Reservations table 
		String sqlStatement = "SELECT * FROM Reservations";
		
		try
		{	
			//Runs the sql query using the statement object
			PreparedStatement statement = connect.prepareStatement(sqlStatement);
			rs = statement.executeQuery();
			
			//Prints out the column titles of the table
			System.out.printf("%-3s %-8s %-20s %-11s %-3s %-20s %-10s\n", "ID", "Name", "Email", "Phone#", "TableID", "ReservationTime", "Status");
			
			while(rs.next())//Runs as long as there are rows in the table
			{
				int id = rs.getInt("reservation_id");
				String name = rs.getString("customer_name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				int tableid = rs.getInt("table_id");
				String datetime = rs.getString("date_time");
				String status = rs.getString("status");

				System.out.printf("%-3d %-8s %-20s %-11s %-7d %-20s %-8s\n", id, name, email, phone, tableid, datetime, status);
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		connect.close();
	}

	@Override
	public void update(String status, int id) throws SQLException 
	{
		Connection connect = Database.getConnection();
		
		//A sql query as a java string that will insert a new reservation into the Reservation table 
		String sqlStatement = "UPDATE Reservations SET status = ? WHERE reservation_id = ?";
		
		try(PreparedStatement statement = connect.prepareStatement(sqlStatement))
		{
			statement.setString(1, status);
			statement.setInt(2, id);
			statement.executeUpdate();
			
			System.out.println("Reservation #" + id + " updated to " + status + "!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connect.close();
		
	}

}
