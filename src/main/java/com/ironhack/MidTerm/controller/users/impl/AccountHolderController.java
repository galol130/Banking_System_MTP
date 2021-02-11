package com.ironhack.MidTerm.controller.users.impl;

import com.ironhack.MidTerm.controller.users.interfaces.IAccountHolderController;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountHolderController implements IAccountHolderController {
    @Autowired
    private AccountHolderRepository accountHolderRepository;



}
