package com.ironhack.MidTerm.controller.users.impl;

import com.ironhack.MidTerm.controller.users.DTO.AccountHolderCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.controller.users.interfaces.IAccountHolderController;
import com.ironhack.MidTerm.model.accounts.Account;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.GeneratedValue;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class AccountHolderController implements IAccountHolderController {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private IAccountHolderService accountHolderService;


    @GetMapping(value = "/account-holder/{username}")
    public Long getIdByUsername(@PathVariable String username){
        if(username.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username can't be empty");
        }else{
            AccountHolder accountHolder =  accountHolderService.getAccountHolderByUsername(username);
            return accountHolder.getId();
        }

    }

    @PostMapping(value = "/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolderGetRequestDTO createAccountHolder(@RequestBody @Valid AccountHolderCreationRequestDTO creationRequestDTO) {
        return accountHolderService.createAccountHolder(creationRequestDTO);
    }
}
