package com.accounts.accountproject.services.exceptions;

public class WrongPeselException extends RuntimeException {

    public WrongPeselException(String message) {
        super(message);
    }
}