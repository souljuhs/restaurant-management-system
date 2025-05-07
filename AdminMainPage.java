package com.fantastic.restaurant;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

public class AdminMainPage extends JFrame {

    private UserDAO userManager;  // Declare userManager
    private Runnable onSuccess;   // Declare onSuccess

    // Constructor takes userManager and onSuccess as arguments
    public AdminMainPage(UserDAO userManager, Runnable onSuccess) {
        this.userManager = userManager;
        this.onSuccess = onSuccess;

        setTitle("Admin Setup");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        // Colors
        Color darkBackground = new Color(30, 30, 30);
        Color lightBackground = new Color(255, 255, 255);
        Color darkText = new Color(200, 200, 200);
        Color lightText = new Color(0, 0, 0);
        Color accentColor = new Color(70, 130, 180);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(darkBackground);

        // Top panel for header + switch user
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkBackground);

        JLabel headerLabel = new JLabel("Setup", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        headerLabel.setForeground(darkText);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 0));
        topPanel.add(headerLabel, BorderLayout.WEST);

        // Switch user button
        JButton switchUserButton = new JButton("Switch User");
        switchUserButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        switchUserButton.setBackground(accentColor);
        switchUserButton.setForeground(darkText);
        switchUserButton.setFocusPainted(false);
        switchUserButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchUserButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        switchUserButton.addActionListener(e -> {
            dispose(); // Close admin page
            new LoginFrame(userManager, onSuccess).setVisible(true); // Open login page
        });

        JPanel switchPanel = new JPanel();
        switchPanel.setBackground(darkBackground);
        switchPanel.add(switchUserButton);
        topPanel.add(switchPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Button grid panel for views
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        buttonPanel.setBackground(darkBackground);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        addButtonToPanel(buttonPanel, "Menu", e -> openMenuView(), darkText, accentColor);
        addButtonToPanel(buttonPanel, "Tables", e -> openTablesView(), darkText, accentColor);
        addButtonToPanel(buttonPanel, "Labor", e -> openLaborView(), darkText, accentColor);
        addButtonToPanel(buttonPanel, "Inventory", e -> openInventoryView(), darkText, accentColor);
        addButtonToPanel(buttonPanel, "User Setup", e -> openUserSetupView(), darkText, accentColor);
        addButtonToPanel(buttonPanel, "Other Settings", e -> openOtherSettingsView(), darkText, accentColor);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Support section header
        JPanel supportPanel = new JPanel(new BorderLayout());
        supportPanel.setBackground(darkBackground);

        JLabel supportHeaderLabel = new JLabel("Support", SwingConstants.LEFT);
        supportHeaderLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        supportHeaderLabel.setForeground(darkText);
        supportHeaderLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 0));
        supportPanel.add(supportHeaderLabel, BorderLayout.NORTH);

        // Support buttons panel
        JPanel supportButtonPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        supportButtonPanel.setBackground(darkBackground);

        addButtonToPanel(supportButtonPanel, "Support Site", e -> openSupportSite(), darkText, accentColor);
        addButtonToPanel(supportButtonPanel, "Status Page", e -> openStatusPage(), darkText, accentColor);

        supportPanel.add(supportButtonPanel, BorderLayout.CENTER);

        mainPanel.add(supportPanel, BorderLayout.SOUTH);

        // Finalize UI setup
        getContentPane().add(mainPanel);
        pack();
    }

    private void addButtonToPanel(JPanel panel, String label, ActionListener action, Color textColor, Color bgColor) {
        JButton button = new JButton(label);
        button.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Made it a bit larger
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Set a fixed size for all buttons
        button.setPreferredSize(new Dimension(200, 60)); // Uniform size for all buttons

        button.addActionListener(action);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        panel.add(button);
    }

    // Dummy view methods
    private void openMenuView() {
        dispose(); // Close AdminMainPage

        try {
            Connection conn = Database.getConnection();
            MenuView.showMenuView(conn, userManager, onSuccess);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to connect to the database.\nPlease try again.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            // Optionally reopen AdminMainPage or exit
            System.exit(1);
        }
    }


    private void openTablesView() {
        dispose(); // Close current window

        try {
            Connection conn = Database.getConnection();
            Runnable onSuccess = () -> System.out.println("Returned from TablesView");

            JFrame frame = new JFrame("Tables Management");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new TablesView());
            frame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to connect to the database.\nPlease try again.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }


    private void openLaborView() {
        JOptionPane.showMessageDialog(this, "Labor View Opened!");
    }

    private void openInventoryView() {
        dispose(); // Close AdminMainPage

        try {
            Connection conn = Database.getConnection(); // Get database connection
            InventoryDAO inventoryDAO = new InventoryDAOImplement(conn); // Create InventoryDAO instance with connection

            // Create and configure the frame
            JFrame frame = new JFrame("Inventory Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new InventoryView(inventoryDAO, userManager, onSuccess)); // Pass InventoryDAO to the view
            frame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to connect to the database.\nPlease try again.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }




    private void openUserSetupView() {
        // Implemented User Setup form
        JFrame frame = new JFrame("User Setup");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new UserSetupView(userManager));
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


    private void openOtherSettingsView() {
        JOptionPane.showMessageDialog(this, "Other Settings View Opened!");
    }

    // Support page actions
    private void openSupportSite() {
        try {
            Desktop.getDesktop().browse(new java.net.URI("https://github.com/souljuhs/restaurant-management-system"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to open support site.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openStatusPage() {
        JOptionPane.showMessageDialog(this, "Opening Status Page...");
    }

    public static void showAdminPage(UserDAO userDAO, Runnable onSuccess) {
        SwingUtilities.invokeLater(() -> {
            AdminMainPage adminPage = new AdminMainPage(userDAO, onSuccess);
            adminPage.setVisible(true);
        });
    }
}

