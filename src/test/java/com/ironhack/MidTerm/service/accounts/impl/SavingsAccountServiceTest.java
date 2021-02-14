package com.ironhack.MidTerm.service.accounts.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountGetRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountGetRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;
import com.ironhack.MidTerm.model.accounts.SavingsAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.repository.CreditCardAccountRepository;
import com.ironhack.MidTerm.repository.SavingsAccountRepository;
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
class SavingsAccountServiceTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private SavingsAccountService savingsAccountService;

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
        SavingsAccount account1 = new SavingsAccount(balance, secretKey, primaryOwner, secondaryOwner, Status.ACTIVE);
        savingsAccountRepository.save(account1);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        savingsAccountRepository.deleteAll();
    }

    @Test
    void createSavingsAccount_correctDTO_created() {
        SavingsAccountCreationRequestDTO creationRequestDTO = new SavingsAccountCreationRequestDTO(
                1L,
                2L,
                "USD",
                2000D,
                200D,
                "123456",
                0.35
        );
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        SavingsAccountGetRequestDTO savingsAccountDTO = savingsAccountService. createSavingsAccount(creationRequestDTO, accountHolderRepository.getOne(accountHolder.get().getId()));

        assertEquals(2000D,savingsAccountDTO.getBalance().getAmount().doubleValue());
    }

    @Test
    void createSavingsAccount_wrongCurrencyDTO_exception() {
        SavingsAccountCreationRequestDTO creationRequestDTO = new SavingsAccountCreationRequestDTO(
                1L,
                2L,
                "FFF",
                2000D,
                200D,
                "123456",
                0.35
        );
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        assertThrows(ResponseStatusException.class, ()-> savingsAccountService.createSavingsAccount(creationRequestDTO, accountHolderRepository.getOne(accountHolder.get().getId())));
    }

    @Test
    void convertSavingsAccountToDTO_checkInterestRate_overYear_updateBalance() {
        Money balance =  new Money(BigDecimal.valueOf(2000D), Currency.getInstance("USD"));
        SecretKey secretKey = EncryptorUtil.createSecretKey("123456");
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");

        SavingsAccount savingsAccount = new SavingsAccount(
                balance,
                secretKey,
                accountHolder.get(),
                Status.ACTIVE
        );
        savingsAccount = savingsAccountRepository.save(savingsAccount);
        Long id = savingsAccount.getId();
        savingsAccount.setStartDate(LocalDate.of(2010,1,10));
        savingsAccount.setLastDateOfInterestPayment(LocalDate.of(2020,2,10));
        SavingsAccountGetRequestDTO accountGetRequestDTO =  savingsAccountService.convertSavingsAccountToDTO(savingsAccount);

        Optional<SavingsAccount> savingsAccountUpdated = savingsAccountRepository.findById(id);
        assertTrue(savingsAccountUpdated.get().getBalance().getAmount().doubleValue() > 2000D);
        assertEquals(LocalDate.now(), savingsAccountUpdated.get().getLastDateOfInterestPayment());
    }

    @Test
    void convertSavingsAccountToDTO_checkInterestRate_notAYear_keepBalance() {
        Money balance =  new Money(BigDecimal.valueOf(2000D), Currency.getInstance("USD"));
        SecretKey secretKey = EncryptorUtil.createSecretKey("123456");
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");

        SavingsAccount savingsAccount = new SavingsAccount(
                balance,
                secretKey,
                accountHolder.get(),
                Status.ACTIVE
        );
        savingsAccount = savingsAccountRepository.save(savingsAccount);
        Long id = savingsAccount.getId();
        SavingsAccountGetRequestDTO accountGetRequestDTO =  savingsAccountService.convertSavingsAccountToDTO(savingsAccount);

        Optional<SavingsAccount> savingsAccountUpdated = savingsAccountRepository.findById(id);
        assertEquals(2000D, savingsAccountUpdated.get().getBalance().getAmount().doubleValue());
        assertEquals(LocalDate.now(), savingsAccountUpdated.get().getLastDateOfInterestPayment());
    }
}