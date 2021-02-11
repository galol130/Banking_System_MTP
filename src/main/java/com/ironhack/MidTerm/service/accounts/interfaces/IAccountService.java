package com.ironhack.MidTerm.service.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.AccountBasicsGetRequestDTO;
import com.ironhack.MidTerm.model.accounts.Account;

import java.util.List;

public interface IAccountService {

    List<AccountBasicsGetRequestDTO> getAccountsWithBalance (String username);

    Object getAccountById(Account account);

    AccountBasicsGetRequestDTO convertAccountToDTO(Account account);
}
