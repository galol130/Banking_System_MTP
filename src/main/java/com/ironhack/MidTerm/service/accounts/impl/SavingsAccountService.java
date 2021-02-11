package com.ironhack.MidTerm.service.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountCreationRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.SavingsAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.SavingsAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.ISavingsAccountService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Service
public class SavingsAccountService implements ISavingsAccountService {
    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private IAccountHolderService accountHolderService;


    @Override
    public SavingsAccount createSavingsAccount(SavingsAccountCreationRequestDTO creationRequestDTO, AccountHolder accountHolder) {
        SavingsAccount savingsAccount;
        BigDecimal amount = BigDecimal.valueOf(creationRequestDTO.getBalanceAmount());
        Currency currency = Currency.getInstance(creationRequestDTO.getBalanceCurrency());
        Money balance = new Money(amount, currency);
        SecretKey secretKey = EncryptorUtil.createSecretKey(creationRequestDTO.getSecretKey());

        if (creationRequestDTO.getSecondaryAccountHolderId() != null) {
            Optional<AccountHolder> secondaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getAccountHolderId());
            savingsAccount = secondaryAccountHolder.map(secondaryHolder -> new SavingsAccount(balance, secretKey, accountHolder, secondaryHolder, Status.ACTIVE)).orElseGet(() -> new SavingsAccount(balance, secretKey, accountHolder, Status.ACTIVE));
        } else {
            savingsAccount = new SavingsAccount(balance, secretKey, accountHolder, Status.ACTIVE);
        }

        if (creationRequestDTO.getInterestRate() > 0)
            savingsAccount.setInterestRate(creationRequestDTO.getInterestRate());
        if (creationRequestDTO.getMinimumBalance() > 0)
            savingsAccount.setMinimumBalance(new Money(BigDecimal.valueOf(creationRequestDTO.getMinimumBalance())));

        return savingsAccountRepository.save(savingsAccount);
    }
}