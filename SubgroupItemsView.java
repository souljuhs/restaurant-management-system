package com.fantastic.restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubgroupItemsView extends JPanel {
    private JTable itemTable;
    private DefaultTableModel itemModel;
    private JButton addItemButton;
    private JButton saveButton;
    private JLabel menuLabel;
    private JPanel subgroupButtonPanel;
    private String selectedSubgroup = null;
    private MenuDAOImplement menuDAO;
    private JButton backButton;

    private int menuId; // Store the menu ID
    private Connection connection; // Database connection

    private static final Color DARK_BACKGROUND = new Color(30, 30, 30);
    private static final Color LIGHT_TEXT = new Color(230, 230, 230);
    private static final Color INPUT_BG = new Color(45, 45, 45);
    private static final Color ACCENT_COLOR = new Color(70, 130, 180);
    private static final Color GRAY_HEADER = new Color(100, 100, 100);
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);

    public SubgroupItemsView(String menuName, int menuId, List<String> subgroups, JPanel parentPanel, Connection connection) {
        this.menuId = menuId; // Set the passed menuId
        this.connection = connection;
        this.menuDAO = new MenuDAOImplement(connection);

        setLayout(new BorderLayout());
        setBackground(DARK_BACKGROUND);

        // Top Panel with Menu Name and Save Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(DARK_BACKGROUND);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        backButton = new JButton("Back to Admin Page");
        styleButton(backButton);
        topPanel.add(backButton, BorderLayout.WEST);
        
        backButton.addActionListener(e -> {
            // Return to AdminMainPage
            Window parentWindow = SwingUtilities.windowForComponent(this); // Get the current window
            if (parentWindow != null) {
                parentWindow.dispose();  // Close the current window (SubgroupItemsView)
            }
            // Show the AdminMainPage
            SwingUtilities.invokeLater(() -> {
                UserDAO userDAO = new UserDAOImplement(); // Assume you have access to the UserDAO instance
                Runnable onSuccess = () -> {}; // Define what happens on success if needed
                AdminMainPage.showAdminPage(userDAO, onSuccess);  // Reopen the AdminMainPage
            });
        });
        
        add(backButton, BorderLayout.SOUTH);
        
        


        menuLabel = new JLabel("Menu: " + menuName);
        menuLabel.setFont(HEADER_FONT);
        menuLabel.setForeground(LIGHT_TEXT);
        topPanel.add(menuLabel, BorderLayout.WEST);

        saveButton = new JButton("Save All");
        styleButton(saveButton);
        topPanel.add(saveButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Left Panel for Subgroup Buttons
        subgroupButtonPanel = new JPanel();
        subgroupButtonPanel.setLayout(new BoxLayout(subgroupButtonPanel, BoxLayout.Y_AXIS));
        subgroupButtonPanel.setBackground(DARK_BACKGROUND);
        subgroupButtonPanel.setPreferredSize(new Dimension(180, getHeight()));
        subgroupButtonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        for (String subgroup : subgroups) {
            JButton button = new JButton(subgroup);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(160, 40));
            button.setFont(DEFAULT_FONT);
            button.setBackground(INPUT_BG);
            button.setForeground(LIGHT_TEXT);
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));
            
            button.addActionListener(e -> {
                selectedSubgroup = subgroup;
                loadSubgroupItems(subgroup);
            });

            subgroupButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            subgroupButtonPanel.add(button);
        }

        add(subgroupButtonPanel, BorderLayout.WEST);

        // Center Panel for Table & Add Item Button
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(DARK_BACKGROUND);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(DARK_BACKGROUND);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        addItemButton = new JButton("+ Add Item");
        styleButton(addItemButton);
        controlPanel.add(addItemButton);
        centerPanel.add(controlPanel, BorderLayout.NORTH);

        // Setup Item Table
        itemModel = new DefaultTableModel(new Object[]{"Item Name", "Price ($)"}, 0);
        itemTable = new JTable(itemModel);
        itemTable.setFont(DEFAULT_FONT);
        itemTable.setRowHeight(30);
        itemTable.setGridColor(DARK_BACKGROUND); // Seamless appearance
        itemTable.setBackground(INPUT_BG);
        itemTable.setForeground(LIGHT_TEXT);
        itemTable.setSelectionBackground(ACCENT_COLOR);
        itemTable.setSelectionForeground(Color.BLACK);

        JTableHeader tableHeader = itemTable.getTableHeader();
        tableHeader.setFont(HEADER_FONT);
        tableHeader.setBackground(GRAY_HEADER);
        tableHeader.setForeground(LIGHT_TEXT);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Action Listener for Add Item Button
        addItemButton.addActionListener(e -> {
            itemModel.addRow(new Object[]{"", ""});
        });

        // Action Listener for Save Button
        saveButton.addActionListener(e -> {
            if (selectedSubgroup == null) {
                JOptionPane.showMessageDialog(this, "Please select a subgroup first.");
                return;
            }

            for (int i = 0; i < itemModel.getRowCount(); i++) {
                String itemName = (String) itemModel.getValueAt(i, 0);
                String priceStr = (String) itemModel.getValueAt(i, 1);
                String description = "No description";

                double price;
                try {
                    price = Double.parseDouble(priceStr.isEmpty() ? "0" : priceStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price for item: " + itemName, "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int subgroupId = getSubgroupId(selectedSubgroup); // Fetch subgroup ID
                
                // Create the new Menu item
                Menu newItem = new Menu(itemName, description, price, selectedSubgroup, 1, menuId, subgroupId);

                try {
                    menuDAO.add(newItem); // Add item using the DAO
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving item: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            JOptionPane.showMessageDialog(this, "Items saved for subgroup: " + selectedSubgroup);
        });
    }

    // Styling method for buttons
    private void styleButton(JButton button) {
        button.setFont(DEFAULT_FONT);
        button.setBackground(INPUT_BG);
        button.setForeground(LIGHT_TEXT);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(INPUT_BG);
            }
        });
    }

    // Loads items for the selected subgroup from the database
    private void loadSubgroupItems(String subgroupName) {
        if (subgroupName == null) return;

        itemModel.setRowCount(0); // Clear current items

        String sql = "SELECT name, price FROM Menu_Items WHERE subgroup_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, getSubgroupId(subgroupName)); // Fetch ID based on subgroup name
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String itemName = rs.getString("name");
                double price = rs.getDouble("price");
                itemModel.addRow(new Object[]{itemName, price});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fetch the subgroup ID based on the name
    private int getSubgroupId(String subgroupName) {
        String sql = "SELECT id FROM Menu_Subgroups WHERE subgroup_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, subgroupName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error getting subgroup ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1; // Return -1 if no valid ID is found
    }

    // Method to get the current menu ID
    public int getMenuId() {
        return menuId; // Return the stored menu ID
    }
}