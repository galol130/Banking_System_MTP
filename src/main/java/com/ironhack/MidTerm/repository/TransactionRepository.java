package com.ironhack.MidTerm.repository;

import com.ironhack.MidTerm.model.transactions.Transaction;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.model.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOriginAccountId(Long id);

    List<Transaction> findByDestinationAccountId(Long id);

    List<Transaction> findByOriginAccountHolder(AccountHolder accountHolder);

    List<Transaction> findByDestinationAccountHolder(AccountHolder accountHolder);

    List<Transaction> findByThirdParty(ThirdParty thirdParty);
}
