package com.ironhack.MidTerm.service.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.UserAccountBalanceDTO;
import com.ironhack.MidTerm.model.accounts.Account;

import java.util.List;

public interface IAccountService {

    List<UserAccountBalanceDTO> getAccountsWithBalance (String username);

    Object getAccountById(Account account);

    UserAccountBalanceDTO convertAccountToDTO(Account account);
}
