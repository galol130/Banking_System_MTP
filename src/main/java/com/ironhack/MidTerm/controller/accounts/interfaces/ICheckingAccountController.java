package com.ironhack.MidTerm.controller.accounts.interfaces;

import com.ironhack.MidTerm.controller.accounts.DTO.CheckingAccountCreationRequestDTO;
import com.ironhack.MidTerm.model.accounts.CheckingAccount;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ICheckingAccountController {

    List<CheckingAccount> getCheckingAccounts();

    Object createCheckingAccount(@RequestBody @Valid CheckingAccountCreationRequestDTO creationRequestDTO);
}
