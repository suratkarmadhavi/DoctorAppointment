package com.oneHealth.Appointments.DTO;

import java.util.Date;

/**
 * Data Transfer Object (DTO) class representing an error message.
 * This class holds the timestamp of the error occurrence, the error message, and additional details about the error.
 * @author Anup
 * @version 3.9.10
 */
public class ErrorMessageDTO {

    private Date timestamp; // The timestamp of the error occurrence.
    private String message; // The error message.
    private String details; // Additional details about the error.

    public ErrorMessageDTO() {
        // Default constructor required for deserialization by Jackson or other serialization frameworks.
    }

    /**
     * Constructor for creating an ErrorMessageDTO with the specified details.
     *
     * @param timestamp The timestamp of the error occurrence.
     * @param message   The error message.
     * @param details   Additional details about the error.
     */
    public ErrorMessageDTO(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    /**
     * Getter for the timestamp of the error occurrence.
     *
     * @return The timestamp of the error occurrence.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Setter for the timestamp of the error occurrence.
     *
     * @param timestamp The timestamp to set for the error occurrence.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter for the error message.
     *
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the error message.
     *
     * @param message The error message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for additional details about the error.
     *
     * @return Additional details about the error.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Setter for additional details about the error.
     *
     * @param details The additional details to set for the error.
     */
    public void setDetails(String details) {
        this.details = details;
    }
}
