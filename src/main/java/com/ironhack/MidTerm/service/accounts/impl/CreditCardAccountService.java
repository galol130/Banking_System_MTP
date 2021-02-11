package com.ironhack.MidTerm.service.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountGetRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.CreditCardAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.ICreditCardAccountService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

@Service
public class CreditCardAccountService implements ICreditCardAccountService {
    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    private IAccountHolderService accountHolderService;


    @Override
    public CreditCardAccountGetRequestDTO createCreditCardAccount(CreditCardAccountCreationRequestDTO creationRequestDTO, AccountHolder accountHolder) {
        CreditCardAccount creditCardAccount;
        BigDecimal amount = BigDecimal.valueOf(creationRequestDTO.getBalanceAmount());
        Currency currency = Currency.getInstance(creationRequestDTO.getBalanceCurrency());
        Money balance = new Money(amount, currency);
        SecretKey secretKey = EncryptorUtil.createSecretKey(creationRequestDTO.getSecretKey());

        if (creationRequestDTO.getSecondaryAccountHolderId() != null) {
            Optional<AccountHolder> secondaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getSecondaryAccountHolderId());
            creditCardAccount = secondaryAccountHolder.map(secondaryHolder -> new CreditCardAccount(balance, secretKey, accountHolder, secondaryHolder, Status.ACTIVE)).orElseGet(() -> new CreditCardAccount(balance, secretKey, accountHolder, Status.ACTIVE));
        } else
            creditCardAccount = new CreditCardAccount(balance, secretKey, accountHolder, Status.ACTIVE);

        if (creationRequestDTO.getInterestRate() > 0)
            creditCardAccount.setInterestRate(creationRequestDTO.getInterestRate());
        if (creationRequestDTO.getCreditLimit() > 0)
            creditCardAccount.setCreditLimit(creationRequestDTO.getCreditLimit());

        creditCardAccountRepository.save(creditCardAccount);

        return convertCreditCardAccountToDTO(creditCardAccount);
    }

    public CreditCardAccountGetRequestDTO convertCreditCardAccountToDTO(CreditCardAccount creditCardAccount) {
        return new CreditCardAccountGetRequestDTO(
                creditCardAccount.getId(),
                creditCardAccount.getStartDate(),
                creditCardAccount.getBalance(),
                creditCardAccount.getPrimaryOwner(),
                creditCardAccount.getSecondaryOwner(),
                creditCardAccount.getStatus(),
                creditCardAccount.getCreditLimit(),
                creditCardAccount.getInterestRate(),
                creditCardAccount.getPenaltyFee());
    }
}

