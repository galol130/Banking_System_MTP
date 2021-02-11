package com.ironhack.MidTerm.service.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.UserAccountBalanceDTO;
import com.ironhack.MidTerm.model.accounts.Account;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.accounts.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IAccountHolderService accountHolderService;


    public Object getAccountById(Account account){
        return convertAccountToDTO(account);
    }

    public List<UserAccountBalanceDTO> getAccountsWithBalance(String username) {
        List<UserAccountBalanceDTO> result = new ArrayList<>();
        AccountHolder accountHolder = accountHolderService.getAccountHolderByUsername(username);
        List<Account> accountList = accountRepository.findAllByPrimaryOwnerId(accountHolder.getId());
        accountList.addAll(accountRepository.findAllBySecondaryOwnerId(accountHolder.getId()));
        if (accountList.size() > 0) {
            for (Account acc : accountList) {
                result.add(convertAccountToDTO(acc));
            }
        }
        return result;
    }

    public UserAccountBalanceDTO convertAccountToDTO(Account account) {
        return new UserAccountBalanceDTO(account.getStartDate(), account.getBalance(), account.getStatus());
    }
}
