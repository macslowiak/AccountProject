package com.accounts.accountproject.controllers;

import com.accounts.accountproject.models.NewPersonalAccountDto;
import com.accounts.accountproject.models.PersonalAccountDto;
import com.accounts.accountproject.services.CustomerFileNotFoundException;
import com.accounts.accountproject.services.PersonalAccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/accounts")
public class PersonalAccountController {
    private final PersonalAccountService personalAccountService;

    public PersonalAccountController(PersonalAccountService personalAccountService) {
        this.personalAccountService = personalAccountService;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody NewPersonalAccountDto newPersonalAccountDto) {
        String createdAccountNumber;

        try {
            createdAccountNumber = personalAccountService.createAndReturnAccountNumber(newPersonalAccountDto);
        } catch (CustomerFileNotFoundException customerFileNotFoundException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(customerFileNotFoundException.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION,
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{accountNumber}")
                                .buildAndExpand(createdAccountNumber)
                                .toString())
                .build();
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<PersonalAccountDto> fetchAccountDetails(@PathVariable("accountNumber") String accountNumber) {
        PersonalAccountDto accountDetails = personalAccountService.fetchDetails(accountNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountDetails);
    }

    @PatchMapping("/{accountNumber}/{accountName}")
    public ResponseEntity<String> modifyAccountName(@PathVariable("accountNumber") String accountNumber,
                                                    @PathVariable("accountName") String accountName) {
        personalAccountService.modifyAccountName(accountNumber, accountName);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    //used delete but closing account could mean ex. block it
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> closeAccount(@PathVariable("accountNumber") String accountNumber) {
        personalAccountService.close(accountNumber);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
