package com.dachpc.kletterapp.Security;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exception-Weitwurf jey!

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFound(EntityNotFoundException ex) {
        System.err.println("Entity not found: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgument(IllegalArgumentException ex) {
        System.err.println("Illegal argument: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public void handleIllegalState(IllegalStateException ex) {
        System.err.println("Illegal state: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        System.err.println("Data integrity violation: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        System.err.println("Missing request parameter: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        System.err.println("Method not allowed: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleMessageNotReadable(HttpMessageNotReadableException ex) {
        System.err.println("Message not readable: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleGeneralException(Exception ex) {
        System.err.println("Unhandled " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }

    // man könnte auch eigene Exceptions definieren und
    // die dann direkt mit @ResponseStatus annotieren
}
