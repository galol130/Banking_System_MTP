package com.ironhack.MidTerm.repository;

import com.ironhack.MidTerm.model.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountById(Long id);

    List<Account> findAllByPrimaryOwnerId(UUID id);

    List<Account> findAllBySecondaryOwnerId(UUID id);
}
