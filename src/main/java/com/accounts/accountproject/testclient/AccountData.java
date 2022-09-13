package com.accounts.accountproject.testclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Profile;

@Data
@AllArgsConstructor
@Profile("test")
public class AccountData {
    private String accountNumber;
    private String personalId;
    private String productType;
    private String accountName;
}
