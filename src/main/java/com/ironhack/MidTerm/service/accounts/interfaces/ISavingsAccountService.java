package com.ironhack.MidTerm.service.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountCreationRequestDTO;
import com.ironhack.MidTerm.model.accounts.SavingsAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;

public interface ISavingsAccountService {

    SavingsAccount createSavingsAccount(SavingsAccountCreationRequestDTO creationRequestDTO, AccountHolder accountHolder);
}
