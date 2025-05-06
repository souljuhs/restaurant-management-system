package com.fantastic.restaurant;

/* |---------------------------------------------|
 * |				   User	  				     |
 * |---------------------------------------------|
 * | - username: String							 |
 * | - password: String							 |
 * | - role: String								 |
 * | - email: String							 |
 * | - phone: String							 |
 * |---------------------------------------------|
 * | + Role: enum								 |
 * | + setUsername(usernm: String): void		 |
 * | + setPassword(password: String: void        |
 * | + setRole()								 |
 * | + setEmail(email: String): void			 |
 * | + setPhone(phone: String): void			 |
 * | + getUsername(): String					 |
 * | + getPassword(): String					 |
 * | + getEmail(): String						 |
 * | + getPhone(): String						 |
 * |---------------------------------------------|
 */ 

public class User 
{
	private String username;
	private String password;
	private String role;
	private String email;
	private String phone;
	
	public User(String username, String password, int role, String email, String phone)
	{
		setUsername(username);
		setPassword(password);
		setRole(role);
		setEmail(email);
		setPhone(phone);
	}
	
	enum Role
	{
		admin,
		staff,
		customer
	}
	
	public void setUsername(String usernm)
	{
		this.username = usernm;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setRole(int roleNum)
	{
		Role roleholder;
		
		if(roleNum == 1)
		{
			roleholder = Role.admin;
			this.role = roleholder.toString();
		}
		else if(roleNum == 2)
		{
			roleholder = Role.staff;
			this.role = roleholder.toString();
		}
		else
		{
			roleholder = Role.customer;
			this.role = roleholder.toString();
		}
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getRole()
	{
		return role;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
}
