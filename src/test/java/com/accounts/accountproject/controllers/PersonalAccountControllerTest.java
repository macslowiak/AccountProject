package com.accounts.accountproject.controllers;

import com.accounts.accountproject.models.NewPersonalAccountDto;
import com.accounts.accountproject.models.PersonalAccountDto;
import com.accounts.accountproject.services.CustomerFileNotFoundException;
import com.accounts.accountproject.services.PersonalAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonalAccountController.class)
class PersonalAccountControllerTest {

    @MockBean
    PersonalAccountService personalAccountService;

    @InjectMocks
    PersonalAccountController personalAccountController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        personalAccountController = new PersonalAccountController(personalAccountService);
    }

    @Test
    void shouldReturn404WhenCustomerFileNotFoundExceptionIsThrown() throws Exception {
        //given
        NewPersonalAccountDto personalAccountDto = NewPersonalAccountDto.builder().build();
        given(personalAccountService.createAndReturnAccountNumber(any(NewPersonalAccountDto.class)))
                .willThrow(CustomerFileNotFoundException.class);

        //when and then
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personalAccountDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn201WhenAccountIsCreated() throws Exception {
        //given
        NewPersonalAccountDto personalAccountDto = NewPersonalAccountDto.builder().build();
        given(personalAccountService.createAndReturnAccountNumber(any()))
                .willReturn("61109010140000071219812872");

        //when and then
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personalAccountDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location", "http://localhost/accounts/61109010140000071219812872"));
    }

    @Test
    void shouldReturn200WhenFetchAccountDetails() throws Exception {
        //given
        PersonalAccountDto personalAccountDto = new PersonalAccountDto();
        given(personalAccountService.fetchDetails(any()))
                .willReturn(personalAccountDto);

        //when and then
        mockMvc.perform(get("/accounts/61109010140000071219812872"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(personalAccountDto)));
    }

    @Test
    void shouldReturn204WhenAccountNameIsModified() throws Exception {
        mockMvc.perform(patch("/accounts/61109010140000071219812872/homeaccount"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenAccountIsClosed() throws Exception {
        mockMvc.perform(delete("/accounts/61109010140000071219812872"))
                .andExpect(status().isNoContent());
    }
}