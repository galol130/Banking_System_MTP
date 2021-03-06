package com.ironhack.MidTerm.controller.users.interfaces;


import com.ironhack.MidTerm.controller.users.DTO.AccountHolderCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;

public interface IAccountHolderController {

    Object getIdByUsername(String username);

    AccountHolderGetRequestDTO createAccountHolder(AccountHolderCreationRequestDTO creationRequestDTO);
}
