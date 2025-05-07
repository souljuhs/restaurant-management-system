package com.fantastic.restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class SubgroupItemsView extends JPanel {
    private JTable itemTable;
    private DefaultTableModel itemModel;
    private JButton addItemButton;
    private JButton saveButton;
    private JLabel menuLabel;
    private JPanel subgroupButtonPanel;
    private String selectedSubgroup = null;

    // Color scheme
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color PANEL_BG = new Color(245, 245, 245);
    private static final Color DARK_TEXT = new Color(30, 30, 30);
    private static final Color GRAY_HEADER = new Color(220, 220, 220);
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);

    public SubgroupItemsView(String menuName, List<String> subgroups, JPanel parentPanel) {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // ─── Top Panel ───────────────────────────────
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(PANEL_BG);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        menuLabel = new JLabel("Menu: " + menuName);
        menuLabel.setFont(HEADER_FONT);
        menuLabel.setForeground(DARK_TEXT);
        topPanel.add(menuLabel, BorderLayout.WEST);

        saveButton = new JButton("Save All");
        styleButton(saveButton);
        topPanel.add(saveButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ─── Left Panel (Subgroups) ──────────────────
        subgroupButtonPanel = new JPanel();
        subgroupButtonPanel.setLayout(new BoxLayout(subgroupButtonPanel, BoxLayout.Y_AXIS));
        subgroupButtonPanel.setBackground(PANEL_BG);
        subgroupButtonPanel.setPreferredSize(new Dimension(180, getHeight()));
        subgroupButtonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        for (String subgroup : subgroups) {
            JButton button = new JButton(subgroup);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(160, 40));
            button.setFont(DEFAULT_FONT);
            button.setBackground(Color.WHITE);
            button.setForeground(DARK_TEXT);
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

            button.addActionListener(e -> {
                selectedSubgroup = subgroup;
                loadSubgroupItems(subgroup);
            });

            subgroupButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            subgroupButtonPanel.add(button);
        }

        add(subgroupButtonPanel, BorderLayout.WEST);

        // ─── Center Panel (Table + Add Item) ─────────
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);

        // Add Item button at top of center panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(PANEL_BG);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        addItemButton = new JButton("+ Add Item");
        styleButton(addItemButton);
        controlPanel.add(addItemButton);
        centerPanel.add(controlPanel, BorderLayout.NORTH);

        // Table setup
        itemModel = new DefaultTableModel(new Object[]{"Item Name", "Price ($)"}, 0);
        itemTable = new JTable(itemModel);
        itemTable.setFont(DEFAULT_FONT);
        itemTable.setRowHeight(30);
        itemTable.setGridColor(new Color(230, 230, 230));
        itemTable.setBackground(Color.WHITE);
        itemTable.setForeground(DARK_TEXT);
        itemTable.setSelectionBackground(new Color(220, 220, 220));
        itemTable.setSelectionForeground(Color.BLACK);

        JTableHeader tableHeader = itemTable.getTableHeader();
        tableHeader.setFont(HEADER_FONT);
        tableHeader.setBackground(GRAY_HEADER);
        tableHeader.setForeground(DARK_TEXT);
        tableHeader.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // ─── Add Item Logic ──────────────────────────
        addItemButton.addActionListener(e -> {
            itemModel.addRow(new Object[]{"", ""});
        });

        // ─── Save Logic ──────────────────────────────
        saveButton.addActionListener(e -> {
            if (selectedSubgroup == null) {
                JOptionPane.showMessageDialog(this, "Please select a subgroup first.");
                return;
            }

            for (int i = 0; i < itemModel.getRowCount(); i++) {
                String itemName = (String) itemModel.getValueAt(i, 0);
                String priceStr = (String) itemModel.getValueAt(i, 1);
                System.out.println("Saving item: " + itemName + ", $" + priceStr + " in subgroup: " + selectedSubgroup);
                // TODO: Save to DB
            }

            JOptionPane.showMessageDialog(this, "Items saved for subgroup: " + selectedSubgroup);
        });
    }

    private void styleButton(JButton button) {
        button.setFont(DEFAULT_FONT);
        button.setBackground(Color.WHITE);
        button.setForeground(DARK_TEXT);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(240, 240, 240));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
    }

    private void loadSubgroupItems(String subgroupName) {
        itemModel.setRowCount(0); // Clear current table
        System.out.println("Loading items for subgroup: " + subgroupName);
        // TODO: Load from DB if needed
    }
}


