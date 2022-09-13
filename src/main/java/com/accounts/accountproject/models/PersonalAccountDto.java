package com.accounts.accountproject.models;

import com.accounts.accountproject.testclient.AccountData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalAccountDto {
    private String accountNumber;
    private String personalId;
    private String productType;
    private String accountName;

    public PersonalAccountDto personalAccountDtoFrom(AccountData accountData) {
        this.accountNumber = accountData.getAccountNumber();
        this.personalId = accountData.getPersonalId();
        this.productType = accountData.getProductType();
        this.accountName = accountData.getAccountName();

        return this;
    }
}
