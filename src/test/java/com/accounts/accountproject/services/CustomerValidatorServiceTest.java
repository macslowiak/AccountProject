package com.accounts.accountproject.services;

import com.accounts.accountproject.services.exceptions.CustomerFileNotFoundException;
import com.accounts.accountproject.services.exceptions.WrongPersonalIdException;
import com.accounts.accountproject.testclient.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerValidatorServiceTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerValidatorService customerValidatorService;

    @BeforeEach
    void setUp() {
        customerValidatorService = new CustomerValidatorService(customerService);
    }

    @Test
    void shouldReturnTrueWhenCustomerFileWasFoundForCustomer() {
        //given
        given(customerService.hasCustomerFile(any(), any(), any())).willReturn(true);

        //when
        boolean hasCustomerFile = customerValidatorService.hasCustomerFile(
                "81042587922",
                "firstName",
                "lastName");

        //then
        assertTrue(hasCustomerFile);
    }

    @Test
    void shouldThrowCustomerFileNotFoundExceptionWhenCustomerFileWasNotFoundForProvidedUser() {
        //given
        given(customerService.hasCustomerFile(any(), any(), any())).willReturn(false);

        //when and then
        Throwable exception = assertThrows(CustomerFileNotFoundException.class,
                () -> customerValidatorService.hasCustomerFile(
                        "81042587922",
                        "firstName",
                        "lastName"));

        assertEquals("Provided customer does not have customer file created. Customer: 81042587922",
                exception.getMessage());
    }

    @Test
    void shouldReturnTrueWhenProvidedTypeOfAccountIsUniqueForCustomer() {
        //given and when
        boolean isAccountTypeUnique = customerValidatorService.isAccountTypeUniqueForCustomer();

        //then
        assertTrue(isAccountTypeUnique);
    }

    @Test
    void shouldReturnTrueWhenCustomerPersonalIdIsValid() {
        //given
        String personalId = "81042587922";

        //when
        boolean isPersonalIdValid = customerValidatorService.validateCustomerPersonalId(personalId);

        //then
        assertTrue(isPersonalIdValid);
    }

    @Test
    void shouldThrowWrongPersonalIdExceptionWhenPersonalIdHasWrongFormat() {
        //given
        String personalId = "81042587924";

        //when and then
        Throwable exception = assertThrows(WrongPersonalIdException.class,
                () -> customerValidatorService.validateCustomerPersonalId(personalId));

        assertEquals("Provided wrong customer id: 81042587924", exception.getMessage());
    }
}