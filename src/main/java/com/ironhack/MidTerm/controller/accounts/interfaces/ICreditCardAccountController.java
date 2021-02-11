package com.ironhack.MidTerm.controller.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountCreationRequestDTO;
import com.ironhack.MidTerm.controller.accounts.DTO.CreditCardAccountGetRequestDTO;
import com.ironhack.MidTerm.model.accounts.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountController {

    List<CreditCardAccount> getCreditCardAccounts();

    CreditCardAccountGetRequestDTO createCreditCardAccount(CreditCardAccountCreationRequestDTO creationRequestDTO);

}
