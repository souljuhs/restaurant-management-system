package com.fantastic.restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class UserSetupView extends JPanel {

    public UserSetupView(UserDAO userDAO) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Add New User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(titleLabel, gbc);

        String[] labels = {"Username:", "Password:", "Role:", "Email:", "Phone:"};
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        //This line below specifically controls the outputs of the drop-down menu.
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"ADMIN", "STAFF", "CUSTOMER"});
        
        JTextField emailField = new JTextField(15);
        JTextField phoneField = new JTextField(15);

        JComponent[] fields = {usernameField, passwordField, roleBox, emailField, phoneField};

        gbc.gridwidth = 1;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            add(fields[i], gbc);
        }

        JButton addButton = new JButton("Add User");
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        add(addButton, gbc);

        // Add Button Listener with DAOException Handling
        addButton.addActionListener((ActionEvent e) -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String roleStr = (String) roleBox.getSelectedItem();
                String email = emailField.getText();
                String phone = phoneField.getText();

                User.Role role = User.Role.valueOf(roleStr);
                User user = new User(username, password, role, email, phone);
                userDAO.add(user); // May throw DAOException

                JOptionPane.showMessageDialog(this, "User added successfully!");
                usernameField.setText("");
                passwordField.setText("");
                emailField.setText("");
                phoneField.setText("");

            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Validation failed: " + ex.getMessage(), "Input Error", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
