package com.accounts.accountproject.services;

import com.accounts.accountproject.testclient.AccountData;
import com.accounts.accountproject.testclient.AccountService;
import com.accounts.accountproject.testclient.CustomerService;
import com.accounts.accountproject.models.NewPersonalAccountDto;
import com.accounts.accountproject.models.PersonalAccountDto;
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
    CustomerService customerService;

    @Mock
    AccountService accountService;

    @InjectMocks
    PersonalAccountService personalAccountService;

    @BeforeEach
    void setUp() {
        personalAccountService = new PersonalAccountService(accountService, customerService);
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenCustomerNotExistsInDb() {
        //given
        NewPersonalAccountDto newPersonalAccountDto = Mockito.mock(NewPersonalAccountDto.class);
        given(customerService.hasCustomerFile(any(), any(), any())).willReturn(false);


        //when and then
        Throwable exception = assertThrows(CustomerFileNotFoundException.class,
                () -> personalAccountService.createAndReturnAccountNumber(newPersonalAccountDto));

        //then
        assertEquals("Provided customer does not exist in the database.", exception.getMessage());
    }

    @Test
    void shouldReturnAccountNumberWhenProvidePersonalAccountDataAndCustomerExistsInDb() {
        //given
        String expectedAccountNumber = "61109010140000071219812874";
        NewPersonalAccountDto newPersonalAccountDto = Mockito.mock(NewPersonalAccountDto.class);
        given(customerService.hasCustomerFile(any(), any(), any())).willReturn(true);
        given(accountService.createAndReturnAccountNumber(any(), any(), any())).willReturn(
                expectedAccountNumber
        );

        //when and then
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