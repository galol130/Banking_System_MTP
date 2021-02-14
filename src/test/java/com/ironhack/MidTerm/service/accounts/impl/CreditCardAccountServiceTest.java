package com.ironhack.MidTerm.service.accounts.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountGetRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountGetRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.CheckingAccount;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.repository.CheckingAccountRepository;
import com.ironhack.MidTerm.repository.CreditCardAccountRepository;
import com.ironhack.MidTerm.service.accounts.interfaces.ICheckingAccountService;
import com.ironhack.MidTerm.service.accounts.interfaces.ICreditCardAccountService;
import com.ironhack.MidTerm.service.users.impl.AccountHolderService;
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
class CreditCardAccountServiceTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    private CreditCardAccountService creditCardAccountService;

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
        Money balance =  new Money(BigDecimal.valueOf(500), Currency.getInstance("USD"));
        SecretKey secretKey = EncryptorUtil.createSecretKey("123456");
        AccountHolder primaryOwner = new AccountHolder("username_1", "123456", "First Name", "Last Name", "Personal ID", LocalDate.parse("1990-12-25"), address, address);
        AccountHolder secondaryOwner = new AccountHolder("username_2", "123456", "Name First", "Name Last", "ID Personal", LocalDate.parse("1990-12-25"), address, address);
        AccountHolder ownsNothing = new AccountHolder("username_3", "123456", "firstname", "lastname", "personalid", LocalDate.parse("1990-12-25"), address, address);
        accountHolderRepository.saveAll(List.of(primaryOwner,secondaryOwner, ownsNothing));
        CreditCardAccount account1 = new CreditCardAccount(balance, secretKey, primaryOwner, secondaryOwner, Status.ACTIVE);
        creditCardAccountRepository.save(account1);

    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        creditCardAccountRepository.deleteAll();
    }

    @Test
    void createCreditCardAccount_correctDTO_created() {
        CreditCardAccountCreationRequestDTO creationRequestDTO = new CreditCardAccountCreationRequestDTO(
                1L,
                2L,
                "USD",
                500D,
                "123456",
                1000D,
                0.15
        );
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        CreditCardAccountGetRequestDTO creditCardAccount = creditCardAccountService.createCreditCardAccount(creationRequestDTO, accountHolderRepository.getOne(accountHolder.get().getId()));

        assertEquals(500D,creditCardAccount.getBalance().getAmount().doubleValue());
    }

    @Test
    void createCreditCardAccount_wrongCurrencyDTO_exception() {
        CreditCardAccountCreationRequestDTO creationRequestDTO = new CreditCardAccountCreationRequestDTO(
                1L,
                2L,
                "FFF",
                500D,
                "123456",
                1000D,
                0.15
        );
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        assertThrows(ResponseStatusException.class, ()-> creditCardAccountService.createCreditCardAccount(creationRequestDTO, accountHolderRepository.getOne(accountHolder.get().getId())));

    }

    @Test
    void convertCreditCardAccountToDTO_checkInterestRate_overAMonth_updateBalance() {
        Money balance =  new Money(BigDecimal.valueOf(500D), Currency.getInstance("USD"));
        SecretKey secretKey = EncryptorUtil.createSecretKey("123456");
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");

        CreditCardAccount creditCardAccount = new CreditCardAccount(
          balance,
          secretKey,
          accountHolder.get(),
          Status.ACTIVE
        );
        creditCardAccount = creditCardAccountRepository.save(creditCardAccount);
        Long id = creditCardAccount.getId();
        creditCardAccount.setStartDate(LocalDate.of(2020,1,10));
        creditCardAccount.setLastDateOfInterestCharge(LocalDate.of(2021,1,10));
        CreditCardAccountGetRequestDTO accountGetRequestDTO =  creditCardAccountService.convertCreditCardAccountToDTO(creditCardAccount);

        Optional<CreditCardAccount> creditCardAccountUpdated = creditCardAccountRepository.findById(id);
        assertTrue(creditCardAccountUpdated.get().getBalance().getAmount().doubleValue() > 500D);
        assertEquals(LocalDate.now(), creditCardAccountUpdated.get().getLastDateOfInterestCharge());
    }

    @Test
    void convertCreditCardAccountToDTO_checkInterestRate_notAMonth_keepBalance() {
        Money balance =  new Money(BigDecimal.valueOf(500D), Currency.getInstance("USD"));
        SecretKey secretKey = EncryptorUtil.createSecretKey("123456");
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");

        CreditCardAccount creditCardAccount = new CreditCardAccount(
                balance,
                secretKey,
                accountHolder.get(),
                Status.ACTIVE
        );
        creditCardAccount = creditCardAccountRepository.save(creditCardAccount);
        Long id = creditCardAccount.getId();

        CreditCardAccountGetRequestDTO accountGetRequestDTO =  creditCardAccountService.convertCreditCardAccountToDTO(creditCardAccount);

        Optional<CreditCardAccount> creditCardAccountUpdated = creditCardAccountRepository.findById(id);
        assertEquals(500D, creditCardAccountUpdated.get().getBalance().getAmount().doubleValue());
        assertEquals(LocalDate.now(), creditCardAccountUpdated.get().getLastDateOfInterestCharge());

    }
}