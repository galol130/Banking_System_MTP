package com.ironhack.MidTerm.controller.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountGetRequestDTO;
import com.ironhack.MidTerm.controller.accounts.interfaces.ISavingsAccountController;
import com.ironhack.MidTerm.model.accounts.SavingsAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.SavingsAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.ISavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SavingsAccountController implements ISavingsAccountController {
    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private ISavingsAccountService savingsAccountService;

    @Autowired
    private IAccountHolderService accountHolderService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/savings-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingsAccount> getSavingsAccounts(){ return savingsAccountRepository.findAll();}

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/savings-account")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccountGetRequestDTO createCheckingAccount(@RequestBody @Valid SavingsAccountCreationRequestDTO creationRequestDTO) {
        Optional<AccountHolder> primaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getAccountHolderId());
        if (primaryAccountHolder.isPresent()) {
            return savingsAccountService.createSavingsAccount(creationRequestDTO, primaryAccountHolder.get());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Account Holder ID");
        }
    }

}
