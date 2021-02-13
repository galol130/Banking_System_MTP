package com.ironhack.MidTerm.service.transaction.interfaces;

import com.ironhack.MidTerm.controller.transactions.DTO.*;
import com.ironhack.MidTerm.model.transactions.Transaction;
import com.ironhack.MidTerm.security.CustomUserDetails;

import java.util.List;
import java.util.Map;


public interface ITransactionService {

    TransferGetRequestDTO transferBetweenAccounts(Long id, CustomUserDetails customUserDetails, TransferPostRequestDTO postRequestDTO);

    CollectionGetRequestDTO collectFunds(String hashedKey, CollectionPostRequestDTO requestDTO);

    DepositGetRequestDTO depositFunds(String hashedKey, DepositPostRequestDTO requestDTO);

    Map<String, List> getTransactionsByThirdPartyId(Long id);

    Map<String, List> getTransactionsByAccountHolderId(Long id, CustomUserDetails customUserDetails);

    Map<String, List> getTransactionsByOriginAccountId(Long id, CustomUserDetails customUserDetails);

    Map<String, List> getTransactionsByDestinationAccountId(Long id, CustomUserDetails customUserDetails);

    TransferGetRequestDTO convertTransferToDTO(Transaction trx);

    CollectionGetRequestDTO convertCollectionToDTO(Transaction trx);

    DepositGetRequestDTO convertDepositToDTO(Transaction trx);
}
