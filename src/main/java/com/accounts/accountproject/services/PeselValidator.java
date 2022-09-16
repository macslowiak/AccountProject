package com.accounts.accountproject.services;

import com.accounts.accountproject.services.exceptions.WrongPeselException;

public class PeselValidator {
    private static final int PESEL_LENGTH = 11;
    private static final int[] PESEL_WEIGHTS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    public static boolean isPeselValid(String pesel) {

        if (pesel.length() != PESEL_LENGTH) {
            throw new WrongPeselException("Provided wrong customer PESEL: " + pesel);
        }

        int validationNumber = 0;

        for (int i = 0; i < PESEL_LENGTH - 1; i++) {
            validationNumber = validationNumber + Integer.parseInt(pesel.substring(i, i + 1)) * PESEL_WEIGHTS[i];
        }
        validationNumber = 10 - (validationNumber % 10);
        int controlNumber = Integer.parseInt(pesel.substring(PESEL_LENGTH - 1, PESEL_LENGTH));

        if (validationNumber == controlNumber) {
            return true;
        } else {
            throw new WrongPeselException("Provided wrong customer PESEL: " + pesel);
        }
    }
}