package com.cornershopapp.usersapi.services.exceptions;

public class FailedToCreateUserException extends RuntimeException {
    public FailedToCreateUserException() {
    }

    public FailedToCreateUserException(String message) {
        super(message);
    }

    public FailedToCreateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
