package com.ironhack.MidTerm.service.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;

public interface ICreditCardAccountService {

    CreditCardAccount createCreditCardAccount(CreditCardAccountCreationRequestDTO creationRequestDTO, AccountHolder accountHolder);
}
