package com.ironhack.MidTerm.controller.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountController {

    List<CreditCardAccount> getCreditCardAccounts();

    CreditCardAccount createCreditCardAccount(CreditCardAccountCreationRequestDTO creationRequestDTO);

}
