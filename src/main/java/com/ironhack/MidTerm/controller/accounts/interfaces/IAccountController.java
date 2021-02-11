package com.ironhack.MidTerm.controller.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.UserAccountBalanceDTO;
import com.ironhack.MidTerm.model.accounts.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IAccountController {

    List<Account> getAccounts();

    Object getAccountById(Long id, Authentication authentication);

    List<UserAccountBalanceDTO> getAccountsBalance(Authentication authentication);

    UserAccountBalanceDTO getAccountBasicDetails(Long id);

    UserAccountBalanceDTO updateAccountBalance(Long id, Double amountInput);

    UserAccountBalanceDTO updateAccountBalance(Long id, String statusInput);


}
