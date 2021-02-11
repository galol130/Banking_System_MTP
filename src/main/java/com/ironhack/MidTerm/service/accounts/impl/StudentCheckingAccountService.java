package com.ironhack.MidTerm.service.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountGetRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.StudentCheckingAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.StudentCheckingAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.IStudentCheckingAccountService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Service
public class StudentCheckingAccountService implements IStudentCheckingAccountService {

    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    private IAccountHolderService accountHolderService;


    @Override
    public CheckingAccountGetRequestDTO createAccount(CheckingAccountCreationRequestDTO creationRequestDTO, AccountHolder primaryAccountHolder) {
        StudentCheckingAccount studentCheckingAccount;
        BigDecimal amount = BigDecimal.valueOf(creationRequestDTO.getBalanceAmount());
        Currency currency = Currency.getInstance(creationRequestDTO.getBalanceCurrency());
        Money balance = new Money(amount, currency);
        SecretKey secretKey = EncryptorUtil.createSecretKey(creationRequestDTO.getSecretKey());

        if (creationRequestDTO.getSecondaryAccountHolderId() != null) {
            Optional<AccountHolder> secondaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getSecondaryAccountHolderId());
            if (secondaryAccountHolder.isPresent() && !secondaryAccountHolder.get().getId().equals(creationRequestDTO.getAccountHolderId())) {
                studentCheckingAccount = studentCheckingAccountRepository
                        .save(new StudentCheckingAccount(balance, secretKey, primaryAccountHolder, secondaryAccountHolder.get(), Status.ACTIVE));
                return convertStudentCheckingAccountToDTO(studentCheckingAccount);
            }
        }
        studentCheckingAccount = studentCheckingAccountRepository
                .save(new StudentCheckingAccount(balance, secretKey, primaryAccountHolder, Status.ACTIVE));
        return convertStudentCheckingAccountToDTO(studentCheckingAccount);
    }

    public CheckingAccountGetRequestDTO convertStudentCheckingAccountToDTO(StudentCheckingAccount studentCheckingAccount) {
        return new CheckingAccountGetRequestDTO(
                studentCheckingAccount.getClass().getSimpleName(),
                studentCheckingAccount.getId(),
                studentCheckingAccount.getStartDate(),
                studentCheckingAccount.getPrimaryOwner(),
                studentCheckingAccount.getSecondaryOwner(),
                studentCheckingAccount.getBalance(),
                studentCheckingAccount.getPenaltyFee(),
                studentCheckingAccount.getStatus()
        );
    }
}
