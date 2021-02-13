package com.ironhack.MidTerm.repository;

import com.ironhack.MidTerm.model.transactions.Transaction;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.model.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOriginAccountId(Long id);

    List<Transaction> findByDestinationAccountId(Long id);

    List<Transaction> findByOriginAccountHolder(AccountHolder accountHolder);

    List<Transaction> findByDestinationAccountHolder(AccountHolder accountHolder);

    List<Transaction> findByThirdParty(ThirdParty thirdParty);

    @Query(value = "" +
            "SELECT SUM(amount) FROM banking_system.transaction" +
            "WHERE time_stamp between DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 24 HOUR) AND CURRENT_TIMESTAMP()" +
            "AND origin_account_id = ?;" +
            "", nativeQuery = true)
    Double getSumFromTransactionsLast24h(Long accountOriginId);

    @Query(value = "" +
            "SELECT SUM(amount) FROM banking_system.transaction \n" +
            "WHERE origin_account_id = 3\n" +
            "GROUP BY DAYOFYEAR(time_stamp);" +
            "", nativeQuery = true)
    List<Double> getSumFromDailyTransactions(Long accountOriginId);
}
