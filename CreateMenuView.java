package com.fantastic.restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * CreateMenuView is a Swing-based GUI for creating a new restaurant menu.
 * It allows users to input a menu name, POS name, and add/edit/delete subgroups.
 * Upon saving, it persists the menu to a database and transitions to the SubgroupItemsView.
 */
public class CreateMenuView extends JFrame {

    private static final long serialVersionUID = 1L;
    private UserDAO userManager;
    private Runnable onSuccess;

    // UI components
    private JTextField nameField;
    private JTextField posNameField;
    private JTextField subgroupField;
    private DefaultTableModel subgroupModel;
    private JTable table;
    private JButton saveButton;
    private JButton backButton;


    // Data storage
    private List<String> subgroups = new ArrayList<>();

    /**
     * Constructs the CreateMenuView GUI window.
     */
    public CreateMenuView(UserDAO userManager, Runnable onSuccess) {
        this.userManager = userManager;
        this.onSuccess = onSuccess;
        setTitle("Create New Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    /**
     * Initializes all UI components and layout for the view.
     */
    private void initUI() {
        // Define theme colors and fonts
        Color darkBackground = new Color(30, 30, 30);
        Color lightText = new Color(230, 230, 230);
        Color inputBg = new Color(45, 45, 45);
        Color accentColor = new Color(70, 130, 180);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(darkBackground);

     // Top panel with Back, Title and Save buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkBackground);
        topPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Back button
        backButton = new JButton("← Back");
        styleButton(backButton, accentColor, lightText);
        backButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose(); // Close the containing window
            }
            new AdminMainPage(userManager, onSuccess).setVisible(true); // Go back to AdminMainPage
        });



        // Title
        JLabel header = new JLabel("Create Menu", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(lightText);

        // Save button
        saveButton = new JButton("Save");
        styleButton(saveButton, accentColor, lightText);
        saveButton.addActionListener(e -> saveMenuToDatabase());

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(header, BorderLayout.CENTER);
        topPanel.add(saveButton, BorderLayout.EAST);


        // Center panel with form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(darkBackground);
        formPanel.setBorder(new EmptyBorder(10, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Naming section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel namingLabel = new JLabel("Naming");
        namingLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        namingLabel.setForeground(lightText);
        formPanel.add(namingLabel, gbc);

        gbc.gridy++;
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(100, 100, 100));
        formPanel.add(separator, gbc);
        gbc.gridwidth = 1;

        // Menu name input
        gbc.gridy++;
        formPanel.add(makeLabel("Name:", labelFont, lightText), gbc);
        nameField = makeTextField(inputBg, lightText);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // POS name input
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(makeLabel("POS Name:", labelFont, lightText), gbc);
        posNameField = makeTextField(inputBg, lightText);
        gbc.gridx = 1;
        formPanel.add(posNameField, gbc);

        // Subgroup section title
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel groupsLabel = new JLabel("Groups");
        groupsLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        groupsLabel.setForeground(lightText);
        formPanel.add(groupsLabel, gbc);
        gbc.gridwidth = 1;

        // Table for subgroups
        gbc.gridy++;
        subgroupModel = new DefaultTableModel(new Object[]{"Subgroup Name", "Actions"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        table = new JTable(subgroupModel);
        table.setBackground(inputBg);
        table.setForeground(lightText);
        table.setFont(labelFont);
        table.setRowHeight(28);
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Actions").setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        formPanel.add(scrollPane, gbc);

        // Input and action buttons for subgroups
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

        subgroupInputPanel.add(subgroupField);
        subgroupInputPanel.add(addButton);
        subgroupInputPanel.add(editButton);
        formPanel.add(subgroupInputPanel, gbc);

        JScrollPane contentScroll = new JScrollPane(formPanel);
        contentScroll.getVerticalScrollBar().setUnitIncrement(16);
        contentScroll.setBorder(null);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentScroll, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        pack();
    }

    // Creates a styled JLabel
    private JLabel makeLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    // Creates a styled JTextField
    private JTextField makeTextField(Color bg, Color fg) {
        JTextField tf = new JTextField(20);
        tf.setBackground(bg);
        tf.setForeground(fg);
        tf.setCaretColor(fg);
        tf.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        return tf;
    }

    // Applies consistent styling to a button
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

    // Adds a subgroup to the list and table
    private void addSubgroup() {
        String name = subgroupField.getText().trim();
        if (!name.isEmpty()) {
            subgroups.add(name);
            subgroupModel.addRow(new Object[]{name, "X"});
            subgroupField.setText("");
        }
    }

    // Enables editing the selected subgroup name
    private void editSubgroup() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            table.editCellAt(selectedRow, 0);
            table.getEditorComponent().requestFocus();
        }
    }

    // Saves the menu to the database and transitions to SubgroupItemsView
    private void saveMenuToDatabase() {
        String name = nameField.getText().trim();
        String posName = posNameField.getText().trim();

        if (name.isEmpty() || posName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Name and POS Name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Initialize connection (adjust as needed for your project setup)
            Connection connection = Database.getConnection();  // <-- Assumes DBUtil exists and provides a valid DB connection

            Connection conn = Database.getConnection();
            MenuDAOImplement dao = new MenuDAOImplement(conn);

            int menuId = dao.addFullMenu(name, posName, subgroups);

            JOptionPane.showMessageDialog(this, "Menu saved successfully!");

            JPanel parentPanel = (JPanel) getContentPane();
            SubgroupItemsView subgroupItemsView = new SubgroupItemsView(name, menuId, subgroups, parentPanel, connection);
            parentPanel.removeAll();
            parentPanel.add(subgroupItemsView);
            parentPanel.revalidate();
            parentPanel.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving menu: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Renders a simple button in a JTable cell.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {
        private static final long serialVersionUID = 1L;

        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(70, 70, 70));
            setForeground(Color.WHITE);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }

    /**
     * Provides button editor functionality to delete subgroups from the table and list.
     */
    class ButtonEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 1L;
        private final JButton button;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setBackground(new Color(70, 70, 70));
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setText("X");
                    button.setBackground(new Color(90, 90, 90));
                }

                public void mouseExited(MouseEvent e) {
                    button.setText("");
                    button.setBackground(new Color(70, 70, 70));
                }
            });

            button.addActionListener(e -> deleteRow());
        }

        // Removes a subgroup entry
        private void deleteRow() {
            if (currentRow >= 0 && currentRow < subgroups.size()) {
                subgroups.remove(currentRow);
                subgroupModel.removeRow(currentRow);
            }
            fireEditingStopped();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}




