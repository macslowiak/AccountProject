package com.accounts.accountproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//Can be passed only with existing interfaces implementation

@SpringBootTest
@ActiveProfiles("test")
class AccountProjectApplicationTests {

    @Test
    void contextLoads() {
    }

}
