package com.oneHealth.Appointments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Custom exception class for representing the scenario when a record is not found in the application.
 * This exception is annotated with @ResponseStatus, indicating that it will trigger a 404 NOT_FOUND response
 * when thrown.
 * @author Anup
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends Exception
{
	
	private static final long serialVersionUID = 1L;

    /**
     * Constructor for creating a new instance of ProfileNotFoundException with a custom error message.
     *
     * @param message The error message indicating the reason for the exception.
     */
    public RecordNotFoundException(String message) 
    {
        super(message);
    }

}
