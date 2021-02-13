package com.ironhack.MidTerm.controller.transactions.impl;

import com.ironhack.MidTerm.controller.transactions.DTO.*;
import com.ironhack.MidTerm.controller.transactions.interfaces.ITransactionController;
import com.ironhack.MidTerm.model.transactions.Transaction;
import com.ironhack.MidTerm.repository.TransactionRepository;
import com.ironhack.MidTerm.security.CustomUserDetails;
import com.ironhack.MidTerm.service.transaction.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TransactionController implements ITransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ITransactionService transactionService;


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/transaction/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/transactions/third-party/{id}")
    public Map<String, List> getTransactionByThirdPartyId(@PathVariable Long id) {
        return transactionService.getTransactionsByThirdPartyId(id);
    }

    @Override
    @GetMapping(value = "/transactions/account-holder/{account-holder-id}")
    public Map<String, List> getTransactionsByAccountHolderId(
            @PathVariable(name = "account-holder-id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println("\t Hasta aquí llega");
        return transactionService.getTransactionsByAccountHolderId(id, customUserDetails);
    }

    // Returns ALL the transactions having this account as origin. Grouped by Transfer/Deposit/Collection
    @Override
    @GetMapping(value = "/transactions/origin-account/{id}")
    public Map<String, List> getTransactionsByOriginAccountId(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        return transactionService.getTransactionsByOriginAccountId(id, customUserDetails);
    }

    // Returns ALL the transactions having this account as destination. Grouped by Transfer/Deposit/Collection
    @Override
    @GetMapping(value = "/transactions/destination-account/{id}")
    public Map<String, List> getTransactionsByDestinationAccountId(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        return transactionService.getTransactionsByDestinationAccountId(id, customUserDetails);
    }

    //  Origin account is passed by the URI
    @Override
    @PostMapping(value = "/transaction/transfer-from-account/{account_id}")
    public TransferGetRequestDTO transfer(
            @PathVariable(name = "account_id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid TransferPostRequestDTO postRequestDTO) {

        return transactionService.transferBetweenAccounts(id, customUserDetails, postRequestDTO);
    }

    @Override
    @PostMapping(value = "/transaction/collect")
    public CollectionGetRequestDTO thirdPartyCollection(@RequestHeader("Third-Party-Key") String hashedKey, @RequestBody @Valid CollectionPostRequestDTO requestDTO) {
        return transactionService.collectFunds(hashedKey, requestDTO);
    }

    //For deposits the account secret key is not needed
    @Override
    @PostMapping(value = "/transaction/deposit")
    public DepositGetRequestDTO thirdPartyDeposit(@RequestHeader("Third-Party-Key") String hashedKey, @RequestBody @Valid DepositPostRequestDTO requestDTO) {
        return transactionService.depositFunds(hashedKey, requestDTO);
    }
}
