package com.fantastic.restaurant;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StaffTaskDAO extends DAO<StaffTask> 
{
	ArrayList<Integer> getTaskList() throws SQLException;
	
	void update(String status, int id, String time) throws SQLException;
}
