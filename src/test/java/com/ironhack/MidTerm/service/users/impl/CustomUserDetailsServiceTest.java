package com.ironhack.MidTerm.service.users.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserDetailsServiceTest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Main Street", "1E", "90210", "USA");
        Address address2 = new Address("Second Street", "2F", "90210", "USA");
        AccountHolder primaryOwner = new AccountHolder("username_1", "123456", "First Name", "Last Name", "Personal ID", LocalDate.parse("1990-12-25"), address, address2);
        AccountHolder secondaryOwner = new AccountHolder("username_2", "123456", "Name First", "Name Last", "ID Personal", LocalDate.parse("1990-12-25"), address, address2);
        accountHolderRepository.saveAll(List.of(primaryOwner,secondaryOwner));
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
    }

    @Test
    void loadUserByUsername_correctUsername_UserDetails() {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("username_1");

        assertEquals("username_1", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_wrongUsername_exception() {
        assertThrows(UsernameNotFoundException.class, ()-> customUserDetailsService.loadUserByUsername("wrong username")) ;
    }
}