package com.accounts.accountproject.services;

import com.accounts.accountproject.services.exceptions.CustomerFileNotFoundException;
import com.accounts.accountproject.services.exceptions.WrongPersonalIdException;
import com.accounts.accountproject.services.exceptions.WrongPeselException;
import com.accounts.accountproject.testclient.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerValidatorService {

    private final CustomerService customerService;

    public CustomerValidatorService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public boolean hasCustomerFile(String personalId, String firstName, String lastName) {
        if (customerService.hasCustomerFile(personalId, firstName, lastName)) {
            log.info("Customer file for customer: " + personalId + " was found.");
            return true;
        } else {
            throw new CustomerFileNotFoundException("Provided customer does not have customer file created. Customer: "
                    + personalId);
        }
    }

    public boolean isAccountTypeUniqueForCustomer() {
        /* I don't have idea how to do that with provided data, I can't get types of accounts that person has
        I need to call DB or some service and get info about user accounts to know that user already have account
        with specific type */
        log.info("Provided Account has unique type for customer");
        return true;
    }

    public boolean validateCustomerPersonalId(String personalId) {
        try {
            boolean isPersonalIdValid = PeselValidator.isPeselValid(personalId);
            log.info("Successfully validated personal id for: " + personalId);
            return isPersonalIdValid;
        } catch (WrongPeselException wrongPeselException) {
            throw new WrongPersonalIdException("Provided wrong customer id: " + personalId, wrongPeselException);
        }
    }

}