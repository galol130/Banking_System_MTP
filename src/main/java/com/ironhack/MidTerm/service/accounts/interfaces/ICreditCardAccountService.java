package com.ironhack.MidTerm.service.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountGetRequestDTO;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;
import com.ironhack.MidTerm.model.users.AccountHolder;

public interface ICreditCardAccountService {

    CreditCardAccountGetRequestDTO createCreditCardAccount(CreditCardAccountCreationRequestDTO creationRequestDTO, AccountHolder accountHolder);
}
