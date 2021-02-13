package com.ironhack.MidTerm.controller.users.impl;

import com.ironhack.MidTerm.controller.users.DTO.AccountHolderCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.controller.users.interfaces.IAccountHolderController;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.accounts.Account;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.GeneratedValue;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class AccountHolderController implements IAccountHolderController {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private IAccountHolderService accountHolderService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STANDARD_USER')")
    @GetMapping(value = "/account-holder/{username}")
    public Object getIdByUsername(@PathVariable String username) {
        if (username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username can't be empty");
        } else {
            AccountHolder accountHolder = accountHolderService.getAccountHolderByUsername(username);
            return "{\"accountHolderId\": " + accountHolder.getId() +"}";
        }
    }

    @PostMapping("/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolderGetRequestDTO createAccountHolder(@RequestBody @Valid AccountHolderCreationRequestDTO creationRequestDTO) {
        return accountHolderService.createAccountHolder(creationRequestDTO);
    }


    @PutMapping(value = "/account-holder/{id}/primary-address")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Address> changePrimaryAddress(@PathVariable Long id, @RequestBody Address address){
        return accountHolderService.changePrimaryAddress(id, address);
    }

    @PutMapping(value = "/account-holder/{id}/secondary-address")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Address> changeSecondaryAddress(@PathVariable Long id, @RequestBody Address address){
        return accountHolderService.changeSecondaryAddress(id, address);
    }
}
