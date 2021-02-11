package com.ironhack.MidTerm.controller.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.interfaces.ICheckingAccountController;
import com.ironhack.MidTerm.model.accounts.CheckingAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.CheckingAccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.ICheckingAccountService;
import com.ironhack.MidTerm.service.accounts.interfaces.IStudentCheckingAccountService;
import com.ironhack.MidTerm.utils.AgeCalculatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CheckingAccountController implements ICheckingAccountController {
    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private ICheckingAccountService checkingAccountService;

    @Autowired
    private IAccountHolderService accountHolderService;

    @Autowired
    private IStudentCheckingAccountService studentCheckingAccountService;


    @GetMapping(value = "/checking-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getCheckingAccounts() {
        return checkingAccountRepository.findAll();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/checking-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createCheckingAccount(@RequestBody @Valid CheckingAccountCreationRequestDTO creationRequestDTO) {
        Optional<AccountHolder> primaryAccountHolder = accountHolderService.findAccountHolderById(creationRequestDTO.getAccountHolderId());
        if(primaryAccountHolder.isPresent()){
            if(AgeCalculatorUtil.isOlderThan(primaryAccountHolder.get().getDateOfBirth(), 24)){
                return checkingAccountService.createAccount(creationRequestDTO, primaryAccountHolder.get());
            }else{
                return studentCheckingAccountService.createAccount(creationRequestDTO, primaryAccountHolder.get());
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account holder not found");
        }
    }





}
