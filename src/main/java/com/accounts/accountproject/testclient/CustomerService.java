package com.accounts.accountproject.testclient;

import org.springframework.context.annotation.Profile;

@Profile("test")
public interface CustomerService {
    boolean hasCustomerFile(String personalId, String firstName, String lastName);
}
