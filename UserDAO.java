package com.fantastic.restaurant;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends DAO<User>
{
	void showStaff() throws SQLException;
	
	ArrayList<Integer> getStaffList() throws SQLException;
}
