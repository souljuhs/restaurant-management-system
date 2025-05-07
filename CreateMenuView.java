package com.fantastic.restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CreateMenuView extends JFrame {

    private JTextField nameField;
    private JTextField posNameField;
    private JTextField subgroupField;
    private DefaultTableModel subgroupModel;
    private List<String> subgroups = new ArrayList<>();
    private JButton saveButton;

    public CreateMenuView() {
        setTitle("Create New Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        Color darkBackground = new Color(30, 30, 30);
        Color lightText = new Color(230, 230, 230);
        Color inputBg = new Color(45, 45, 45);
        Color accentColor = new Color(70, 130, 180);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(darkBackground);

        // === Top Panel ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkBackground);
        topPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel header = new JLabel("Create Menu");
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(lightText);

        saveButton = new JButton("Save");
        styleButton(saveButton, accentColor, lightText);
        saveButton.addActionListener(e -> saveMenuToDatabase());

        topPanel.add(header, BorderLayout.WEST);
        topPanel.add(saveButton, BorderLayout.EAST);

        // === Center Panel ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(darkBackground);
        formPanel.setBorder(new EmptyBorder(10, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Section Title
        JLabel namingLabel = new JLabel("Naming");
        namingLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        namingLabel.setForeground(lightText);
        gbc.gridwidth = 2;
        formPanel.add(namingLabel, gbc);

        // Divider
        gbc.gridy++;
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(100, 100, 100));
        formPanel.add(separator, gbc);

        gbc.gridwidth = 1;

        // Name
        gbc.gridy++;
        formPanel.add(makeLabel("Name:", labelFont, lightText), gbc);
        nameField = makeTextField(inputBg, lightText);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // POS Name
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(makeLabel("POS Name:", labelFont, lightText), gbc);
        posNameField = makeTextField(inputBg, lightText);
        gbc.gridx = 1;
        formPanel.add(posNameField, gbc);

        // Groups
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel groupsLabel = new JLabel("Groups");
        groupsLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        groupsLabel.setForeground(lightText);
        gbc.gridwidth = 2;
        formPanel.add(groupsLabel, gbc);

        // Subgroup Table
        gbc.gridy++;
        subgroupModel = new DefaultTableModel(new Object[]{"Subgroup Name", "Actions"}, 0);
        JTable table = new JTable(subgroupModel);
        table.setBackground(inputBg);
        table.setForeground(lightText);
        table.setFont(labelFont);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        formPanel.add(scrollPane, gbc);

        // Subgroup Input Row
        gbc.gridy++;
        JPanel subgroupInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subgroupInputPanel.setBackground(darkBackground);
        subgroupField = makeTextField(inputBg, lightText);
        subgroupField.setPreferredSize(new Dimension(200, 30));
        subgroupField.addActionListener(e -> addSubgroup());

        JButton addButton = new JButton("+ Add");
        styleButton(addButton, accentColor, lightText);
        addButton.addActionListener(e -> addSubgroup());

        JButton editButton = new JButton("Edit");
        styleButton(editButton, accentColor, lightText);
        editButton.addActionListener(e -> editSubgroup());

        JButton deleteButton = new JButton("X");
        styleButton(deleteButton, accentColor, lightText);
        deleteButton.addActionListener(e -> deleteSubgroup());

        subgroupInputPanel.add(subgroupField);
        subgroupInputPanel.add(addButton);
        subgroupInputPanel.add(editButton);
        subgroupInputPanel.add(deleteButton);

        formPanel.add(subgroupInputPanel, gbc);

        // Scroll wrapper
        JScrollPane contentScroll = new JScrollPane(formPanel);
        contentScroll.getVerticalScrollBar().setUnitIncrement(16);
        contentScroll.setBorder(null);

        // Add all to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentScroll, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        pack();
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

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bg.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }

    private void addSubgroup() {
        String name = subgroupField.getText().trim();
        if (!name.isEmpty()) {
            subgroups.add(name);
            subgroupModel.addRow(new Object[]{name, "Edit/Delete"});
            subgroupField.setText("");
        }
    }

    private void editSubgroup() {
        int selectedRow = getSelectedRow();
        if (selectedRow != -1) {
            String subgroupName = subgroups.get(selectedRow);

            // Use the main panel (or another JPanel) as the parent panel
            JPanel parentPanel = (JPanel) getContentPane(); // Ensure it's a JPanel

            // Create the SubgroupItemsView and pass the correct parameters
            SubgroupItemsView subgroupItemsView = new SubgroupItemsView(subgroupName, subgroups, parentPanel);

            // Replace the current content with SubgroupItemsView
            parentPanel.removeAll();
            parentPanel.add(subgroupItemsView);
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    }


    private void deleteSubgroup() {
        int selectedRow = getSelectedRow();
        if (selectedRow != -1) {
            subgroups.remove(selectedRow);
            subgroupModel.removeRow(selectedRow);
        }
    }

    private int getSelectedRow() {
        JTable table = (JTable) ((JScrollPane) getContentPane().getComponent(1)).getViewport().getView();
        return table.getSelectedRow();
    }

    private void saveMenuToDatabase() {
        String name = nameField.getText().trim();
        String posName = posNameField.getText().trim();

        if (name.isEmpty() || posName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Name and POS Name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            MenuDAOImplement dao = new MenuDAOImplement();
            dao.addFullMenu(name, posName, subgroups);
            JOptionPane.showMessageDialog(this, "Menu saved successfully!");

            // After saving, transition to SubgroupItemsView
            JPanel parentPanel = (JPanel) getContentPane();  // Get the main panel

            // Create and display the SubgroupItemsView
            SubgroupItemsView subgroupItemsView = new SubgroupItemsView(name, subgroups, parentPanel);
            parentPanel.removeAll();
            parentPanel.add(subgroupItemsView);
            parentPanel.revalidate();
            parentPanel.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving menu: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

