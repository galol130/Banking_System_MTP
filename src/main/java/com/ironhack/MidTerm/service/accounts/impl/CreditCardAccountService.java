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
import com.ironhack.MidTerm.utils.styles.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
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
            if (secondaryAccountHolder.isPresent()) {
                if (!secondaryAccountHolder.get().equals(accountHolder)) {
                    creditCardAccount = new CreditCardAccount(balance, secretKey, accountHolder, secondaryAccountHolder.get(), Status.ACTIVE);
                } else {
                    creditCardAccount = new CreditCardAccount(balance, secretKey, accountHolder, Status.ACTIVE);
                }
            } else {
                creditCardAccount = new CreditCardAccount(balance, secretKey, accountHolder, Status.ACTIVE);
            }
        } else {
            creditCardAccount = new CreditCardAccount(balance, secretKey, accountHolder, Status.ACTIVE);
        }

        if (creationRequestDTO.getInterestRate() > 0)
            creditCardAccount.setInterestRate(creationRequestDTO.getInterestRate());
        if (creationRequestDTO.getCreditLimit() > 0)
            creditCardAccount.setCreditLimit(creationRequestDTO.getCreditLimit());

        creditCardAccountRepository.save(creditCardAccount);

        return convertCreditCardAccountToDTO(creditCardAccount);
    }

    public CreditCardAccountGetRequestDTO convertCreditCardAccountToDTO(CreditCardAccount creditCardAccount) {
        //Every time a CreditCardAccount is called, the return uses this DTO, so we verify the interest charge as well.
        checkInterestCharge(creditCardAccount);

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

    private void checkInterestCharge(CreditCardAccount creditCardAccount){
        Period elapsedTime = Period.between(creditCardAccount.getLastDateOfInterestCharge(), LocalDate.now());
        //If one month has passed since the last charge, the balance is increased and the charge date reset.

        if(elapsedTime.getMonths()>0){
            Double interestRate = creditCardAccount.getInterestRate();
            BigDecimal amountToAdd = BigDecimal.valueOf(creditCardAccount.getBalance().getAmount().doubleValue() * interestRate);
            creditCardAccount.getBalance().increaseAmount(amountToAdd);
            creditCardAccount.setLastDateOfInterestCharge(LocalDate.now());
            creditCardAccountRepository.save(creditCardAccount);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Monthly interest rate charge done!");
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT);
        }

    }
}

