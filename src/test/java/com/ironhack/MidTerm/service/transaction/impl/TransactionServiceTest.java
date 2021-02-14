package com.ironhack.MidTerm.service.transaction.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.repository.ThirdPartyRepository;
import com.ironhack.MidTerm.repository.TransactionRepository;
import com.ironhack.MidTerm.service.users.impl.AccountHolderService;
import com.ironhack.MidTerm.service.users.impl.ThirdPartyService;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.users.interfaces.IThirdPartyService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private ThirdPartyService thirdPartyService;


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
    }

    @Test
    void transferBetweenAccounts() {
    }

    @Test
    void collectFunds() {
    }

    @Test
    void depositFunds() {
    }

    @Test
    void getTransactionsByThirdPartyId() {
    }

    @Test
    void getTransactionsByAccountHolderId() {
    }

    @Test
    void getTransactionsByOriginAccountId() {
    }

    @Test
    void getTransactionsByDestinationAccountId() {
    }

    @Test
    void convertTransferToDTO() {
    }

    @Test
    void convertCollectionToDTO() {
    }

    @Test
    void convertDepositToDTO() {
    }
}