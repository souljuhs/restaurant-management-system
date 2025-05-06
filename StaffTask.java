package com.fantastic.restaurant;

/* |---------------------------------------------------|
 * |					StaffTask					   |
 * |---------------------------------------------------|
 * | - userid: int 									   |
 * | - description: String							   |
 * | - status: String								   |
 * |---------------------------------------------------|
 * | + setUserId(id: int): void						   |
 * | + setDescription(des: String): void			   |
 * | + setStatus(status: int): void					   |
 * | + getUserId(): Int								   |
 * | + getDescription(): String						   |
 * | + getStatus(): String							   |
 * |---------------------------------------------------|	
 * */

public class StaffTask 
{
	private int userid;
	private String description;
	private String status; 

	enum Status
	{
		assigned,
		in_progress,
		completed
	}
	
	public StaffTask(int id, String des, int status)
	{
		setUserId(id);
		setDescription(des);
		setStatus(status);
	}
	
	public void setUserId(int id)
	{
		this.userid = id;
	}
	
	public void setDescription(String des)
	{
		this.description = des;
	}
	
	public void setStatus(int status)
	{
		Status temp;
		
		if(status == 1)
		{
			temp = Status.assigned;
			this.status = temp.toString();
		}
		else if(status == 2)
		{
			temp = Status.in_progress;
			this.status = temp.toString();
		}
		else
		{
			temp = Status.completed;
			this.status = temp.toString();
		}
		
	}
	
	public int getUserid()
	{
		return userid;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getStatus()
	{
		return status;
	}
	
}