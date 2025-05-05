package com.fantastic.restaurant;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FantasticRestaurant 
{
	public static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) throws SQLException, InterruptedException 
	{
	    int option;//Holds user's choice for interacting with the system
		    
		while(true)
		{
		   System.out.println("Restaurant Management System Menu:\n"
		    			+ "1.  Add Menu Item\n"
		    			+ "2.  View Menu\n"
		    			+ "3.  Add User\n"
		    			+ "4.  View Users\n"
		    			+ "5.  Add to Inventory\n"
		    			+ "6.  View Inventory\n"
		    			+ "7.  Modify Order Item\n" 
		    			+ "8.  Update Order Status\n"
		    			+ "9.  Check Inventory (Low Stock Alerts)\n" 
		    			+ "10. Place an Order\n" 
		    			+ "11. Add Reservation\n"  
		    			+ "12. View Reservation\n" 
		    			+ "13. Update Reservations\n" 
		    			+ "14. Assign Task to Staff\n" 
		    			+ "15. View Staff Tasks\n" 
		    			+ "16. Update Staff Task\n"
		    			+ "17. Create Restaurant Table\n"
		    			+ "0. Exit\n" 
		    			+ "Enter Choice: ");
		    	
		   option = scnr.nextInt();
		    	
		   scnr.nextLine();//Takes the leftover newline from previous input
		    	
		   if(option == 0)
		   {
			   System.out.println("Goodbye.");
			   break;
		   }//End of IF STATEMENT
		    
		   switch(option)
		   {
		   	   case 1:
		   		   addMenuItem();
		   	       break;
		       case 2:
		    	   MenuDAO menu = new MenuDAOImplement();
		    	   menu.view();
		    	   System.out.println("\n\n\n");
	    		   Thread.sleep(5000);
		           break;
	    	   case 3:
	    		   addUser();
	    		   break;
	    	   case 4:
	    		   UserDAO user = new UserDAOImplement();
	    		   user.view();
	    		   System.out.println("\n\n\n");
	    		   Thread.sleep(5000);
	    		   break;
	    	   case 5:
	    		   addToInventory();
	    		   break;
	    	   case 6:
	    		   InventoryDAO inventoryDAO = new InventoryDAOImplement();
	    		   inventoryDAO.view();
	    		   System.out.println("\n\n\n");
	    		   Thread.sleep(5000);
	    		   break;
	    	   case 11:
	    		   addReservation();
	    		   break;
	    	   case 12:
	    		   ReservationDAO reservationDAO = new ReservationDAOImplement();
	    		   reservationDAO.view();
	    		   System.out.println("\n\n\n");
	    		   Thread.sleep(5000);
	    		   break;
	    	   case 13:
	    		   updateReservation();
	    		   break;
	    	   case 14:
	    		   assignTaskToStaff();
	    		   break;
	    	   case 15:
	    		   StaffTaskDAO stDAO = new StaffTaskDAOImplement();
	    		   stDAO.view();
	    		   System.out.println("\n\n\n");
	    		   Thread.sleep(5000);
	    		   break;
	    	   case 16:
	    		   updateStaffTasks();
	    		   break;
	    	   case 17:
	    		   createRestaurantTable();
	    		   break;
		       default:
		    	   System.out.println("Must select from available options!");
		   }//End of SWITCH CASE
		    	
	    }//End of WHILE LOOP
		
	}//End of MAIN METHOD
	
	//Used to make a custome time and date
	public static LocalDateTime getTime()
	{
		int year, month, day, hour, min;
		
		System.out.print("Enter year (2000): ");
		year = scnr.nextInt();
		
		System.out.print("Enter month (1-12): ");
		month = scnr.nextInt();
		
		System.out.print("Enter day (1 - 31): ");
		day = scnr.nextInt();
		
		System.out.print("Enter hour (0 - 23): ");
		hour = scnr.nextInt();
		
		System.out.print("Enter min (0 - 59): ");
		min = scnr.nextInt();
		
		LocalDateTime time = LocalDateTime.of(year,  month, day, hour, min);
		
		return time;
	}
	
	//Case 1
	public static void addMenuItem() throws SQLException
	{
		String answer;
		
		do
		{
			String name; //Hold the name of the item
			String description; //Hold the description
			double cost = 0.0; //Hold the price of the item
			String category; //
			int available;
			
			boolean check = false;
			
			//Item's Name---------------------------------------------------
			do //Prevents the name from being null
			{
				//Will ask for and take the user input of the item's name 
				System.out.println("Enter the item's name:");
				name = scnr.nextLine();
				
				if(name.isEmpty())
				{
					System.out.println("MUST enter item's name!");
				}
				
			}while(name.isEmpty());
			
			//Description of item--------------------------------------------
			System.out.println("Describe the item: ");
			description = scnr.nextLine();
			
			//Item's Price---------------------------------------------------
			String input = ""; //Will hold the initial input for empty check
			while(!check)
			{
				do //Prevents the price from being null
				{
					//Will ask for and take the user input of the item's price 
					System.out.println("Enter the item's price:");
					input = scnr.nextLine();
					
					if(input.isEmpty()) //Asks the user to re-enter the price
					{
						System.out.println("MUST enter item's price!");
					}
					
				}while(input.isEmpty());
				
				try //Checks if input was a number
				{
					cost = Double.parseDouble(input);
					
					if(cost < 0) //Checks if cost is negative
					{
						System.out.println("Item's price CANNOT be negtive!");
					}
					else
					{
						check = true; //break out of loop if entered a proper price
					}
				}
				catch(NumberFormatException e) //Throws exception with non-number inputs
				{
					System.out.println("Numbers only!");
				}
			}
			
			//Item's Category-----------------------------------------------------
			System.out.println("Enter the item's category:");
			category = scnr.nextLine();
			
			//Item's Availability-------------------------------------------------
			do //Has the user enter 0 or 1 anything else will ask the user to re-enter
			{
				System.out.println("Enter the currently availablity(1 for available, 0 for unavailable):");
				available = scnr.nextInt();
				
				if(available < 0 || available > 1)
				{
					System.out.println("1 or 0 must be entered! \n0 for unavailable.\n1 for available.");
				}
				
			}while(available != 0 && available != 1);
			
			Menu newItem = new Menu(name, description, cost, category, available);
			
			MenuDAO menu = new MenuDAOImplement();
	   		menu.add(newItem);
			
			//Asks the user if they would like to add more items to the table before exiting
			System.out.println("Would you like to add another item?");
			answer = scnr.nextLine();
			
		}while(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"));
	}
	
	//Case 3
	public static void addUser() throws SQLException
	{
		String addAnother; //Used to ask if if another user will be added
		
		String userName;
		String password = "";
		int role = 0;
		String email;
		String phone = "";
		
		boolean isPassword;
		boolean isNum;
		boolean isPhone;
		
		do
		{
			isPassword = false;
			isNum = false;
			isPhone = false;
			
			System.out.println("Username: ");//Gets username for new user
			userName = scnr.nextLine();
			
			while(!isPassword)
			{
				System.out.println("Password: ");//Gets password for new user
				password = scnr.nextLine();
				
				String specialChar = ".*\\W+.*";
		        
		        // Use the matches() method to check if the string matches the regex.
				if(password.length() > 6)
				{
			        if(password.matches(specialChar))
			        {
			        	isPassword = true;
			        }
			        else
			        {
			        	System.out.println("Password must contain a special character!");
			        }
				}
				else
				{
					System.out.println("Password too short! Must be at least 7 characters long!");
				}
			}	
			
			while(!isNum) //User must enter 1, 2, or 3 to select a role
			{
				System.out.println("Roles are: \n1. admin \n2. staff \n3. customer \nEnter role: ");
				role = scnr.nextInt();
					
				if(role > 0 && role < 4)
				{
					isNum = true;
				}
				else
				{
					System.out.println("MUST SELECT FROM GIVEN ROLES (1 - 3)!");
				}
			}
			
			scnr.nextLine();
			
			System.out.println("Enter user email: "); //Gets new user's email
			email = scnr.nextLine();
			
			while(!isPhone)//Checks if valid phone number was used
			{
				System.out.println("Enter user phone number: ");
				phone = scnr.nextLine();
				
			    // Regular expression to check if
			    // the number starts with
				// 7, 8, or 9, followed by 9 digits
				String comparison = "^[1-9][0-9]{9}$";

				// Check if the number matches
				// the regular expression
				if (phone.matches(comparison)) 
				{
				    isPhone = true;
				}
				else
				{
					System.out.println("Invalid phone number!");
				}
			}
	
			//Creates the a new user object
			User newUser = new User(userName, password, role, email, phone);
			
			UserDAO userDAO = new UserDAOImplement();
			userDAO.add(newUser);
			
			System.out.println("Would you like to add another user?");
			addAnother = scnr.nextLine();
		}while(addAnother.equalsIgnoreCase("yes") || addAnother.equalsIgnoreCase("y"));
	}
	
	//Case 5
	public static void addToInventory() throws SQLException
	{
		String add = ""; //Holds answer for adding new item question
		
		String itemName; //Holds the name of item
		int quantity;    //Holds current stock quantity
		String unit;     //Holds unit (kg, box, pack, liter, etc)
		int threshold;   //Holds min number for low stock alert
		
		do
		{
			//Asks for name of item(Required)
			System.out.print("Item Name: ");
			itemName = scnr.nextLine();
			
			//Asks for current stock quantity(Required)
			System.out.print("\nQuantity: ");
			quantity = scnr.nextInt();
			scnr.nextLine();
			
			//Asks for unit of measurement (Not Required)
			System.out.print("\nUnit of measurement: ");
			unit = scnr.nextLine();
			
			//Asks for item threshold (Default is 10 if not given, used for low stock alert)
			System.out.print("\nItem Threshold(10 if left blank): ");
			threshold = scnr.nextInt();
			
			//Adds item to the inventory
			Inventory item = new Inventory(itemName, quantity, unit, threshold);
			
			//Adds to the Inventory Table in the database
			InventoryDAO inventoryDAO = new InventoryDAOImplement();
			inventoryDAO.add(item);
			
			scnr.nextLine();
			System.out.println("Would you like to add an addition item?:");
			add = scnr.nextLine();
			
		}while(add.equalsIgnoreCase("yes") || add.equalsIgnoreCase("y"));
		
	}
	
	//Case 11
	public static void addReservation() throws SQLException
	{
		String customerName;				//Required
		String customerEmail;				//Not Required
		String customerPhone = "";			//Not Required
		int tableid = 0;					//Not Required
		LocalDateTime reservationDateTime;	//Required
		int tableStatus = 0;				//Not Required(1/booked automatically)
		
		boolean isPhone;
		boolean isTable;
		boolean isStatus;
		String addAnotherReservation = "";
		
		ArrayList<Integer> tableID = new ArrayList<>();
		
		RestaurantTableDAO tableDAO = new RestaurantTableDAOImplement();
		
		do
		{
			isPhone = false;
			isTable = false;
			isStatus = false;
			
			//Asks for customer's name
			System.out.print("Enter name of customer: ");
			customerName = scnr.nextLine();
			
			//Asks for customer's email(NOT REQUIRED)
			System.out.print("Email(Not Required): ");
			customerEmail = scnr.nextLine();
			
			//Asks for customer's phone number(NOT REQUIRED)
			while(!isPhone)//Checks if valid phone number was used
			{
				System.out.println("Phone (Not Required): ");
				customerPhone = scnr.nextLine();
				
				String comparison = "^[1-9][0-9]{9}$";

				if(customerPhone.equalsIgnoreCase(""))//Entering nothing is acceptable
				{
					isPhone = true;
				}
				else
				{
					if (customerPhone.matches(comparison)) 
					{
					    isPhone = true;
					}
					else
					{
						System.out.println("Invalid phone number!");
					}
				}
			}
			System.out.println();
			
			//Assigns a table id(check for free tables first)
			//ViewTables vt = new ViewTables();
			//tableID = vt.showTables();
			tableDAO.view();
			
			while(!isTable)
			{
				System.out.print("Table ID: ");
				tableid = scnr.nextInt();
				
				if(tableID.contains(tableid))
				{
					isTable = true;
				}
				else
				{
					System.out.println("\nTable does not exist!");
				}
			}
			
			//Asks for the time of reservation
			LocalDateTime reservationTime = getTime();
			String reserveTime = reservationTime.toString();
			
			//Asks for table status
			while(!isStatus)
			{
				System.out.println("\n1. booked\n2. cancelled\n3. completed");
				System.out.println("Table Status: ");
				tableStatus = scnr.nextInt();
				
				if(tableStatus > 0 && tableStatus < 4)
				{
					isStatus = true;
				}
				else
				{
					System.out.println("INVALID!");
				}
			}
			Reservation newReservation = new Reservation(customerName, customerEmail, customerPhone, tableid, reserveTime, tableStatus);
			
			ReservationDAO reservationDAO = new ReservationDAOImplement();
			reservationDAO.add(newReservation);
				
			//Ask if user wants to make another reservation
			System.out.println("Would you like to make another reservation?: ");
			addAnotherReservation = scnr.nextLine();
		}while(addAnotherReservation.equalsIgnoreCase("yes") || addAnotherReservation.equalsIgnoreCase("y"));
	
		
	}

	//Case 13
	public static void updateReservation() throws SQLException
	{
		ReservationDAO reserveDAO = new ReservationDAOImplement();
		reserveDAO.view();
		
		String status = "";
		boolean isStatus = false;
		
		System.out.println("Enter Reservation ID:");
		int id = scnr.nextInt();
		
		//Asks for table status
		while(!isStatus)
		{
			System.out.println("\n1. booked\n2. cancelled\n3. completed");
			System.out.println("Table Status: ");
			int num = scnr.nextInt();
			
			if(num > 0 && num < 4)
			{
				if(num == 2)
				{
					status = "cancelled";
					isStatus = true;
				}
				else if(num == 3)
				{
					status = "completed";
					isStatus = true;
				}
			}
			else
			{
				System.out.println("INVALID!");
			}
		}
		
		reserveDAO.update(status, id);
	}
	
	//Case 14
	public static void assignTaskToStaff() throws SQLException
	{
		int id = 0; 
		int status = 0; 
		String description;
		boolean isStaff = false;
		boolean isStatus = false;
		ArrayList<Integer> idFromDb;
		
		UserDAO userDAO = new UserDAOImplement();
		userDAO.showStaff();
		idFromDb = userDAO.getStaffList();
		
		System.out.println("\nCurrent Employee Tasks:-----------------------------------------------------------");
		StaffTaskDAO stDAO = new StaffTaskDAOImplement();
		stDAO.view();
		
		while(!isStaff)
		{
			System.out.println("Enter staff member's ID: ");
			id = scnr.nextInt();
			
			for(int i = 0; i < idFromDb.size(); i++)//Iterates through list of id for staff members
			{
				if(idFromDb.get(i) != id)
				{
					if(i == (idFromDb.size() - 1))//if id isn't in the list, it's not a staff member
					{
						System.out.println("Not a staff member!");
					}
				}
				else 
				{
					isStaff = true;
					break;
				}
			}
		}
		
		scnr.nextLine();
		
		System.out.println("Description of task: ");
		description = scnr.nextLine();
		
		System.out.println("Select status from the following:");
		while(!isStatus)
		{
			//Get status of task
			System.out.print("1. Assigned \n2. In Progress \n3. Completed");
			System.out.print("\nStatus: ");
			status = scnr.nextInt();
			
			if(status > 0 && status < 4)
			{
				isStatus = true;
			}
			else
			{
				System.out.println("Invalid! Must select from the following:");
			}	
		}//End of STATUS while loop
		scnr.nextLine();
		
		StaffTask staff = new StaffTask(id, description, status);
		
		//StaffTaskDAO stDAO = new StaffTaskDAOImplement();
		stDAO.add(staff);
		
	}
	
	//Case 16
	public static void updateStaffTasks() throws SQLException
	{
		int taskid = 0;				 //Holds chosen id
		int num = 0;				 //Holds chosen status
		String status = "";			 //Holds status
		String formattedTime = "";   //Holds the current time when task is completed
		boolean isStaff = false;	 //Used to loop to make sure proper id was chosen
		boolean isStatus = false;	 //Used to loop to make sure a proper status was chosen
		ArrayList<Integer> tasklist; //Holds list of task ids to check if proper id was input
		
		StaffTaskDAO stDAO = new StaffTaskDAOImplement();
		stDAO.view();
		tasklist = stDAO.getTaskList();
		
		while(!isStaff)
		{
			System.out.println("Enter task ID: ");
			taskid = scnr.nextInt();

			for(int i = 0; i < tasklist.size(); i++)//Iterates through list of id for staff members
			{
				if(tasklist.get(i) != taskid)
				{
					if(i == (tasklist.size() - 1))//if id isn't in the list, it's not a staff member
					{
						System.out.println("Not a valid id for tasks!");
					}
				}
				else 
				{
					isStaff = true;
					break;
				}
			}
		}
		
		//Status---
		System.out.println("Select status from the following:");
		while(!isStatus)
		{
			//Get status of task
			System.out.print("1. In Progress \n2. Completed");
			System.out.print("\nStatus: ");
			num = scnr.nextInt();
			
			if(num > 0 && num < 3)
			{
				if(num == 1)
				{
					isStatus = true;
					status = "in_progress";
					formattedTime = null;
				}
				else
				{
					status = "completed";
					
					//Gets current time and converts to a string 
					LocalDateTime current = LocalDateTime.now();
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					formattedTime = current.format(format);
					
					isStatus = true;
				}
			}
			else
			{
				System.out.println("Invalid! Must select from the following:");
			}	
		}//End of STATUS while loop
		scnr.nextLine();

		stDAO.update(status, taskid, formattedTime);
		
	}
	
	//Case 17
	//Case 17
	public static void createRestaurantTable() throws SQLException
	{
		int tableNumber, seatCapacity, tableStatus;
		String status = "";
		
		boolean isStatus;
		
		String add; //Used to hold the answer if user wants to add another table 
		
		RestaurantTableDAO tableDAO = new RestaurantTableDAOImplement();
		tableDAO.view();
		
		do
		{
			isStatus = false;
			//Assigns table a number
			System.out.print("Table #: ");
			tableNumber = scnr.nextInt();
			
			//Gets capacity for table
			System.out.print("Seat capacity: ");
			seatCapacity = scnr.nextInt();
			
			while(!isStatus)
			{
				//Gets status of the table (free, occupied, reserved)
				System.out.println("1. free \n2. occupied \n3. reserved");
				System.out.print("Select table status: ");
				tableStatus = scnr.nextInt(); 
				
				//String result = (x > 10) ? "High" : "Low";
				if(tableStatus < 4 && tableStatus > 0)
				{
					if(tableStatus == 1)
					{
						status = "free";
						isStatus = true;
					}
					else if(tableStatus == 2)
					{
						status = "occupied";
						isStatus = true;
					}
					else
					{
						status = "reserved";
						isStatus = true;
					}
				}
				else 
				{
					System.out.println(tableStatus + " is not an option!");
				}
			}
			
			scnr.nextLine();
			
			RestaurantTable table = new RestaurantTable(tableNumber, seatCapacity, status);
			
			tableDAO.add(table);
			
			//AddRestaurantTable addTable = new AddRestaurantTable(table);
			
			System.out.println("Would you like to add another table?:");
			add = scnr.nextLine();
			
		}while(add.equalsIgnoreCase("yes") || add.equalsIgnoreCase("y"));
	}
	
	
}
