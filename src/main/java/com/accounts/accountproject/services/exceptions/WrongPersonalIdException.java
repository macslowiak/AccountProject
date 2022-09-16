package com.accounts.accountproject.services.exceptions;

public class WrongPersonalIdException extends RuntimeException {

    public WrongPersonalIdException(String message) {
        super(message);
    }

    public WrongPersonalIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
