package com.oneHealth.Appointments.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.oneHealth.Appointments.DTO.ErrorMessageDTO;


/**
 * GlobalExceptionHandler is a centralized exception handling class for the application.
 * It uses the @ControllerAdvice annotation to handle exceptions globally across all controllers.
 * @author Anup
 * @version 3.9.10
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ProfileNotFoundException and returns a custom error response with NOT_FOUND status (404).
     *
     * @param ex      The ProfileNotFoundException instance.
     * @param request The WebRequest object containing the details of the current request.
     * @return A ResponseEntity containing the error details and NOT_FOUND status (404).
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> ProfileNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    
    
    
    /**
     * Handles ProfileNotFoundException and returns a custom error response with NOT_FOUND status (404).
     *
     * @param ex      The ProfileNotFoundException instance.
     * @param request The WebRequest object containing the details of the current request.
     * @return A ResponseEntity containing the error details and NOT_FOUND status (404).
     */
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<?> ProfileNotFoundException(ProfileNotFoundException ex, WebRequest request) {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    
    
    
    /**
     * Handles DatabaseException and returns a custom error response with UNPROCESSABLE_ENTITY status (422).
     *
     * @param ex      The DatabaseException instance.
     * @param request The WebRequest object containing the details of the current request.
     * @return A ResponseEntity containing the error details and UNPROCESSABLE_ENTITY status (422).
     */
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<?> DatabaseException(DatabaseException ex, WebRequest request) {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    
    
    
    
    
    
    
    
    

    /**
     * Handles all other unhandled exceptions and returns a custom error response with INTERNAL_SERVER_ERROR status (500).
     *
     * @param ex      The Exception instance representing the unhandled exception.
     * @param request The WebRequest object containing the details of the current request.
     * @return A ResponseEntity containing the error details and INTERNAL_SERVER_ERROR status (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
