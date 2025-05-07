package com.fantastic.restaurant;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuView extends JFrame {

    private UserDAO userManager;  // Declare userManager
    private Runnable onSuccess;   // Declare onSuccess

    // Constructor takes userManager and onSuccess as arguments
    public MenuView(UserDAO userManager, Runnable onSuccess) {
        this.userManager = userManager;
        this.onSuccess = onSuccess;

        setTitle("Menu View");
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

        // Top panel for header + back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkBackground);

        JLabel headerLabel = new JLabel("Menus", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        headerLabel.setForeground(darkText);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 0));
        topPanel.add(headerLabel, BorderLayout.WEST);

        // Back button to go to AdminMainPage
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        backButton.setBackground(accentColor);
        backButton.setForeground(darkText);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(e -> {
            dispose(); // Close current menu view
            AdminMainPage.showAdminPage(userManager, onSuccess); // Go back to AdminMainPage
        });

        JPanel backPanel = new JPanel();
        backPanel.setBackground(darkBackground);
        backPanel.add(backButton);
        topPanel.add(backPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Button grid panel for menu actions
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(darkBackground);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        addButtonToPanel(buttonPanel, "Create New Menu", e -> openCreateMenu(), darkText, accentColor);
        addButtonToPanel(buttonPanel, "View Menus", e -> openViewMenus(), darkText, accentColor);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

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

    // Dummy view methods for actions
    private void openCreateMenu() {
    	 dispose(); // Close AdminMainPage
    	 new CreateMenuView().setVisible(true);
    }

    private void openViewMenus() {
        JOptionPane.showMessageDialog(this, "View Existing Menus!");
    }

    public static void showMenuView(UserDAO userDAO, Runnable onSuccess) {
        SwingUtilities.invokeLater(() -> {
            MenuView menuView = new MenuView(userDAO, onSuccess);
            menuView.setVisible(true);
        });
    }
}
