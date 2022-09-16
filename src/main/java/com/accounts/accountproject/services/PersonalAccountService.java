package com.accounts.accountproject.services;

import com.accounts.accountproject.models.NewPersonalAccountDto;
import com.accounts.accountproject.models.PersonalAccountDto;
import com.accounts.accountproject.services.exceptions.CannotCreateAccountException;
import com.accounts.accountproject.services.exceptions.CustomerFileNotFoundException;
import com.accounts.accountproject.services.exceptions.WrongPersonalIdException;
import com.accounts.accountproject.testclient.AccountData;
import com.accounts.accountproject.testclient.AccountService;
import org.springframework.stereotype.Service;

@Service
public class PersonalAccountService {

    private final AccountService accountService;
    private final CustomerValidatorService newCustomerValidatorService;

    public PersonalAccountService(AccountService accountService, CustomerValidatorService newCustomerValidatorService) {
        this.accountService = accountService;
        this.newCustomerValidatorService = newCustomerValidatorService;
    }

    public String createAndReturnAccountNumber(NewPersonalAccountDto newPersonalAccountDto) {
        String accountNumber;

        try {
            /* I do not create one method for validation in newCustomerValidatorService because I thought some
            methods can be used somewhere else alone */
            newCustomerValidatorService.validateCustomerPersonalId(newPersonalAccountDto.getPersonalId());
            newCustomerValidatorService.isAccountTypeUniqueForCustomer();
            newCustomerValidatorService.hasCustomerFile(
                    newPersonalAccountDto.getPersonalId(),
                    newPersonalAccountDto.getFirstName(),
                    newPersonalAccountDto.getLastName());
        } catch (CustomerFileNotFoundException | WrongPersonalIdException customerFileNotFoundException) {
            throw new CannotCreateAccountException("CANNOT_CREATE_ACCOUNT", customerFileNotFoundException);
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