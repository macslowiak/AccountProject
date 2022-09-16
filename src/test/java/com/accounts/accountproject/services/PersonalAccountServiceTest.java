package com.accounts.accountproject.services;

import com.accounts.accountproject.models.NewPersonalAccountDto;
import com.accounts.accountproject.models.PersonalAccountDto;
import com.accounts.accountproject.services.exceptions.CannotCreateAccountException;
import com.accounts.accountproject.services.exceptions.CustomerFileNotFoundException;
import com.accounts.accountproject.services.exceptions.WrongPersonalIdException;
import com.accounts.accountproject.testclient.AccountData;
import com.accounts.accountproject.testclient.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonalAccountServiceTest {

    @Mock
    CustomerValidatorService customerValidatorService;

    @Mock
    AccountService accountService;

    @InjectMocks
    PersonalAccountService personalAccountService;

    @BeforeEach
    void setUp() {
        personalAccountService = new PersonalAccountService(accountService, customerValidatorService);
    }

    @Test
    void shouldThrowCannotCreateAccountExceptionWhenCatchWrongPersonalIdException() {
        //given
        NewPersonalAccountDto newPersonalAccountDto = Mockito.mock(NewPersonalAccountDto.class);
        given(customerValidatorService.validateCustomerPersonalId(any())).willThrow(WrongPersonalIdException.class);

        //when and then
        Throwable exception = assertThrows(CannotCreateAccountException.class,
                () -> personalAccountService.createAndReturnAccountNumber(newPersonalAccountDto));

        assertEquals("CANNOT_CREATE_ACCOUNT", exception.getMessage());
    }

    @Test
    void shouldThrowCannotCreateAccountExceptionWhenCatchCustomerFileNotFoundException() {
        //given
        NewPersonalAccountDto newPersonalAccountDto = Mockito.mock(NewPersonalAccountDto.class);
        given(customerValidatorService.hasCustomerFile(any(), any(), any())).willThrow(CustomerFileNotFoundException.class);

        //when and then
        Throwable exception = assertThrows(CannotCreateAccountException.class,
                () -> personalAccountService.createAndReturnAccountNumber(newPersonalAccountDto));

        assertEquals("CANNOT_CREATE_ACCOUNT", exception.getMessage());
    }

    @Test
    void shouldReturnAccountNumberWhenProvidePersonalAccountDataAndCustomerPassValidation() {
        //given
        String expectedAccountNumber = "61109010140000071219812874";
        NewPersonalAccountDto newPersonalAccountDto = Mockito.mock(NewPersonalAccountDto.class);
        given(customerValidatorService.hasCustomerFile(any(), any(), any())).willReturn(true);
        given(customerValidatorService.validateCustomerPersonalId(any())).willReturn(true);
        given(customerValidatorService.isAccountTypeUniqueForCustomer()).willReturn(true);
        given(accountService.createAndReturnAccountNumber(any(), any(), any())).willReturn(
                expectedAccountNumber
        );

        //when
        String accountNumber = personalAccountService.createAndReturnAccountNumber(newPersonalAccountDto);

        //then
        assertEquals(expectedAccountNumber, accountNumber);
    }


    @Test
    void shouldCallAccountServiceWhenTryToModifyAccountName() {
        personalAccountService.modifyAccountName(any(), any());
        verify(accountService).modifyAccountName(any(), any());
    }

    @Test
    void shouldReturnPersonalAccountDtoWhenFetchingPersonalAccountDetails() {
        //given
        AccountData accountData = new AccountData(
                "61109010140000071219812874",
                "c2aa3cca-1d12-4953-aa96-e9cc59a78b22",
                "golden",
                "home"
        );
        PersonalAccountDto expectedPersonalAccountDto = PersonalAccountDto.builder()
                .accountNumber("61109010140000071219812874")
                .personalId("c2aa3cca-1d12-4953-aa96-e9cc59a78b22")
                .productType("golden")
                .accountName("home")
                .build();
        given(accountService.fetchDetails(any())).willReturn(accountData);

        //when
        PersonalAccountDto personalAccountDto = personalAccountService.fetchDetails(any());

        //then
        assertEquals(expectedPersonalAccountDto, personalAccountDto);
    }

    @Test
    void shouldCallAccountServiceWhenTryToClosePersonalAccount() {
        personalAccountService.close(any());
        verify(accountService).close(any());
    }
}