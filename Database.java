package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database 
{
	public static String url = "jdbc:mysql://localhost:3306/restaurant_db";
    public static String username = "arv";
    public static String password = "wpaksj8239!";
    
    private Database()
    {
    	
    }
    
    //Gives connection to the database
    public static Connection getConnection() throws SQLException
    {
    	Connection connection = null;
    	connection = DriverManager.getConnection(url, username, password);
    	
    	return connection;
    }
}
