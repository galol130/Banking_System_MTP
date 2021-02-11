package com.ironhack.MidTerm.service.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountGetRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.Account;
import com.ironhack.MidTerm.model.accounts.CheckingAccount;
import com.ironhack.MidTerm.model.accounts.StudentCheckingAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.CheckingAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.ICheckingAccountService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;


@Service
public class CheckingAccountService implements ICheckingAccountService {
    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private IAccountHolderService accountHolderService;

    @Override
    public CheckingAccountGetRequestDTO createAccount(CheckingAccountCreationRequestDTO creationRequestDTO, AccountHolder primaryAccountHolder) {
        CheckingAccount checkingAccount;
        BigDecimal amount = BigDecimal.valueOf(creationRequestDTO.getBalanceAmount());
        Currency currency = Currency.getInstance(creationRequestDTO.getBalanceCurrency());
        Money balance = new Money(amount, currency);
        SecretKey secretKey = EncryptorUtil.createSecretKey(creationRequestDTO.getSecretKey());

        if (creationRequestDTO.getSecondaryAccountHolderId() != null) {
            Optional<AccountHolder> secondaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getSecondaryAccountHolderId());
            if (secondaryAccountHolder.isPresent()) {
                if (!secondaryAccountHolder.get().equals(primaryAccountHolder)) {
                    checkingAccount = checkingAccountRepository
                            .save(new CheckingAccount(balance, secretKey, primaryAccountHolder, secondaryAccountHolder.get(), Status.ACTIVE));
                    return convertCheckingAccountToDTO(checkingAccount);
                }
            }
        }
        checkingAccount = checkingAccountRepository
                .save(new CheckingAccount(balance, secretKey, primaryAccountHolder, Status.ACTIVE));

        return convertCheckingAccountToDTO(checkingAccount);
    }

    public CheckingAccountGetRequestDTO convertCheckingAccountToDTO(CheckingAccount checkingAccount) {
        return new CheckingAccountGetRequestDTO(
                checkingAccount.getClass().getSimpleName(),
                checkingAccount.getId(),
                checkingAccount.getStartDate(),
                checkingAccount.getPrimaryOwner(),
                checkingAccount.getSecondaryOwner(),
                checkingAccount.getBalance(),
                checkingAccount.getMinimumBalance(),
                checkingAccount.getMonthlyMaintenanceFee(),
                checkingAccount.getPenaltyFee(),
                checkingAccount.getStatus()
        );
    }


}
