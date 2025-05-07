package com.fantastic.restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewMenuView extends JPanel {

    private final Connection connection;
    private final UserDAO userDAO;
    private final JPanel menuListPanel;
    private final JPanel contentPanel; // Panel to hold either the menu list or subgroup details

    public ViewMenuView(Connection connection, UserDAO userDAO) {
        this.connection = connection;
        this.userDAO = userDAO;

        setLayout(new BorderLayout());

        // Create the header panel with "View Menus" and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 30, 30));

        JLabel headerLabel = new JLabel("View Menus", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Back button at top-right
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        backButton.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder()); // Remove the border
        backButton.addActionListener(e -> {
            Window parentWindow = SwingUtilities.windowForComponent(ViewMenuView.this);
            if (parentWindow != null) parentWindow.dispose();
            AdminMainPage.showAdminPage(userDAO, () -> {});
        });
        headerPanel.add(backButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Menu list panel setup
        menuListPanel = new JPanel();
        menuListPanel.setLayout(new BoxLayout(menuListPanel, BoxLayout.Y_AXIS));
        menuListPanel.setBackground(new Color(30, 30, 30)); // Dark background

        // Content panel to display either menus or submenu details
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Scrollable panel for the menu buttons
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(menuListPanel, BorderLayout.NORTH); 
        // Populate menu list
        fetchAndDisplayMenus();
    }

    private void fetchAndDisplayMenus() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT * FROM menus";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<String> menus = new ArrayList<>();
            while (resultSet.next()) {
                menus.add(resultSet.getString("name"));
            }

            // Clear previous menu buttons
            menuListPanel.removeAll();

            if (menus.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No menus found in the database.", "Empty Data", JOptionPane.WARNING_MESSAGE);
            }

            for (String menuName : menus) {
                JButton menuButton = new JButton(menuName);
                menuButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
                menuButton.setForeground(Color.WHITE);                 // White text
                menuButton.setContentAreaFilled(false);                // No background fill
                menuButton.setOpaque(false);                           // Fully transparent
                menuButton.setBorder(null);                            // No border
                menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuButton.setFocusPainted(false);
                menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);    // Align buttons to the left
                menuButton.addActionListener(e -> {
                    System.out.println("Menu button clicked: " + menuName); // Debugging
                    showMenuDetails(menuName);
                });
                menuListPanel.add(Box.createVerticalStrut(10));        // Spacing between buttons
                menuListPanel.add(menuButton);
            }

            menuListPanel.revalidate();
            menuListPanel.repaint();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching menus: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMenuDetails(String menuName) {
        System.out.println("Fetching details for menu: " + menuName); // Debugging

        // Clear the existing content
        contentPanel.removeAll();

        String query = "SELECT ms.subgroup_name, mi.name AS item_name, mi.price " +
                "FROM menu_subgroups ms " +
                "JOIN Menu_Items mi ON ms.id = mi.subgroup_id " +
                "JOIN menus m ON m.id = ms.menu_id " +
                "WHERE m.name = ? " +
                "ORDER BY ms.subgroup_name, mi.name";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, menuName);
            ResultSet resultSet = statement.executeQuery();

            JPanel subgroupsPanel = new JPanel();
            subgroupsPanel.setLayout(new BoxLayout(subgroupsPanel, BoxLayout.Y_AXIS));
            subgroupsPanel.setBackground(new Color(30, 30, 30));

            String lastSubgroup = "";
            JPanel subgroupPanel = null;
            while (resultSet.next()) {
                String subgroupName = resultSet.getString("subgroup_name");
                String itemName = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");

                // If we're in a new subgroup, create a new panel for it
                if (!subgroupName.equals(lastSubgroup)) {
                    lastSubgroup = subgroupName;
                    if (subgroupPanel != null) {
                        subgroupsPanel.add(subgroupPanel);
                    }
                    subgroupPanel = new JPanel();
                    subgroupPanel.setLayout(new BoxLayout(subgroupPanel, BoxLayout.Y_AXIS));
                    subgroupPanel.setBorder(BorderFactory.createTitledBorder(subgroupName));
                }

                // Add item to the current subgroup
                JButton itemButton = new JButton(itemName + " - $" + price);
                itemButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                itemButton.setForeground(Color.WHITE);
                itemButton.setContentAreaFilled(false);
                itemButton.setOpaque(false);
                itemButton.setBorder(null);
                itemButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                itemButton.setFocusPainted(false);
                subgroupPanel.add(itemButton);
            }

            if (subgroupPanel != null) {
                subgroupsPanel.add(subgroupPanel);
            }

            contentPanel.add(subgroupsPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching submenu details: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showViewMenuView(Connection connection, UserDAO userDAO) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("View Menus");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setContentPane(new ViewMenuView(connection, userDAO));
            frame.setVisible(true);
        });
    }
}


