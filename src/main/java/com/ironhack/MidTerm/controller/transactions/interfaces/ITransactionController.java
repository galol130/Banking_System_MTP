package com.ironhack.MidTerm.controller.transactions.interfaces;

import com.ironhack.MidTerm.controller.transactions.DTO.*;
import com.ironhack.MidTerm.model.transactions.Transaction;
import com.ironhack.MidTerm.security.CustomUserDetails;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITransactionController {
    List<Transaction> getAllTransactions();

    Optional<Transaction> getTransactionById(Long id);

    Map<String, List> getTransactionByThirdPartyId(Long id);

    Map<String, List> getTransactionsByAccountHolderId(Long id, CustomUserDetails customUserDetails);

    Map<String, List> getTransactionsByOriginAccountId(Long id, CustomUserDetails customUserDetails);

    Map<String, List> getTransactionsByDestinationAccountId(Long id, CustomUserDetails customUserDetails);

    TransferGetRequestDTO transfer(Long id, CustomUserDetails customUserDetails, TransferPostRequestDTO postRequestDTO);

    CollectionGetRequestDTO thirdPartyCollection(String hashedKey, CollectionPostRequestDTO requestDTO);

    DepositGetRequestDTO thirdPartyDeposit(String hashedKey, DepositPostRequestDTO requestDTO);
}
