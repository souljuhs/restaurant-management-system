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
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private StaffTaskDAO staffTaskDAO;
    private RestaurantTableDAO tableDAO;
    private MenuDAO menuDAO;
    private InventoryDAO inventoryDAO;

    private JTable userTable;
    private JTable restaurantTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel restaurantTableModel;
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

        initComponents();
        loadAllUsers();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(3, 5, 10, 10));

        initializeFormFields();

        JButton addButton = new JButton("Add User");
        addButton.addActionListener(e -> {
			try {
				addUser();
			} catch (DAOException e1) {
				e1.printStackTrace();
			}
		});

        JButton addReservationButton = new JButton("Add Reservation");
        addReservationButton.addActionListener(e -> addReservation());

        JButton addMenuButton = new JButton("Add Menu Item");
        addMenuButton.addActionListener(e -> addMenuItem());

        addInventoryButton = new JButton("Add Inventory Item");
        addInventoryButton.addActionListener(e -> addInventoryItem());

        JButton viewMenuButton = new JButton("View Menu");
        viewMenuButton.addActionListener(e -> loadMenu());

        JButton viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.addActionListener(e -> loadInventory());

        searchField = new JTextField();
        JButton searchButton = new JButton("Find User");
        searchButton.addActionListener(e -> findUser());

        addComponentsToFormPanel(formPanel, addButton, addReservationButton, addMenuButton, viewMenuButton, viewInventoryButton, searchButton);

        // Initialize table models and tables
        initializeTables();

        // Layout for auxiliary panels
        JPanel buttonPanel = new JPanel();
        addButtonsToPanel(buttonPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        JSplitPane centerSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(userTable), new JScrollPane(restaurantTable));
        centerSplit.setResizeWeight(0.5);
        mainPanel.add(centerSplit, BorderLayout.CENTER);

        JSplitPane reservationSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerSplit, new JScrollPane(new JTable(reservationTableModel)));
        reservationSplit.setResizeWeight(0.5);
        mainPanel.add(reservationSplit, BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
    }

    private void initializeFormFields() {
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
        itemDescriptionField = new JTextField();
        itemCostField = new JTextField();
        itemCategoryField = new JTextField();
        itemAvailabilityField = new JTextField();
        itemQuantityField = new JTextField();
        itemUnitField = new JTextField();
        itemThresholdField = new JTextField();
    }

    private void addComponentsToFormPanel(JPanel formPanel, JButton addButton, JButton addReservationButton, JButton addMenuButton, JButton viewMenuButton, JButton viewInventoryButton, JButton searchButton) {
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
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(itemQuantityField);
        formPanel.add(new JLabel("Unit:"));
        formPanel.add(itemUnitField);
        formPanel.add(new JLabel("Threshold:"));
        formPanel.add(itemThresholdField);
        formPanel.add(addInventoryButton);
    }

    private void initializeTables() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Username", "Password", "Role", "Email", "Phone"}, 0);
        userTable = new JTable(tableModel);

        restaurantTableModel = new DefaultTableModel(new String[]{"Table ID", "Number", "Capacity", "Status"}, 0);
        restaurantTable = new JTable(restaurantTableModel);

        reservationTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Table ID", "Time", "Status"}, 0);
    }

    private void addButtonsToPanel(JPanel buttonPanel) {
        JButton loadAllButton = new JButton("Load All Users");
        loadAllButton.addActionListener(e -> loadAllUsers());

        JButton showStaffButton = new JButton("Show Staff");
        showStaffButton.addActionListener(e -> showStaff());

        JButton getStaffIDsButton = new JButton("Get Staff IDs");
        getStaffIDsButton.addActionListener(e -> getStaffIDs());

        JButton viewReservationsButton = new JButton("View Reservations");
        viewReservationsButton.addActionListener(e -> loadReservations());

        buttonPanel.add(viewReservationsButton);
        buttonPanel.add(loadAllButton);
        buttonPanel.add(showStaffButton);
        buttonPanel.add(getStaffIDsButton);
    }

    private void addUser() throws DAOException {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            int roleIndex = roleComboBox.getSelectedIndex();
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            User.Role role = mapIndexToRole(roleIndex);

            User user = new User(username, password, role, email, phone);
            userDAO.add(user);
            loadAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding user.");
        }
    }

    private User.Role mapIndexToRole(int index) {
        switch (index) {
            case 0: return User.Role.ADMIN;
            case 1: return User.Role.STAFF;
            case 2: return User.Role.CUSTOMER;
            default: throw new IllegalArgumentException("Invalid role index");
        }
    }

    
    private void loadAllUsers() {
        try {
            List<User> users = userDAO.getAll();
            tableModel.setRowCount(0); // Clear existing rows
            for (User user : users) {
                tableModel.addRow(new Object[]{user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getEmail(), user.getPhone()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void addReservation() {
        // Implement logic for adding a reservation
    }

    private void loadMenu() {
        // Implement logic for loading menu
    }

    private void addMenuItem() {
        // Implement logic for adding a menu item
    }

    private void addInventoryItem() {
        // Implement logic for adding inventory
    }

    private void loadInventory() {
        // Implement logic for loading inventory
    }

    private void findUser() {
        // Implement logic for finding a user by username
    }

    private void showStaff() {
        // Implement logic for showing staff
    }

    private void getStaffIDs() {
        // Implement logic for getting staff IDs
    }

    private void loadReservations() {
        // Implement logic for loading reservations
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Initialize the database connection
                Connection conn = Database.getConnection();

                // Create DAO instances with the connection
                UserDAO userDAO = new UserDAOImplement();
                StaffTaskDAO staffTaskDAO = new StaffTaskDAOImplement();
                RestaurantTableDAO tableDAO = new RestaurantTableDAOImplement();
                MenuDAO menuDAO = new MenuDAOImplement(conn);
                InventoryDAO inventoryDAO = new InventoryDAOImplement(conn);

                // Show Login Frame
                new LoginFrame(userDAO, () -> {
                    // This runs only after successful login
                    App app = new App(userDAO, staffTaskDAO, tableDAO, menuDAO, inventoryDAO);
                    app.setVisible(true);
                }).setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred during app initialization.");
            }
        });
    }
}


