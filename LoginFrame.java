package com.fantastic.restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private final JPasswordField inputField = new JPasswordField(10);
    private final UserDAO userDAO;
    private final Runnable onSuccess;
    private boolean showingPlaceholder = true;
    private boolean darkMode = true;

    private final String placeholderText = "Enter your passcode";
    private final Color placeholderTextColor = Color.RED;  // Color for the placeholder

    public LoginFrame(UserDAO userDAO, Runnable onSuccess) {
        this.userDAO = userDAO;
        this.onSuccess = onSuccess;
        setTitle("Staff Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rebuildUI();
    }

    private void rebuildUI() {
        getContentPane().removeAll();
        initUI();
        revalidate();
        repaint();
    }

    private void initUI() {
        Color darkBackground = new Color(30, 30, 30);
        Color lightBackground = new Color(255, 255, 255);
        Color darkText = new Color(200, 200, 200);
        Color lightText = new Color(0, 0, 0);
        Color buttonColor = new Color(50, 50, 50);
        Color lightButtonColor = new Color(240, 240, 240);
        Color accentColor = new Color(70, 130, 180);

        getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(darkMode ? darkBackground : lightBackground);

        // Left (Logo)
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/resources/logo.png"));
            Image scaledLogo = logo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledLogo));
        } catch (Exception e) {
            logoLabel.setText("Company Logo");
            logoLabel.setForeground(darkMode ? lightText : darkText);
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        }
        mainPanel.add(logoLabel);

        // Right (Login Panel)
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(darkMode ? darkBackground : lightBackground);

        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setForeground(darkMode ? lightText : darkText);
        inputField.setBackground(darkMode ? buttonColor : lightButtonColor);
        inputField.setText(placeholderText);
        inputField.setForeground(placeholderTextColor);  // Set the initial color to red for placeholder
        inputField.setEchoChar((char) 0);
        inputField.setBorder(BorderFactory.createLineBorder(darkMode ? lightText : darkText, 1));

        inputField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder) {
                    inputField.setText("");
                    inputField.setForeground(Color.WHITE);  // Change text color to white when user starts typing
                    inputField.setEchoChar('●');
                    showingPlaceholder = false;
                }
            }

            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText(placeholderText);
                    inputField.setForeground(placeholderTextColor);  // Set text color back to red when focus is lost
                    inputField.setEchoChar((char) 0);  // Remove the echo character
                    showingPlaceholder = true;
                }
            }
        });
        
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (showingPlaceholder) {
                    inputField.setText("");
                    inputField.setForeground(Color.WHITE);  // Change text color to white when user starts typing
                    inputField.setEchoChar('●');
                    showingPlaceholder = false;
                }
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 20, 60));
        inputPanel.setBackground(darkMode ? darkBackground : lightBackground);
        inputPanel.add(inputField, BorderLayout.CENTER);
        rightPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        buttonPanel.setBackground(darkMode ? darkBackground : lightBackground);

        for (int i = 1; i <= 9; i++) {
            final String digit = String.valueOf(i);
            addStyledButton(buttonPanel, digit, e -> {
                if (showingPlaceholder) {
                    inputField.setText("");
                    inputField.setForeground(Color.WHITE);
                    inputField.setEchoChar('●');
                    showingPlaceholder = false;
                }
                inputField.setText(new String(inputField.getPassword()) + digit);
            }, darkMode ? lightText : darkText, darkMode ? buttonColor : lightButtonColor);
        }

        addStyledButton(buttonPanel, "X", e -> resetInput(), darkMode ? lightText : darkText, darkMode ? buttonColor : lightButtonColor);
        addStyledButton(buttonPanel, "0", e -> inputField.setText(new String(inputField.getPassword()) + "0"), darkMode ? lightText : darkText, darkMode ? buttonColor : lightButtonColor);
        addStyledButton(buttonPanel, "←", e -> backspace(), darkMode ? lightText : darkText, darkMode ? buttonColor : lightButtonColor);
        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        JButton goButton = new JButton("Go");
        goButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        goButton.setBackground(accentColor);
        goButton.setForeground(darkMode ? Color.WHITE : Color.BLACK); // Adjust text color based on mode
        goButton.setFocusPainted(false);
        goButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        goButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        goButton.addActionListener(e -> validateLogin());

        JPanel goPanel = new JPanel(new BorderLayout());
        goPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        goPanel.setBackground(darkMode ? darkBackground : lightBackground);
        goPanel.add(goButton, BorderLayout.CENTER);

        rightPanel.add(goPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // 🔽 Add sun/moon icon toggle button at bottom left
        ImageIcon sunIcon = null;
        ImageIcon moonIcon = null;

        try {
            Image sunImg = new ImageIcon(getClass().getResource("/resources/sun.png")).getImage()
                .getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            sunIcon = new ImageIcon(sunImg);

            Image moonImg = new ImageIcon(getClass().getResource("/resources/moon.png")).getImage()
                .getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            moonIcon = new ImageIcon(moonImg);
        } catch (Exception e) {
            System.err.println("Icon(s) not found.");
        }

        JButton toggleButton = new JButton();
        toggleButton.setIcon(darkMode ? sunIcon : moonIcon);
        toggleButton.setPreferredSize(new Dimension(40, 40));
        toggleButton.setContentAreaFilled(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleButton.setToolTipText("Toggle light/dark mode");
        toggleButton.addActionListener(e -> toggleMode());

        // Create a JLabel for version text
        JLabel versionLabel = new JLabel("Program V 0.0.01");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));  // Set font size to small
        versionLabel.setForeground(darkMode ? Color.WHITE : darkText);  // Set text color to white in dark mode, darkText in light mode

        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeftPanel.setBackground(darkMode ? darkBackground : lightBackground);
        bottomLeftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomLeftPanel.add(toggleButton);
        bottomLeftPanel.add(versionLabel);  // Add the version label next to the button

        getContentPane().add(bottomLeftPanel, BorderLayout.SOUTH);
    }

    private void toggleMode() {
        darkMode = !darkMode;
        rebuildUI();
    }

    private void addStyledButton(JPanel panel, String label, ActionListener action, Color textColor, Color bgColor) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(textColor);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(80, 80, 80));
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        panel.add(btn);
    }

    private void backspace() {
        if (!showingPlaceholder) {
            String text = new String(inputField.getPassword());
            if (!text.isEmpty()) {
                inputField.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    private void resetInput() {
        inputField.setText(placeholderText);
        inputField.setForeground(placeholderTextColor);  // Set text color back to red for placeholder
        inputField.setEchoChar((char) 0);
        showingPlaceholder = true;
    }

    private void validateLogin() {
        try {
            if (showingPlaceholder) {
                return;
            }
            int id = Integer.parseInt(new String(inputField.getPassword()));
            User user = userDAO.getUserById(id);
            
            if (user != null) {
                if (user.getRole().toString().equals("ADMIN")) {  // Check if the user is an admin
                    dispose();  // Close the login frame
                    // Pass the necessary arguments to showAdminPage
                    AdminMainPage.showAdminPage(userDAO, () -> {
                        // onSuccess logic goes here
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "You are not an admin.");
                }
            } else {
                showInvalidIdMessage();  // Show error message if user doesn't exist
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.");
        }
    }


    
    private void showInvalidIdMessage() {
        inputField.setText("Invalid Passcode");
        inputField.setForeground(placeholderTextColor);  // Red color
        inputField.setEchoChar((char) 0);  // Show text instead of masking
        showingPlaceholder = true;
    }

}



