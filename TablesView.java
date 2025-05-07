package com.fantastic.restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablesView extends JPanel {
	
	    private JTable reservationsTable;
	    private JTable tablesTable;
	    private JButton editButton, assignTableButton, addReservationButton;
	    private JComboBox<String> statusDropdown;
	    private JButton backButton;


	    // UI Color Scheme (Grey-based)
	    private final Color BACKGROUND_COLOR = new Color(50, 50, 50);  // Dark grey
	    private final Color TEXT_COLOR = Color.WHITE;  // White text color
	    private final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 18);

    public TablesView() {
        setLayout(new BorderLayout(15, 15));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablesPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadReservations();
        loadTableStatuses();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("Table & Reservation Management");
        title.setFont(HEADER_FONT);
        title.setForeground(TEXT_COLOR);  

        statusDropdown = new JComboBox<>(new String[]{"All", "Checked In", "Booked", "Cancelled", "Completed"});
        statusDropdown.setBackground(Color.WHITE);
        statusDropdown.setForeground(Color.BLACK);
        statusDropdown.addActionListener(e -> loadReservations());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.add(new JLabel("Filter by Status:"));
        rightPanel.add(statusDropdown);

        panel.add(title, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        reservationsTable = new JTable();
        JScrollPane scroll1 = new JScrollPane(reservationsTable);
        scroll1.setBorder(BorderFactory.createTitledBorder("Reservations"));
        panel.add(scroll1);

        tablesTable = new JTable();
        JScrollPane scroll2 = new JScrollPane(tablesTable);
        scroll2.setBorder(BorderFactory.createTitledBorder("Tables"));
        panel.add(scroll2);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);

        editButton = styleButton("Edit Reservation");
        assignTableButton = styleButton("Assign Table");
        addReservationButton = styleButton("Add Reservation");

        editButton.addActionListener(e -> editReservation());
        assignTableButton.addActionListener(e -> assignTable());
        addReservationButton.addActionListener(e -> showAddReservationDialog());
        
        backButton = styleButton("Back");
        backButton.addActionListener(e -> openAdminMainPage());
        panel.add(backButton);


        panel.add(editButton);
        panel.add(assignTableButton);
        panel.add(addReservationButton);

        return panel;
    }
    
    private JButton styleButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 0, 0, 0));  // Transparent background
        button.setForeground(TEXT_COLOR);  // White text for buttons
        button.setBorder(BorderFactory.createEmptyBorder());  // No border
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return button;
    }

    private void loadReservations() {
        String selectedStatus = (String) statusDropdown.getSelectedItem();
        StringBuilder query = new StringBuilder("SELECT * FROM Reservations");

        if (!"All".equals(selectedStatus)) {
            query.append(" WHERE checked_in = '").append(selectedStatus.toLowerCase()).append("'");
        }

        query.append(" ORDER BY CASE checked_in ")
             .append("WHEN 'checked_in' THEN 1 ")
             .append("WHEN 'booked' THEN 2 ELSE 3 END");

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {

            List<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                data.add(new Object[]{
                        rs.getInt("reservation_id"), rs.getString("customer_name"), rs.getString("email"),
                        rs.getString("phone"), rs.getInt("total_people"), rs.getString("status"),
                        rs.getString("checked_in"), rs.getInt("table_id"), rs.getString("date_time")
                });
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{
                    "ID", "Customer", "Email", "Phone", "People", "Status", "Checked In", "Table ID", "Reservation Time"
            }, 0);

            data.forEach(model::addRow);
            reservationsTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTableStatuses() {
        String query = "SELECT table_id, capacity, status FROM Tables";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                data.add(new Object[]{rs.getInt("table_id"), rs.getInt("capacity"), rs.getString("status")});
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"Table ID", "Capacity", "Status"}, 0);
            data.forEach(model::addRow);
            tablesTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editReservation() {
        int row = reservationsTable.getSelectedRow();
        if (row < 0) return;

        int id = (int) reservationsTable.getValueAt(row, 0);
        String currentName = (String) reservationsTable.getValueAt(row, 1);
        String currentStatus = (String) reservationsTable.getValueAt(row, 5);
        String currentCheckIn = (String) reservationsTable.getValueAt(row, 6);

        String newName = JOptionPane.showInputDialog("Enter new name:", currentName);
        String newStatus = JOptionPane.showInputDialog("Enter new status:", currentStatus);
        String newCheckIn = JOptionPane.showInputDialog("Enter checked-in status:", currentCheckIn);

        updateReservationInDatabase(id, newName, newStatus, newCheckIn);
    }

    private void updateReservationInDatabase(int id, String name, String status, String checkedIn) {
        String updateReservation = "UPDATE Reservations SET customer_name = ?, status = ?, checked_in = ? WHERE reservation_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateReservation)) {

            stmt.setString(1, name);
            stmt.setString(2, status);
            stmt.setString(3, checkedIn);
            stmt.setInt(4, id);
            stmt.executeUpdate();

            if ("completed".equalsIgnoreCase(status)) {
                int tableId = getTableIdForReservation(id);
                if (tableId != -1) {
                    freeTableForReservation(conn, tableId);
                }
            }

            loadReservations();
            loadTableStatuses();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getTableIdForReservation(int reservationId) {
        String query = "SELECT table_id FROM Reservations WHERE reservation_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("table_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void freeTableForReservation(Connection conn, int reservationId) throws SQLException {
        String getTableId = "SELECT table_id FROM Reservations WHERE reservation_id = ?";
        String freeTable = "UPDATE Tables SET status = 'free' WHERE table_id = ?";

        try (PreparedStatement stmt1 = conn.prepareStatement(getTableId);
             PreparedStatement stmt2 = conn.prepareStatement(freeTable)) {

            stmt1.setInt(1, reservationId);
            ResultSet rs = stmt1.executeQuery();

            if (rs.next()) {
                int tableId = rs.getInt("table_id");
                if (tableId > 0) {
                    stmt2.setInt(1, tableId);
                    stmt2.executeUpdate();
                }
            }
        }
    }

    private void assignTable() {
        int row = reservationsTable.getSelectedRow();
        if (row < 0) return;

        int totalPeople = (int) reservationsTable.getValueAt(row, 4);
        String checkIn = (String) reservationsTable.getValueAt(row, 6);

        if (!"checked_in".equals(checkIn)) return;

        int tableId = findAvailableTable(totalPeople);
        if (tableId == -1) {
            JOptionPane.showMessageDialog(this, "No suitable table available.");
            return;
        }

        updateTableAssignment(row, tableId);
    }

    private int findAvailableTable(int totalPeople) {
        String query = "SELECT table_id FROM Tables WHERE capacity >= ? AND status = 'free' LIMIT 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, totalPeople);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("table_id");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void updateTableAssignment(int row, int tableId) {
        int reservationId = (int) reservationsTable.getValueAt(row, 0);

        String updateReservation = "UPDATE Reservations SET table_id = ? WHERE reservation_id = ?";
        String updateTableStatus = "UPDATE Tables SET status = 'occupied' WHERE table_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(updateReservation);
             PreparedStatement stmt2 = conn.prepareStatement(updateTableStatus)) {

            stmt1.setInt(1, tableId);
            stmt1.setInt(2, reservationId);
            stmt1.executeUpdate();

            stmt2.setInt(1, tableId);
            stmt2.executeUpdate();

            loadReservations();
            loadTableStatuses();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAddReservationDialog() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField peopleField = new JTextField();
        JTextField dateTimeField = new JTextField(); // Format: YYYY-MM-DD HH:MM:SS

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Customer Name:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);
        panel.add(new JLabel("Total People:")); panel.add(peopleField);
        panel.add(new JLabel("Reservation Time (YYYY-MM-DD HH:MM:SS):")); panel.add(dateTimeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Reservation",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addReservationToDatabase(
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    Integer.parseInt(peopleField.getText()),
                    dateTimeField.getText()
            );
        }
    }

    private void addReservationToDatabase(String name, String email, String phone, int people, String dateTime) {
        String query = "INSERT INTO Reservations (customer_name, email, phone, total_people, status, checked_in, date_time) " +
                       "VALUES (?, ?, ?, ?, 'pending', 'booked', ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setInt(4, people);
            stmt.setString(5, dateTime);
            stmt.executeUpdate();

            loadReservations();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void openAdminMainPage() {
        // Close the current frame (TablesView)
        Window window = SwingUtilities.windowForComponent(this);
        if (window != null) {
            window.dispose();
        }

        // Open the AdminMainPage
        UserDAO userManager = new UserDAOImplement(); // Make sure to pass the correct userManager
        Runnable onSuccess = () -> System.out.println("Returned to Admin Main Page");

        AdminMainPage adminMainPage = new AdminMainPage(userManager, onSuccess);
        adminMainPage.setVisible(true);
    }
    

}

