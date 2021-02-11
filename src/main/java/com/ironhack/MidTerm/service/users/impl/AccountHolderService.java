package com.ironhack.MidTerm.service.users.impl;

import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountHolderService implements IAccountHolderService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;


    public Optional<AccountHolder> findAccountHolderById(UUID id){
        return accountHolderRepository.findById(id);
    }

    @Override
    public AccountHolder getAccountHolderByUsername(String username) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername(username);
        if(accountHolder.isPresent()){
            return accountHolder.get();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no accounts associated");
        }

    }


}
