package com.fantastic.restaurant;

import java.sql.SQLException;

public interface DAO <T> 
{
	void add(T t) throws SQLException;
	
	void view() throws SQLException; 

}
