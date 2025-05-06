package com.fantastic.restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.fantastic.restaurant.Reservation.Status;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO = new UserDAOImplement();
    private StaffTaskDAO staffTaskDAO = new StaffTaskDAOImplement();
    private RestaurantTableDAO tableDAO = new RestaurantTableDAOImplement();
    private MenuDAO menuDAO = new MenuDAOImplement();
    private InventoryDAO inventoryDAO = new InventoryDAOImplement();


    private JTable userTable;
    private JTable restaurantTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel restaurantTableModel;
    private JTable reservationTable;
    private DefaultTableModel reservationTableModel;
    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JTextField customerPhoneField;
    private JTextField reservationTimeField;
    private JTextField tableIdField;
    private JTextField reservationStatusField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JComboBox<String> roleComboBox;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField searchField;
    private JTextField itemNameField;
    private JTextField itemDescriptionField;
    private JTextField itemCostField;
    private JTextField itemCategoryField;
    private JTextField itemAvailabilityField;
    private JTextField itemQuantityField;
    private JTextField itemUnitField;
    private JTextField itemThresholdField;
    private JButton addInventoryButton;


    public App() {
        setTitle("Restaurant Management App");
        setSize(1300, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadAllUsers();
    }
    
    public App(UserDAO userDAO, StaffTaskDAO staffTaskDAO, RestaurantTableDAO tableDAO, 
            MenuDAO menuDAO, InventoryDAO inventoryDAO) {
     this.userDAO = userDAO;
     this.staffTaskDAO = staffTaskDAO;
     this.tableDAO = tableDAO;
     this.menuDAO = menuDAO;
     this.inventoryDAO = inventoryDAO;
     
     setTitle("Restaurant Management App");
     setSize(1300, 750);
     setDefaultCloseOperation(EXIT_ON_CLOSE);
     setLocationRelativeTo(null);

     initComponents(); // Initialize your components
     loadAllUsers();   // Example of loading users
 }


    private void initComponents() {
        // Create and configure panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(3, 5, 10, 10));

        // Initialize the components
        usernameField = new JTextField();
        passwordField = new JTextField();
        roleComboBox = new JComboBox<>(new String[]{"admin", "staff", "customer"});
        emailField = new JTextField();
        phoneField = new JTextField();
        customerNameField = new JTextField();
        customerEmailField = new JTextField();
        customerPhoneField = new JTextField();
        reservationTimeField = new JTextField();
        tableIdField = new JTextField();
        reservationStatusField = new JTextField();
        itemNameField = new JTextField();
        itemQuantityField = new JTextField();
        itemUnitField = new JTextField();
        itemThresholdField = new JTextField();
        
        // Initialize missing fields
        itemNameField = new JTextField();
        itemDescriptionField = new JTextField();
        itemCostField = new JTextField();
        itemCategoryField = new JTextField();
        itemAvailabilityField = new JTextField();
        
        JButton addButton = new JButton("Add User");
        addButton.addActionListener(e -> addUser());

        searchField = new JTextField();
        JButton searchButton = new JButton("Find User");
        searchButton.addActionListener(e -> findUser());
        
        JButton addReservationButton = new JButton("Add Reservation");
        addReservationButton.addActionListener(e -> {
			try {
				addReservation();
			} catch (DAOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error adding reservation.");
			}
		});
        
        JButton addMenuButton = new JButton("Add Menu Item");
        addMenuButton.addActionListener(e -> {
			try {
				addMenuItem();
			} catch (DAOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error adding menu item.");
			}
		});
        
        addInventoryButton = new JButton("Add Inventory Item");
        addInventoryButton.addActionListener(e -> {
			try {
				addInventoryItem();
			} catch (DAOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error adding inventory item.");
			}
		});

        
        JButton viewMenuButton = new JButton("View Menu");
        viewMenuButton.addActionListener(e -> {
            try {
                loadMenu();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading menu.");
            }
        });
        
        JButton viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.addActionListener(e -> {
            try {
                // Call the loadInventory method that might throw exceptions
                loadInventory();
            } catch (DAOException daoException) {
                // Handle DAO-specific exceptions (e.g., database-related issues)
                JOptionPane.showMessageDialog(null, "Error loading inventory data: " + daoException.getMessage(),
                                              "Database Error", JOptionPane.ERROR_MESSAGE);
                daoException.printStackTrace(); // Optionally log the stack trace for debugging
            } catch (Exception exception) {
                // Catch any other unexpected exceptions
                JOptionPane.showMessageDialog(null, "Unexpected error: " + exception.getMessage(),
                                              "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace(); // Optionally log the stack trace for debugging
            }
        });

        

        // Add components to formPanel
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleComboBox);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(addButton);
        formPanel.add(new JLabel("Search by Username:"));
        formPanel.add(searchField);
        formPanel.add(searchButton);
        formPanel.add(new JLabel("Customer Name:"));
        formPanel.add(customerNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(customerEmailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(customerPhoneField);
        formPanel.add(new JLabel("Table ID:"));
        formPanel.add(tableIdField);
        formPanel.add(new JLabel("Reservation Time:"));
        formPanel.add(reservationTimeField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(reservationStatusField);
        formPanel.add(addReservationButton);
        formPanel.add(new JLabel("Item Name:"));
        formPanel.add(itemNameField);
        formPanel.add(new JLabel("Item Description:"));
        formPanel.add(itemDescriptionField);
        formPanel.add(new JLabel("Item Cost:"));
        formPanel.add(itemCostField);
        formPanel.add(new JLabel("Item Category:"));
        formPanel.add(itemCategoryField);
        formPanel.add(new JLabel("Item Availability:"));
        formPanel.add(itemAvailabilityField);
        formPanel.add(addMenuButton);
        formPanel.add(viewMenuButton);
        formPanel.add(new JLabel("Item Name:"));
        formPanel.add(itemNameField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(itemQuantityField);
        formPanel.add(new JLabel("Unit:"));
        formPanel.add(itemUnitField);
        formPanel.add(new JLabel("Threshold:"));
        formPanel.add(itemThresholdField);
        formPanel.add(addInventoryButton);

        // Initialize table models and tables
        tableModel = new DefaultTableModel(new String[]{"ID", "Username", "Password", "Role", "Email", "Phone"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        restaurantTableModel = new DefaultTableModel(new String[]{"Table ID", "Number", "Capacity", "Status"}, 0);
        restaurantTable = new JTable(restaurantTableModel);
        JScrollPane restaurantScrollPane = new JScrollPane(restaurantTable);
        restaurantScrollPane.setBorder(BorderFactory.createTitledBorder("Restaurant Tables"));
        
        JScrollPane reservationScrollPane = new JScrollPane(reservationTable);
        reservationScrollPane.setBorder(BorderFactory.createTitledBorder("Reservations"));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton loadAllButton = new JButton("Load All Users");
        loadAllButton.addActionListener(e -> loadAllUsers());

        JButton showStaffButton = new JButton("Show Staff");
        showStaffButton.addActionListener(e -> showStaff());

        JButton getStaffIDsButton = new JButton("Get Staff IDs");
        getStaffIDsButton.addActionListener(e -> getStaffIDs());
        
        JButton viewReservationsButton = new JButton("View Reservations");
        viewReservationsButton.addActionListener(e -> {
            try {
                loadReservations();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading reservations.");
            }
        });
        
        buttonPanel.add(viewReservationsButton);
        buttonPanel.add(loadAllButton);
        buttonPanel.add(showStaffButton);
        buttonPanel.add(getStaffIDsButton);
        buttonPanel.add(viewInventoryButton);

        // Layout for auxiliary panels like tasks and table management
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.NORTH);
        
        // Organize the layout of the main content
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        JSplitPane centerSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, restaurantScrollPane);
        centerSplit.setResizeWeight(0.5);
        mainPanel.add(centerSplit, BorderLayout.CENTER);
        
        JSplitPane reservationSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerSplit, reservationScrollPane);
        reservationSplit.setResizeWeight(0.5);
        mainPanel.add(reservationSplit, BorderLayout.CENTER);
        
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Set the content pane
        setContentPane(mainPanel);
    }


    private void addUser() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            int roleIndex = roleComboBox.getSelectedIndex(); // Get the selected index
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            // Map the role index to the corresponding Role enum
            User.Role role = mapIndexToRole(roleIndex);

            // Create a new User object with the correct role
            User user = new User(username, password, role, email, phone);
            
            // Add the user to the database
            try {
				userDAO.add(user);
			} catch (DAOException e) {
				e.printStackTrace();
			}
            loadAllUsers();  // Reload the user list after adding the user

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding user.");
        }
    }

    // Helper method to map the selected index to the Role enum
    private User.Role mapIndexToRole(int index) {
        switch (index) {
            case 0: return User.Role.ADMIN;    // 0 = Admin
            case 1: return User.Role.STAFF;    // 1 = Staff
            case 2: return User.Role.CUSTOMER; // 2 = Customer
            default: throw new IllegalArgumentException("Invalid role index");
        }
    }

    private void loadAllUsers() {
        try {
            tableModel.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Viewing all users in console. GUI view not implemented for DB fetch.");
            try {
				userDAO.view();
			} catch (DAOException e) {
				e.printStackTrace();
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showStaff() {
        try {
            JOptionPane.showMessageDialog(this, "Staff list printed to console.");
            userDAO.showStaff();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving staff.");
        }
    }

    private void getStaffIDs() {
        try {
            List<User> staffList = userDAO.getStaffList();  // Get the list of staff (User objects)
            
            // Create a list of IDs from the staff list
            List<Integer> ids = new ArrayList<>();
            for (User user : staffList) {
                ids.add(user.getId());  // Extract the ID from each User object
            }

            // Display the staff IDs as a comma-separated string
            JOptionPane.showMessageDialog(this, "Staff IDs: " + ids);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving staff IDs.");
        }
    }


    private void findUser() {
        JOptionPane.showMessageDialog(this, "Feature not yet implemented. Requires a `getUserByUsername()` method.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Create instances of the DAOs (dependencies)
                UserDAO userDAO = new UserDAOImplement();
                StaffTaskDAO staffTaskDAO = new StaffTaskDAOImplement();
                RestaurantTableDAO tableDAO = new RestaurantTableDAOImplement();
                MenuDAO menuDAO = new MenuDAOImplement();
                InventoryDAO inventoryDAO = new InventoryDAOImplement();
                
                // Create the App object with all dependencies
                new App(userDAO, staffTaskDAO, tableDAO, menuDAO, inventoryDAO).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred during app initialization.");
            }
        });
    }


    private void loadRestaurantTables() throws SQLException {
        restaurantTableModel.setRowCount(0);
        Connection connect = Database.getConnection();

        String sql = "SELECT * FROM Tables";
        try (PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("table_id");
                int number = rs.getInt("table_number");
                int capacity = rs.getInt("capacity");
                String status = rs.getString("status");

                restaurantTableModel.addRow(new Object[]{id, number, capacity, status});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            connect.close();
        }
    }
    private void loadReservations() throws SQLException {
        reservationTableModel.setRowCount(0);
        Connection connect = Database.getConnection();

        String sql = "SELECT * FROM Reservations";
        try (PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String name = rs.getString("customer_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int tableid = rs.getInt("table_id");
                String datetime = rs.getString("date_time");
                String status = rs.getString("status");

                reservationTableModel.addRow(new Object[]{id, name, email, phone, tableid, datetime, status});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            connect.close();
        }
    }
    private void addReservation() throws DAOException {
        try {
            String name = customerNameField.getText();
            String email = customerEmailField.getText();
            String phone = customerPhoneField.getText();
            int tableId = Integer.parseInt(tableIdField.getText());
            String reservationTime = reservationTimeField.getText();
            String status = reservationStatusField.getText();

            // Default status is "booked" if none provided
            if (status.isEmpty()) status = "booked";

            if (name.isEmpty() || reservationTime.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Reservation Time are required.");
                return;
            }

            Reservation reservation = new Reservation(name, email, phone, tableId, reservationTime, Status.valueOf(status.toUpperCase()).ordinal() + 1);
            ReservationDAO reservationDAO = new ReservationDAOImplement();
            reservationDAO.add(reservation);

            JOptionPane.showMessageDialog(this, "Reservation Added!");
            loadReservations(); // Refresh reservation table

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding reservation.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Table ID format.");
        }
    }
    private void addMenuItem() throws DAOException {
        try {
            String name = itemNameField.getText();
            String description = itemDescriptionField.getText();
            double cost = Double.parseDouble(itemCostField.getText());
            String category = itemCategoryField.getText();
            int availability = Integer.parseInt(itemAvailabilityField.getText());

            if (name.isEmpty() || description.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            Menu menuItem = new Menu(name, description, cost, category, availability);
            menuDAO.add(menuItem);

            JOptionPane.showMessageDialog(this, "Menu item added!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding menu item.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for cost or availability.");
        }
    }
    private void loadMenu() throws SQLException {
        DefaultTableModel menuTableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Description", "Price", "Category", "Availability"}, 0);
        JTable menuTable = new JTable(menuTableModel);
        JScrollPane menuScrollPane = new JScrollPane(menuTable);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("Menu"));

        Connection connect = Database.getConnection();

        String sql = "SELECT * FROM Menu_Items";
        try (PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("menu_item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double cost = rs.getDouble("price");
                String category = rs.getString("category");
                int availability = rs.getInt("availability");

                menuTableModel.addRow(new Object[]{id, name, description, cost, category, availability});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            connect.close();
        }

        JOptionPane.showMessageDialog(this, menuScrollPane);
    }
    private void addInventoryItem() throws DAOException {
        try {
            String itemName = itemNameField.getText();
            int quantity = Integer.parseInt(itemQuantityField.getText());
            String unit = itemUnitField.getText();
            int threshold = Integer.parseInt(itemThresholdField.getText());

            if (itemName.isEmpty() || unit.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item Name and Unit are required.");
                return;
            }

            Inventory inventory = new Inventory(itemName, quantity, unit, threshold);
            inventoryDAO.add(inventory);
            JOptionPane.showMessageDialog(this, "Inventory item added!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding inventory item.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for quantity or threshold.");
        }
    }
    private void loadInventory() throws DAOException {
        try {
            inventoryDAO.view();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading inventory.");
        }
    }


}

