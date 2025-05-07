package com.fantastic.restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class InventoryView extends JPanel {

    private JTextField nameTextField;
    private JTextField quantityTextField;
    private JTextField unitTextField;
    private JTextField thresholdTextField;
    private InventoryDAO inventoryDAO;

    public InventoryView(InventoryDAO inventoryDAO, UserDAO userDAO, Runnable onSuccess) {
        this.inventoryDAO = inventoryDAO;

        // Theme
        Color darkBackground = new Color(30, 30, 30);
        Color lightText = new Color(230, 230, 230);
        Color inputBg = new Color(45, 45, 45);
        Color accentColor = new Color(70, 130, 180);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        setLayout(new BorderLayout());
        setBackground(darkBackground);

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkBackground);
        topPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JButton backButton = new JButton("← Back");
        styleButton(backButton, accentColor, lightText);
        backButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
            new AdminMainPage(userDAO, onSuccess).setVisible(true);
        });

        JLabel header = new JLabel("Manage Inventory", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(lightText);

        JPanel rightEmpty = new JPanel();
        rightEmpty.setBackground(darkBackground);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(header, BorderLayout.CENTER);
        topPanel.add(rightEmpty, BorderLayout.EAST);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(darkBackground);
        formPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameTextField = makeTextField(inputBg, lightText);
        quantityTextField = makeTextField(inputBg, lightText);
        unitTextField = makeTextField(inputBg, lightText);
        thresholdTextField = makeTextField(inputBg, lightText);

        String[] labels = {"Item Name:", "Quantity:", "Unit:", "Threshold:"};
        JTextField[] fields = {nameTextField, quantityTextField, unitTextField, thresholdTextField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(makeLabel(labels[i], labelFont, lightText), gbc);
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(darkBackground);

        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");

        styleButton(addButton, accentColor, lightText);
        styleButton(updateButton, accentColor, lightText);
        styleButton(deleteButton, accentColor, lightText);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add listeners
        addButton.addActionListener((ActionEvent e) -> {
            try {
                addInventoryItem();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        updateButton.addActionListener((ActionEvent e) -> updateInventoryItem());
        deleteButton.addActionListener((ActionEvent e) -> deleteInventoryItem());

        // Scroll wrapper in case content grows
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel makeLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JTextField makeTextField(Color bg, Color fg) {
        JTextField tf = new JTextField(20);
        tf.setBackground(bg);
        tf.setForeground(fg);
        tf.setCaretColor(fg);
        tf.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        return tf;
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bg.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });
    }

    private void addInventoryItem() throws SQLException {
        String name = nameTextField.getText();
        String unit = unitTextField.getText();
        String thresholdStr = thresholdTextField.getText();
        String quantityStr = quantityTextField.getText();

        try {
            int quantity = Integer.parseInt(quantityStr);
            int threshold = Integer.parseInt(thresholdStr);

            Inventory newItem = new Inventory(name, quantity, unit, threshold);
            inventoryDAO.add(newItem);

            JOptionPane.showMessageDialog(this, "Item added successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity and threshold.");
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error adding item: " + e.getMessage());
        }
    }

    private void updateInventoryItem() {
        String name = nameTextField.getText();
        String quantityStr = quantityTextField.getText();

        try {
            int quantity = Integer.parseInt(quantityStr);
            inventoryDAO.updateQuantityByName(name, quantity);

            JOptionPane.showMessageDialog(this, "Item updated successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.");
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error updating item: " + e.getMessage());
        }
    }

    private void deleteInventoryItem() {
        String name = nameTextField.getText();

        try {
            inventoryDAO.deleteInventoryByName(name);

            JOptionPane.showMessageDialog(this, "Item deleted successfully!");

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting item: " + e.getMessage());
        }
    }
}


