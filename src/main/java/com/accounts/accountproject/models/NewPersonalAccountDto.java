package com.accounts.accountproject.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewPersonalAccountDto {
    private String personalId;
    private String firstName;
    private String lastName;
    private String productType;
    private String accountName;
}
