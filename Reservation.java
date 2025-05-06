package com.fantastic.restaurant;

/* |---------------------------------------------|
 * |			   Reservation	  			     |
 * |---------------------------------------------|
 * | - name: String							 	 |
 * | - email: String							 |
 * | - phone: String							 |
 * | - tableID: int								 |
 * | - datetime: Timestamp						 |
 * | - status: String							 |
 * |---------------------------------------------|
 * | + setName(name: String): void				 |
 * | + setEmail(email: String): void			 |
 * | + setPhone(phone: String): void			 |
 * | + setTableID( tableID: int): void			 |
 * | + setDateTime(current: LocalDateTime): void |
 * | + setTableStatus(status: int): void		 |
 * | + getName(): String						 |
 * | + getEmail(): String						 |
 * | + getPhone(): String						 |
 * | + getTableID(): int						 |
 * | + getDateTime(): Timestamp					 |
 * | + getTableStatus(): String					 |
 * |---------------------------------------------|
 */ 
import java.sql.*;
import java.time.LocalDateTime;

public class Reservation 
{
	private String customerName; 		//Required
	private String customerEmail; 		//Not Required
	private String customerPhoneNumber; //Not Required
	private int tableID;				//Not Required
	private String reservationTime;		//Required
	private String tableStatus;			//Default Booked if not status not set
	
	public Reservation(String name, String email, String phone, int id, String time, int status)
	{
		setCustomerName(name);
		setCustomerEmail(email);
		setCustomerPhoneNumber(phone);
		setTableId(id);
		setReservationTime(time);
		setStatus(status);
	}
	
	enum Status
	{
		booked,
		cancelled,
		completed
	}
	
	//Setters~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void setCustomerName(String name)
	{
		this.customerName = name;
	}
	
	public void setCustomerEmail(String email)
	{
		this.customerEmail = email;
	}
	
	public void setCustomerPhoneNumber(String phone)
	{
		this.customerPhoneNumber = phone;
	}
	
	public void setTableId(int id)
	{
		this.tableID = id;
	}

	public void setReservationTime(String time)
	{
		//Timestamp resTime = Timestamp.valueOf(time);
		
		this.reservationTime = time;
	}
	
	public void setStatus(int status)
	{
		Status temp;
		
		if(status == 1)
		{
			temp = Status.booked;
			tableStatus = temp.toString();
		}
		else if(status == 2)
		{
			temp = Status.cancelled;
			tableStatus = temp.toString();
		}
		else if(status == 3)
		{
			temp = Status.completed;
			tableStatus = temp.toString();
		}
		else 
		{
			temp = Status.booked;
			tableStatus = temp.toString();
		}
	}
	
	//Getters~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String getCustomerName()
	{
		return customerName;
	}
	
	public String getCustomerEmail()
	{
		return customerEmail;
	}
	
	public String getCustomerPhoneNumber()
	{
		return customerPhoneNumber;
	}
	
	public int getTableId()
	{
		return tableID;
	}
	
	public String getReservationTime()
	{
		return reservationTime;
	}
	
	public String getStatus()
	{
		return tableStatus;
	}
	
}