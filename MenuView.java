package com.fantastic.restaurant;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

import javax.swing.*;

/**
 * MenuView class represents the main view for managing menus in the restaurant system.
 * This view allows users to create new menus and view existing menus.
 * It is part of the admin interface and manages actions related to menu creation and viewing.
 */
public class MenuView extends JFrame {
    private Connection connection;
    private UserDAO userDAO;

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDAO userManager;  // The UserDAO instance for handling user data.
    private Runnable onSuccess;   // The callback to be executed upon successful operations.

    /**
     * Constructor for the MenuView.
     *
     * @param userManager An instance of UserDAO used for user management.
     * @param onSuccess A Runnable to be executed on success (e.g., after performing an action).
     */
    public MenuView(Connection connection, UserDAO userManager, Runnable onSuccess) {
        this.connection = connection;
        this.userManager = userManager;
        this.userDAO = userManager;
        this.onSuccess = onSuccess;
        setTitle("Menu View");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }


    /**
     * Initializes the user interface components of the MenuView.
     * This method sets up the layout, colors, panels, and buttons.
     */
    private void initUI() {
        // Define common colors for the UI.
        final Color DARK_BACKGROUND = new Color(30, 30, 30);
        final Color DARK_TEXT = new Color(200, 200, 200);
        final Color ACCENT_COLOR = new Color(70, 130, 180);

        // Create the main panel and add it to the frame.
        JPanel mainPanel = createMainPanel(DARK_BACKGROUND);

        // Create and add the top panel (header and back button).
        JPanel topPanel = createTopPanel(DARK_BACKGROUND, DARK_TEXT, ACCENT_COLOR);

        // Create and add the button panel (buttons for menu actions).
        JPanel buttonPanel = createButtonPanel(DARK_BACKGROUND, DARK_TEXT, ACCENT_COLOR);

        // Add the top panel and button panel to the main panel.
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Finalize the UI setup by adding the main panel to the frame.
        getContentPane().add(mainPanel);
        pack();
    }

    /**
     * Creates and returns the main panel with a BorderLayout.
     * 
     * @param backgroundColor The background color for the panel.
     * @return The created main panel.
     */
    private JPanel createMainPanel(Color backgroundColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        return panel;
    }

    /**
     * Creates and returns the top panel containing the header and back button.
     * 
     * @param backgroundColor The background color for the top panel.
     * @param textColor The color for text (e.g., header).
     * @param accentColor The accent color used for buttons.
     * @return The created top panel.
     */
    private JPanel createTopPanel(Color backgroundColor, Color textColor, Color accentColor) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);

        // Create the header label for the menu view.
        JLabel headerLabel = new JLabel("Menus", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        headerLabel.setForeground(textColor);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 0));
        topPanel.add(headerLabel, BorderLayout.WEST);

        // Create the back button to navigate to the AdminMainPage.
        JButton backButton = createButton("Back", 16, accentColor, textColor, e -> goBack());

        // Panel for the back button
        JPanel backPanel = new JPanel();
        backPanel.setBackground(backgroundColor);
        backPanel.add(backButton);
        topPanel.add(backPanel, BorderLayout.EAST);

        return topPanel;
    }

    /**
     * Creates and returns the button panel that contains the action buttons.
     * 
     * @param backgroundColor The background color for the button panel.
     * @param textColor The color for button text.
     * @param accentColor The accent color used for buttons.
     * @return The created button panel.
     */
    private JPanel createButtonPanel(Color backgroundColor, Color textColor, Color accentColor) {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        // Add buttons for actions.
        addButtonToPanel(buttonPanel, "Create New Menu", e -> openCreateMenu(), textColor, accentColor);
        addButtonToPanel(buttonPanel, "View Menus", e -> openViewMenus(), textColor, accentColor);

        return buttonPanel;
    }

    /**
     * Creates a button with the specified label, font size, background color, text color, and action listener.
     * 
     * @param label The text to be displayed on the button.
     * @param fontSize The font size for the button text.
     * @param bgColor The background color of the button.
     * @param textColor The color of the button text.
     * @param action The action listener for button click events.
     * @return The created button.
     */
    private JButton createButton(String label, int fontSize, Color bgColor, Color textColor, ActionListener action) {
        JButton button = new JButton(label);
        button.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }

    /**
     * Adds a button to the specified panel.
     * 
     * @param panel The panel to which the button will be added.
     * @param label The text label for the button.
     * @param action The action listener for the button click event.
     * @param textColor The color of the button text.
     * @param bgColor The background color of the button.
     */
    private void addButtonToPanel(JPanel panel, String label, ActionListener action, Color textColor, Color bgColor) {
        JButton button = createButton(label, 24, bgColor, textColor, action);
        button.setPreferredSize(new Dimension(200, 60)); // Set a uniform size for all buttons.

        // Add mouse listeners to change the button's background color on hover.
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        // Add the button to the panel.
        panel.add(button);
    }

    /**
     * Handles the action for the "Back" button, navigating back to the AdminMainPage.
     */
    private void goBack() {
        dispose(); // Close current menu view
        AdminMainPage.showAdminPage(userManager, onSuccess); // Go back to AdminMainPage
    }

    /**
     * Opens the CreateMenuView for creating a new menu.
     * This action disposes of the current view and shows the CreateMenuView.
     */
    private void openCreateMenu() {
        dispose(); // Close current view
        new CreateMenuView(userDAO, onSuccess).setVisible(true);
    }

    /**
     * Opens the ViewMenuView to view the list of existing menus.
     * This action disposes of the current view and shows the ViewMenuView.
     */
    private void openViewMenus() {
        dispose(); // Close current view
        ViewMenuView.showViewMenuView(connection, userDAO);
    }

    /**
     * Displays the MenuView on the screen.
     * This method is used to show the MenuView and initialize it with necessary data.
     * 
     * @param userDAO An instance of UserDAO used for user management.
     * @param onSuccess A Runnable to be executed on success (e.g., after performing an action).
     */
    public static void showMenuView(Connection connection, UserDAO userDAO, Runnable onSuccess) {
        SwingUtilities.invokeLater(() -> {
            MenuView menuView = new MenuView(connection, userDAO, onSuccess);
            menuView.setVisible(true);
        });
    }
}

