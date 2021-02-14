package com.ironhack.MidTerm.service.accounts.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountGetRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.CheckingAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.repository.CheckingAccountRepository;
import com.ironhack.MidTerm.service.accounts.interfaces.ICheckingAccountService;
import com.ironhack.MidTerm.service.users.impl.AccountHolderService;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
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
class CheckingAccountServiceTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private ICheckingAccountService checkingAccountService;

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
        CheckingAccount acc1 = new CheckingAccount(balance, secretKey, primaryOwner, secondaryOwner, Status.ACTIVE);
        checkingAccountRepository.save(acc1);

    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        checkingAccountRepository.deleteAll();
    }

    @Test
    void createAccount_correctDTO_created() {
        CheckingAccountCreationRequestDTO creationRequestDTO = new CheckingAccountCreationRequestDTO(
                1L,
                2L,
                "USD",
                500D,
                "123456"
        );
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        CheckingAccountGetRequestDTO checkingAccount = checkingAccountService.createAccount(creationRequestDTO, accountHolderRepository.getOne(accountHolder.get().getId()));

        assertEquals(500D,checkingAccount.getBalance().getAmount().doubleValue());
    }

    @Test
    void createAccount_wrongCurrencyDTO_exception() {
        CheckingAccountCreationRequestDTO creationRequestDTO = new CheckingAccountCreationRequestDTO(
                1L,
                2L,
                "FFF",
                500D,
                "123456"
        );
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername("username_1");
        assertThrows(ResponseStatusException.class, ()-> checkingAccountService.createAccount(creationRequestDTO, accountHolderRepository.getOne(accountHolder.get().getId())));
    }

    @Test
    void convertCheckingAccountToDTO() {
//  Nothing to test...

    }
}