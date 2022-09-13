package com.accounts.accountproject.services;

import com.accounts.accountproject.models.NewPersonalAccountDto;
import com.accounts.accountproject.models.PersonalAccountDto;
import com.accounts.accountproject.testclient.AccountData;
import com.accounts.accountproject.testclient.AccountService;
import com.accounts.accountproject.testclient.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class PersonalAccountService {

    private final AccountService accountService;
    private final CustomerService customerService;

    public PersonalAccountService(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    public String createAndReturnAccountNumber(NewPersonalAccountDto newPersonalAccountDto) {
        String accountNumber;
        boolean isNewCustomer = !customerService.hasCustomerFile(
                newPersonalAccountDto.getPersonalId(),
                newPersonalAccountDto.getFirstName(),
                newPersonalAccountDto.getLastName()
        );

        if (isNewCustomer) {
            throw new CustomerFileNotFoundException("Provided customer does not exist in the database.");
        }

        accountNumber = accountService.createAndReturnAccountNumber(
                newPersonalAccountDto.getPersonalId(),
                newPersonalAccountDto.getProductType(),
                newPersonalAccountDto.getAccountName()
        );

        return accountNumber;
    }

    public void modifyAccountName(String accountNumber, String accountName) {
        accountService.modifyAccountName(accountNumber, accountName);
    }

    //validation to not use interface unnecessary could be nice
    public PersonalAccountDto fetchDetails(String accountNumber) {
        AccountData accountData = accountService.fetchDetails(accountNumber);
        return new PersonalAccountDto().personalAccountDtoFrom(accountData);
    }

    public void close(String accountNumber) {
        accountService.close(accountNumber);
    }

}