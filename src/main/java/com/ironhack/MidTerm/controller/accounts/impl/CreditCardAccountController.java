package com.ironhack.MidTerm.controller.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.interfaces.ICreditCardAccountController;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.CreditCardAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.ICreditCardAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CreditCardAccountController implements ICreditCardAccountController {
    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    private ICreditCardAccountService creditCardAccountService;

    @Autowired
    private IAccountHolderService accountHolderService;

    @GetMapping(value = "/credit-card-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCardAccount> getCreditCardAccounts(){return creditCardAccountRepository.findAll();}

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/credit-card-account")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardAccount createCreditCardAccount(CreditCardAccountCreationRequestDTO creationRequestDTO) {
        Optional<AccountHolder> primaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getAccountHolderId());
        if (primaryAccountHolder.isPresent()) {
            return creditCardAccountService.createCreditCardAccount(creationRequestDTO, primaryAccountHolder.get());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Holder ID not found");
        }
    }


}
