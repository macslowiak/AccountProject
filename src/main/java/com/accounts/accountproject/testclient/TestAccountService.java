package com.accounts.accountproject.testclient;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class TestAccountService implements AccountService{
    @Override
    public String createAndReturnAccountNumber(String personalId, String productType, String accountName) {
        return null;
    }

    @Override
    public void modifyAccountName(String accountNumber, String accountName) {

    }

    @Override
    public AccountData fetchDetails(String accountNumber) {
        return null;
    }

    @Override
    public void close(String accountNumber) {

    }
}
