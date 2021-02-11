package com.ironhack.MidTerm.service.users.interfaces;

import com.ironhack.MidTerm.model.users.AccountHolder;

import java.util.Optional;
import java.util.UUID;

public interface IAccountHolderService {

    Optional<AccountHolder> findAccountHolderById(UUID id);

    AccountHolder getAccountHolderByUsername(String username);
}
