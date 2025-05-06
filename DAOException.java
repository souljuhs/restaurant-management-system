package com.fantastic.restaurant;

/**
 * A custom exception class to handle errors related to data access operations.
 * This exception can be thrown when a problem occurs during data retrieval,
 * insertion, update, or deletion operations.
 */
@SuppressWarnings("serial")
public class DAOException extends Exception {

    // Constructor that accepts a message
    public DAOException(String message) {
        super(message);
    }

    // Constructor that accepts a message and cause
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts only the cause of the exception
    public DAOException(Throwable cause) {
        super(cause);
    }
}
