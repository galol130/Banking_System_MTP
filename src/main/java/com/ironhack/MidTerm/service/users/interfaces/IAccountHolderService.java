package com.ironhack.MidTerm.service.users.interfaces;

import com.ironhack.MidTerm.controller.users.DTO.AccountHolderCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.users.AccountHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountHolderService {

    Optional<AccountHolder> findAccountHolderById(Long id);

    AccountHolder getAccountHolderByUsername(String username);

    AccountHolderGetRequestDTO createAccountHolder(AccountHolderCreationRequestDTO creationRequestDTO);

    AccountHolderGetRequestDTO convertAccountHolderToDTO(AccountHolder accountHolder);

    List<Address> changePrimaryAddress(Long id, Address address);

    List<Address> changeSecondaryAddress(Long id, Address address);
}
