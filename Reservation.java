package com.fantastic.restaurant;

/**
 * Represents a restaurant reservation with attributes such as customer name,
 * email, phone number, table ID, reservation time, and status.
 * This class is designed with maintainability, dependability, security,
 * efficiency, and acceptability in mind.
 * 
 * The class ensures valid data, provides intuitive access to its attributes,
 * and allows flexible changes to the reservation status.
 */
public class Reservation {

    // Reservation attributes
    private String customerName;        // Required
    private String customerEmail;       // Not Required
    private String customerPhoneNumber; // Not Required
    private int tableID;                // Not Required
    private String reservationTime;     // Required
    private String tableStatus;         // Default "booked" if not set

    /**
     * Constructs a new Reservation with the specified customer details, table ID,
     * reservation time, and status. If the status is invalid, it defaults to "booked".
     * 
     * @param name the name of the customer
     * @param email the email address of the customer (optional)
     * @param phone the phone number of the customer (optional)
     * @param id the table ID for the reservation
     * @param time the reservation time (required)
     * @param status the status of the reservation (1 = booked, 2 = cancelled, 3 = completed)
     * @throws IllegalArgumentException if required fields are missing or invalid
     */
    public Reservation(String name, String email, String phone, int id, String time, int status) {
        setCustomerName(name);
        setCustomerEmail(email);
        setCustomerPhoneNumber(phone);
        setTableId(id);
        setReservationTime(time);
        setStatus(status);
    }

    // Enum representing reservation status
    enum Status {
        booked,
        cancelled,
        completed
    }

    // Setters with validation or default behavior

    /**
     * Sets the customer name for this reservation.
     * 
     * @param name the name of the customer
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setCustomerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        this.customerName = name;
    }

    /**
     * Sets the customer email for this reservation.
     * 
     * @param email the email address of the customer
     * @throws IllegalArgumentException if the email format is invalid
     */
    public void setCustomerEmail(String email) {
        // Basic validation for email format (can be expanded for more robust validation)
        if (email != null && !email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.customerEmail = email;
    }

    /**
     * Sets the customer phone number for this reservation.
     * 
     * @param phone the phone number of the customer
     * @throws IllegalArgumentException if the phone number format is invalid
     */
    public void setCustomerPhoneNumber(String phone) {
        if (phone != null && !phone.matches("\\d{10}")) { // Validate phone number format (10 digits)
            throw new IllegalArgumentException("Invalid phone number format.");
        }
        this.customerPhoneNumber = phone;
    }

    /**
     * Sets the table ID for this reservation.
     * 
     * @param id the table ID
     * @throws IllegalArgumentException if the table ID is invalid
     */
    public void setTableId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Table ID must be a positive integer.");
        }
        this.tableID = id;
    }

    /**
     * Sets the reservation time for this reservation.
     * 
     * @param time the reservation time in string format (e.g., "2022-12-12 18:30")
     * @throws IllegalArgumentException if the reservation time is null or incorrectly formatted
     */
    public void setReservationTime(String time) {
        if (time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation time cannot be null or empty.");
        }
        this.reservationTime = time;
    }

    /**
     * Sets the status of this reservation. The status is determined based on the
     * provided integer value. If the value is invalid, it defaults to "booked".
     * 
     * @param status an integer representing the reservation status
     *               (1 = booked, 2 = cancelled, 3 = completed)
     * @throws IllegalArgumentException if the status is invalid
     */
    public void setStatus(int status) {
        Status temp;

        switch (status) {
            case 1:
                temp = Status.booked;
                break;
            case 2:
                temp = Status.cancelled;
                break;
            case 3:
                temp = Status.completed;
                break;
            default:
                throw new IllegalArgumentException("Invalid status. Must be 1 (booked), 2 (cancelled), or 3 (completed).");
        }

        this.tableStatus = temp.toString();
    }

    // Getters

    /**
     * Gets the customer name for this reservation.
     * 
     * @return the name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the customer email for this reservation.
     * 
     * @return the email address of the customer
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Gets the customer phone number for this reservation.
     * 
     * @return the phone number of the customer
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * Gets the table ID for this reservation.
     * 
     * @return the table ID associated with the reservation
     */
    public int getTableId() {
        return tableID;
    }

    /**
     * Gets the reservation time for this reservation.
     * 
     * @return the reservation time
     */
    public String getReservationTime() {
        return reservationTime;
    }

    /**
     * Gets the current status of this reservation.
     * 
     * @return the status of the reservation (e.g., "booked", "cancelled", "completed")
     */
    public String getStatus() {
        return tableStatus;
    }

    /**
     * Validates that all required fields are properly set for the reservation.
     * 
     * @throws IllegalStateException if any required field is missing
     */
    public void validate() {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalStateException("Customer name is required.");
        }
        if (reservationTime == null || reservationTime.trim().isEmpty()) {
            throw new IllegalStateException("Reservation time is required.");
        }
        if (tableID <= 0) {
            throw new IllegalStateException("Valid table ID is required.");
        }
    }
}

