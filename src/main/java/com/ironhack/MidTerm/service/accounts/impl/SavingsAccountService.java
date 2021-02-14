package com.ironhack.MidTerm.service.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountGetRequestDTO;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.SavingsAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.SavingsAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.ISavingsAccountService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import com.ironhack.MidTerm.utils.styles.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Currency;
import java.util.Optional;

@Service
public class SavingsAccountService implements ISavingsAccountService {
    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private IAccountHolderService accountHolderService;


    @Override
    public SavingsAccountGetRequestDTO createSavingsAccount(SavingsAccountCreationRequestDTO creationRequestDTO, AccountHolder accountHolder) {
        SavingsAccount savingsAccount;
        BigDecimal amount = BigDecimal.valueOf(creationRequestDTO.getBalanceAmount());
        Currency currency;
        try {
            currency = Currency.getInstance(creationRequestDTO.getBalanceCurrency());
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency acronym does not exist");
        }
        Money balance = new Money(amount, currency);
        SecretKey secretKey = EncryptorUtil.createSecretKey(creationRequestDTO.getSecretKey());

        if (creationRequestDTO.getSecondaryAccountHolderId() != null) {
            Optional<AccountHolder> secondaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getSecondaryAccountHolderId());
            if (secondaryAccountHolder.isPresent())
                if (!secondaryAccountHolder.get().equals(accountHolder))
                    savingsAccount = new SavingsAccount(balance, secretKey, accountHolder, secondaryAccountHolder.get(), Status.ACTIVE);
                else
                    savingsAccount = new SavingsAccount(balance, secretKey, accountHolder, Status.ACTIVE);
            else
                savingsAccount = new SavingsAccount(balance, secretKey, accountHolder, Status.ACTIVE);
        } else
            savingsAccount = new SavingsAccount(balance, secretKey, accountHolder, Status.ACTIVE);


        if (creationRequestDTO.getInterestRate() > 0)
            savingsAccount.setInterestRate(creationRequestDTO.getInterestRate());
        if (creationRequestDTO.getMinimumBalance() > 0)
            savingsAccount.setMinimumBalance(new Money(BigDecimal.valueOf(creationRequestDTO.getMinimumBalance())));

        savingsAccountRepository.save(savingsAccount);

        return convertSavingsAccountToDTO(savingsAccount);
    }

    public SavingsAccountGetRequestDTO convertSavingsAccountToDTO(SavingsAccount savingsAccount) {
        //Every time a SavingsAccount is called, the return uses this DTO, so we verify the interest payment as well.
        checkInterestPayment(savingsAccount);

        return new SavingsAccountGetRequestDTO(
                savingsAccount.getId(),
                savingsAccount.getStartDate(),
                savingsAccount.getBalance(),
                savingsAccount.getPrimaryOwner(),
                savingsAccount.getSecondaryOwner(),
                savingsAccount.getStatus(),
                savingsAccount.getMinimumBalance(),
                savingsAccount.getPenaltyFee(),
                savingsAccount.getInterestRate());
    }

    private void checkInterestPayment(SavingsAccount savingsAccount){
        Period elapsedTime = Period.between(savingsAccount.getLastDateOfInterestPayment(), LocalDate.now());
        //If one year has passed since the last payment, the balance is increased and the payment date reset.
        if(elapsedTime.getYears()>0){
            Double interestRate = savingsAccount.getInterestRate();
            BigDecimal amountToAdd = BigDecimal.valueOf(savingsAccount.getBalance().getAmount().doubleValue() * interestRate);
            savingsAccount.getBalance().increaseAmount(amountToAdd);
            savingsAccount.setLastDateOfInterestPayment(LocalDate.now());
            savingsAccountRepository.save(savingsAccount);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Annual interest rate payment done!");
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT);
        }
    }
}