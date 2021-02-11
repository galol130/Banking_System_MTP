package com.ironhack.MidTerm.repository;

import com.ironhack.MidTerm.model.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
    Optional<AccountHolder> findByUsername(String username);
}
