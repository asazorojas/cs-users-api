package com.cornershopapp.usersapi.services.exceptions;

public class FailedToDeleteUserException extends RuntimeException {
    public FailedToDeleteUserException() {
    }

    public FailedToDeleteUserException(String message) {
        super(message);
    }

    public FailedToDeleteUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
