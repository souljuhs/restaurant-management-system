package com.fantastic.restaurant;

import java.sql.SQLException;

public interface ReservationDAO extends DAO<Reservation>
{
	void update(String status, int id) throws SQLException; 
}
