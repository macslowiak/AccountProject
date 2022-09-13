package com.accounts.accountproject.testclient;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class TestCustomerService implements CustomerService{
    @Override
    public boolean hasCustomerFile(String personalId, String firstName, String lastName) {
        return false;
    }
}
