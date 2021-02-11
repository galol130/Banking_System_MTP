package com.ironhack.MidTerm.controller.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.AccountBasicsGetRequestDTO;
import com.ironhack.MidTerm.model.accounts.Account;
import com.ironhack.MidTerm.security.CustomUserDetails;

import java.util.List;

public interface IAccountController {

    List<Account> getAccounts();

    Object getAccountById(Long id, CustomUserDetails customUserDetails);

    List<AccountBasicsGetRequestDTO> getAccountsBalance(CustomUserDetails customUserDetails);

    AccountBasicsGetRequestDTO getAccountBasicDetails(Long id);

    AccountBasicsGetRequestDTO updateAccountBalance(Long id, Double amountInput);

    AccountBasicsGetRequestDTO updateAccountStatus(Long id, String statusInput);


}
