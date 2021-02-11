package com.ironhack.MidTerm.controller.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.DTO.AccountBasicsGetRequestDTO;
import com.ironhack.MidTerm.controller.accounts.interfaces.IAccountController;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.Account;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.security.CustomUserDetails;
import com.ironhack.MidTerm.service.accounts.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController implements IAccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IAccountService accountService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/accounts")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STANDARD_USER')")
    @GetMapping(value = "/account/{id}")
    public Object getAccountById(@PathVariable Long id,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        for (GrantedAuthority auth : customUserDetails.getAuthorities()) {
            System.out.println(auth.getAuthority());
        }
        Optional<Account> result = accountRepository.findAccountById(id);

        if (result.isPresent()) {
            if (customUserDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"))) {
                return result.get();
            } else {
                return accountService.getAccountById(result.get());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_STANDARD_USER')")
    @GetMapping(value = "/accounts/balance")
    public List<AccountBasicsGetRequestDTO> getAccountsBalance(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return accountService.getAccountsWithBalance(customUserDetails.getUsername());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/account/{id}/basic-details")
    public AccountBasicsGetRequestDTO getAccountBasicDetails(@PathVariable Long id) {
        Optional<Account> account = accountRepository.findAccountById(id);
        if (account.isPresent()) {
            return accountService.convertAccountToDTO(account.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account ID not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/account/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public AccountBasicsGetRequestDTO updateAccountBalance(@PathVariable Long id, @RequestParam(name = "amount") Double amountInput) {
        if (amountInput <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong balance amount");
        } else {
            Optional<Account> optionalAccount = accountRepository.findAccountById(id);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                Money balance = new Money(BigDecimal.valueOf(amountInput), account.getBalance().getCurrency());
                account.setBalance(balance);
                accountRepository.save(account);
                return accountService.convertAccountToDTO(account);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account ID not found");
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/account/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public AccountBasicsGetRequestDTO updateAccountStatus(@PathVariable Long id, @RequestParam(name = "status") String statusInput) {
        try {
            Status status = Status.valueOf(statusInput.trim().toUpperCase());
            Optional<Account> optionalAccount = accountRepository.findAccountById(id);
            if(optionalAccount.isPresent()){
                Account account = optionalAccount.get();
                account.setStatus(status);
                account = accountRepository.save(account);
                return accountService.convertAccountToDTO(account);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account ID not found");
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong status. Only 'Activate' and 'Freeze' are valid");
        }
    }


}
