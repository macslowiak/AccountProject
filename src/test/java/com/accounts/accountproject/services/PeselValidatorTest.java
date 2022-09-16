package com.accounts.accountproject.services;

import com.accounts.accountproject.services.exceptions.WrongPeselException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeselValidatorTest {

    @Test
    void shouldThrowWrongPeselExceptionWhenPeselHasWrongLength() {
        //given
        String tooLongPesel = "1234567891234";
        String tooShortPesel = "123456789";

        //when and then
        Throwable isPeselTooLong = assertThrows(WrongPeselException.class,
                () -> PeselValidator.isPeselValid(tooLongPesel));
        Throwable isPeselTooShort = assertThrows(WrongPeselException.class,
                () -> PeselValidator.isPeselValid(tooShortPesel));

        assertEquals("Provided wrong customer PESEL: 1234567891234", isPeselTooLong.getMessage());
        assertEquals("Provided wrong customer PESEL: 123456789", isPeselTooShort.getMessage());
    }

    @Test
    void shouldThrowWrongPeselExceptionWhenPeselControlNumberIsNoteEqualValidationNumber() {
        //given
        String peselNumber = "81042587924";

        //when and then
        Throwable isPeselTooLong = assertThrows(WrongPeselException.class,
                () -> PeselValidator.isPeselValid(peselNumber));

        //then
        assertEquals("Provided wrong customer PESEL: 81042587924", isPeselTooLong.getMessage());
    }

    @Test
    void shouldReturnTrueWhenPeselIsInCorrectFormat() {
        //given
        String peselNumber = "81042587922";

        //when
        boolean peselValidation = PeselValidator.isPeselValid(peselNumber);

        //then
        assertTrue(peselValidation);
    }
}