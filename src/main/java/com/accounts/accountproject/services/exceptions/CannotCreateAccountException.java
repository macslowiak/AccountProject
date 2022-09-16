package com.accounts.accountproject.services.exceptions;

public class CannotCreateAccountException extends RuntimeException {

    public CannotCreateAccountException(String message) {
        super(message);
    }

    public CannotCreateAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
