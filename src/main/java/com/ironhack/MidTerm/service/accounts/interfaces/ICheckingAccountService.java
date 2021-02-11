package com.ironhack.MidTerm.service.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountGetRequestDTO;
import com.ironhack.MidTerm.model.accounts.CheckingAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;

public interface ICheckingAccountService {
    CheckingAccountGetRequestDTO createAccount(CheckingAccountCreationRequestDTO creationRequestDTO, AccountHolder primaryAccountHolder);
}
