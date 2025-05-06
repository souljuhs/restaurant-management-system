package com.fantastic.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implements the ReservationDAO interface to manage reservations in the database.
 *
 * This class handles adding new reservations, viewing existing reservations,
 * and updating reservation status. It ensures efficient and dependable database
 * interaction using prepared statements and proper resource handling.
 * Maintainability: Well-structured, modular methods. Easy to modify or extend the logic.
 * Security: Prepared statements prevent SQL injection.
 * Efficiency: Optimized database interactions with prepared statements and automatic resource management.
 * Acceptability: Follows standard Java conventions and is easy for developers to understand and use.
 * 
 */
public class ReservationDAOImplement implements ReservationDAO {

    /**
     * Adds a new reservation to the database.
     *
     * This method inserts a new record into the Reservations table using the data
     * from the provided Reservation object.
     *
     * @param reservation The reservation to be added.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void add(Reservation reservation) throws SQLException {
        Connection connect = Database.getConnection();

        String sqlStatement = "INSERT INTO Reservations (customer_name, email, phone, table_id, date_time, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connect.prepareStatement(sqlStatement)) {
            statement.setString(1, reservation.getCustomerName());
            statement.setString(2, reservation.getCustomerEmail());
            statement.setString(3, reservation.getCustomerPhoneNumber());
            statement.setInt(4, reservation.getTableId());
            statement.setString(5, reservation.getReservationTime());
            statement.setString(6, reservation.getStatus());
            statement.executeUpdate();

            System.out.println("Reservation Made!");
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging if needed
        }

        connect.close();
    }

    /**
     * Displays all reservations from the database.
     *
     * This method retrieves all records from the Reservations table and prints
     * their details to the console.
     *
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void view() throws SQLException {
        Connection connect = Database.getConnection();
        ResultSet rs = null;

        String sqlStatement = "SELECT * FROM Reservations";

        try {
            PreparedStatement statement = connect.prepareStatement(sqlStatement);
            rs = statement.executeQuery();

            System.out.printf("%-3s %-8s %-20s %-11s %-3s %-20s %-10s\n", "ID", "Name", "Email", "Phone#", "TableID", "ReservationTime", "Status");

            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String name = rs.getString("customer_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int tableId = rs.getInt("table_id");
                String dateTime = rs.getString("date_time");
                String status = rs.getString("status");

                System.out.printf("%-3d %-8s %-20s %-11s %-7d %-20s %-8s\n", id, name, email, phone, tableId, dateTime, status);
            }
        } catch (SQLException e) {
            System.out.println(e); // Replace with logging if needed
        }

        connect.close();
    }

    /**
     * Updates the status of an existing reservation.
     *
     * This method sets a new status value for the reservation identified by its ID.
     *
     * @param status The new reservation status (e.g., "confirmed", "cancelled").
     * @param id The reservation ID to update.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void update(String status, int id) throws SQLException {
        Connection connect = Database.getConnection();

        String sqlStatement = "UPDATE Reservations SET status = ? WHERE reservation_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(sqlStatement)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();

            System.out.println("Reservation #" + id + " updated to " + status + "!");
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging if needed
        }

        connect.close();
    }
}

