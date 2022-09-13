package com.accounts.accountproject.testclient;

import org.springframework.context.annotation.Profile;

@Profile("test")
public interface AccountService {

    String createAndReturnAccountNumber(String personalId, String productType, String accountName);

    void modifyAccountName(String accountNumber, String accountName);

    AccountData fetchDetails(String accountNumber);

    void close(String accountNumber);
}
