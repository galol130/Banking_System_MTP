package com.ironhack.MidTerm.service.users.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidTerm.controller.users.DTO.AccountHolderCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.repository.StudentCheckingAccountRepository;
import com.ironhack.MidTerm.service.accounts.impl.StudentCheckingAccountService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccountHolderServiceTest {
    @Autowired
    private AccountRepository accountRepository;

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
        Money balance =  new Money(BigDecimal.valueOf(500), Currency.getInstance("USD"));
        SecretKey secretKey = EncryptorUtil.createSecretKey("123456");
        AccountHolder primaryOwner = new AccountHolder("username_1", "123456", "First Name", "Last Name", "Personal ID", LocalDate.parse("1990-12-25"), address, address2);
        AccountHolder secondaryOwner = new AccountHolder("username_2", "123456", "Name First", "Name Last", "ID Personal", LocalDate.parse("1990-12-25"), address, address2);
        AccountHolder ownsNothing = new AccountHolder("username_3", "123456", "firstname", "lastname", "personalid", LocalDate.parse("1990-12-25"), address, address2);
        accountHolderRepository.saveAll(List.of(primaryOwner,secondaryOwner, ownsNothing));
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();

    }

    @Test
    void findAccountHolderById() {
//  Nothing to test here...
    }

    @Test
    void getAccountHolderByUsername_existingUsername_accountHolder() {
        AccountHolder accountHolder = accountHolderService.getAccountHolderByUsername("username_1");
        assertEquals("First Name", accountHolder.getFirstName());
    }

    @Test
    void getAccountHolderByUsername_nonExistingUsername_exception() {
        assertThrows(ResponseStatusException.class, ()-> accountHolderService.getAccountHolderByUsername("wrong username"));
    }

    @Test
    void createAccountHolder_everythingOK_AccountHolderGetRequestDTO() {
        AccountHolderCreationRequestDTO creationRequestDTO = new AccountHolderCreationRequestDTO(
          "username_creation",
          "123456",
          "First Name creation",
          "Last Name creation",
          "ABC1",
          LocalDate.parse("1990-10-10"),
          "Algo St.",
          "12",
          "21",
          "no extra info",
          "City creation",
          "State creation",
          "90210",
          "USA"
        );
        AccountHolderGetRequestDTO accountHolder = accountHolderService.createAccountHolder(creationRequestDTO);

        assertEquals("90210", accountHolder.getPostalCode());
    }

    @Test
    void createAccountHolder_alreadyTakenUsername_exception() {
        AccountHolderCreationRequestDTO creationRequestDTO = new AccountHolderCreationRequestDTO(
                "username_1",
                "123456",
                "First Name creation",
                "Last Name creation",
                "ABC1",
                LocalDate.parse("1990-10-10"),
                "Algo St.",
                "12",
                "21",
                "no extra info",
                "City creation",
                "State creation",
                "90210",
                "USA"
        );

        assertThrows(ResponseStatusException.class, ()-> accountHolderService.createAccountHolder(creationRequestDTO));
    }

    @Test
    void createAccountHolder_personalIdAlreadyInDB_exception() {
        AccountHolderCreationRequestDTO creationRequestDTO = new AccountHolderCreationRequestDTO(
                "username_creation",
                "123456",
                "First Name creation",
                "Last Name creation",
                "Personal ID",
                LocalDate.parse("1990-10-10"),
                "Algo St.",
                "12",
                "21",
                "no extra info",
                "City creation",
                "State creation",
                "90210",
                "USA"
        );

        assertThrows(ResponseStatusException.class, ()-> accountHolderService.createAccountHolder(creationRequestDTO));
    }


    @Test
    void convertAccountHolderToDTO() {
//  Nothing to test here...
    }

    @Test
    void changePrimaryAddress_Ok_ListOfAddresses() {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        Address address = new Address("Address changed", "99", "90210", "USA");

        List<Address> addressList = accountHolderService.changePrimaryAddress(accountHolder.get().getId(), address);

        assertEquals("99", addressList.get(0).getDoorNumber());
    }

    @Test
    void changePrimaryAddress_wrongId_exception() {
        Address address = new Address("Address changed", "99", "90210", "USA");

        assertThrows(ResponseStatusException.class, ()-> accountHolderService.changePrimaryAddress(123L, address));
    }

    @Test
    void changeSecondaryAddress_Ok_ListOfAddresses() {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        Address address = new Address("Address changed", "99", "90210", "USA");

        List<Address> addressList = accountHolderService.changeSecondaryAddress(accountHolder.get().getId(), address);

        assertEquals("99", addressList.get(1).getDoorNumber());
    }

    @Test
    void changeSecondaryAddress_wrongId_exception() {
        Address address = new Address("Address changed", "99", "90210", "USA");

        assertThrows(ResponseStatusException.class, ()-> accountHolderService.changeSecondaryAddress(123L, address));
    }
}