package com.fantastic.restaurant;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablesView extends JPanel {
    
    private JTable tablesTable;
    private JScrollPane scrollPane;
    
    public TablesView() {
        setLayout(new BorderLayout());
        initializeTablesView();
    }
    
    private void initializeTablesView() {
        // Fetch tables from the database and display them
        List<TableInfo> tableList = fetchTablesFromDatabase();
        
        // Create a JTable to display the tables
        String[] columns = {"Table Number", "Capacity", "Status"};
        Object[][] data = new Object[tableList.size()][3];
        
        for (int i = 0; i < tableList.size(); i++) {
            TableInfo table = tableList.get(i);
            data[i][0] = table.getTableNumber();
            data[i][1] = table.getCapacity();
            data[i][2] = table.getStatus();
        }
        
        tablesTable = new JTable(data, columns);
        tablesTable.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(tablesTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private List<TableInfo> fetchTablesFromDatabase() {
        List<TableInfo> tableList = new ArrayList<>();
        
        try (Connection connect = Database.getConnection()) {
            String sql = "SELECT table_number, capacity, status FROM Tables";
            try (PreparedStatement statement = connect.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                
                while (resultSet.next()) {
                    int tableNumber = resultSet.getInt("table_number");
                    int capacity = resultSet.getInt("capacity");
                    String status = resultSet.getString("status");
                    
                    tableList.add(new TableInfo(tableNumber, capacity, status));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving tables: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return tableList;
    }
    
    private static class TableInfo {
        private int tableNumber;
        private int capacity;
        private String status;
        
        public TableInfo(int tableNumber, int capacity, String status) {
            this.tableNumber = tableNumber;
            this.capacity = capacity;
            this.status = status;
        }

        public int getTableNumber() {
            return tableNumber;
        }

        public int getCapacity() {
            return capacity;
        }

        public String getStatus() {
            return status;
        }
    }

    // Test the TablesView in a JFrame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tables View");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(new TablesView());
            frame.setVisible(true);
        });
    }
}
