package com.ironhack.MidTerm.controller.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.SavingsAccountGetRequestDTO;
import com.ironhack.MidTerm.model.accounts.SavingsAccount;

import java.util.List;

public interface ISavingsAccountController {
    List<SavingsAccount> getSavingsAccounts();

    SavingsAccountGetRequestDTO createCheckingAccount(SavingsAccountCreationRequestDTO creationRequestDTO);

}
