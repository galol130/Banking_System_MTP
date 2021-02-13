package com.ironhack.MidTerm.service.transaction.impl;

import com.ironhack.MidTerm.controller.transactions.DTO.*;
import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.accounts.Account;
import com.ironhack.MidTerm.model.transactions.Transaction;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.model.users.ThirdParty;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.repository.ThirdPartyRepository;
import com.ironhack.MidTerm.repository.TransactionRepository;
import com.ironhack.MidTerm.security.CustomUserDetails;
import com.ironhack.MidTerm.service.transaction.interfaces.ITransactionService;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.service.users.interfaces.IThirdPartyService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import com.ironhack.MidTerm.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private IAccountHolderService accountHolderService;

    @Autowired
    private IThirdPartyService thirdPartyService;


    //  Using personalId instead of owner's name, it's unique!
    @Override
    public TransferGetRequestDTO transferBetweenAccounts(Long id, CustomUserDetails customUserDetails, TransferPostRequestDTO postRequestDTO) {
        Transaction trx;
        Account originAccount;
        Account destinationAccount;
        AccountHolder originAccountHolder;
        AccountHolder destinationAccountHolder;
        LocalDateTime timeStamp = LocalDateTime.now();
        Money transferMoney = new Money(BigDecimal.valueOf(postRequestDTO.getAmount()), Currency.getInstance(postRequestDTO.getCurrency()));

        //Check if the account number exist and belongs to the actual user (primary or secondary).
        Optional<Account> accountOptional = accountRepository.findAccountById(id);
        if (accountOptional.isPresent()) {
            originAccount = accountOptional.get();
            if (originAccount.getPrimaryOwner().getUsername().equals(customUserDetails.getUsername())) {
                //Origin account is OK
            } else {
                if (originAccount.getSecondaryOwner() != null && originAccount.getSecondaryOwner().getUsername().equals(customUserDetails.getUsername())) {
                    //Origin account is OK
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account doesn't belong to active user");
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong origin account ID");
        }

        //check for fraud
        if(isFraudCase1(originAccount, timeStamp)) {
            originAccount.setStatus(Status.FROZEN);
            accountRepository.save(originAccount);
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests for the Origin account. Account has been frozen!");
        }
        if(isFraudCase2(originAccount, timeStamp)) {
            originAccount.setStatus(Status.FROZEN);
            accountRepository.save(originAccount);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The account has reached the daily limit. Contact Admin!");
        }

        //Check if the Origin account is ACTIVE
        if (!originAccount.getStatus().equals(Status.ACTIVE))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Origin Account is not Active");


        //Then whe check if the destination account exist, is no the same account and the owner is correct
        Optional<Account> optionalAccount = accountRepository.findAccountById(postRequestDTO.getDestinationAccountId());
        if (optionalAccount.isPresent()) {
            destinationAccount = optionalAccount.get();
            if (!originAccount.getId().equals(destinationAccount.getId())) {
                if (destinationAccount.getPrimaryOwner().getPersonalId().equals(postRequestDTO.getAccountOwnerPersonalID())
                        || destinationAccount.getSecondaryOwner().getPersonalId().equals(postRequestDTO.getAccountOwnerPersonalID())) {
                    //Destination account is OK
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner provided does not match");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Origin and destination account are the same");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination account does not exist");
        }
        //Check if the Destination account is ACTIVE
        if (!destinationAccount.getStatus().equals(Status.ACTIVE))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination Account is not Active");

        //Check if the origin account currency is the same
        if (!originAccount.getBalance().getCurrency().equals(transferMoney.getCurrency())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The origin account is in a different currency");
        }

        //Check if the balance is enough for the transaction
        if (originAccount.getBalance().getAmount().doubleValue() < transferMoney.getAmount().doubleValue()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        //Check if the destination account currency is the same
        if (!destinationAccount.getBalance().getCurrency().equals(transferMoney.getCurrency())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The destination account is in a different currency");
        }

        //Balance modifications in both accounts
        originAccount.getBalance().decreaseAmount(transferMoney);
        destinationAccount.getBalance().increaseAmount(transferMoney);


        originAccountHolder = accountHolderService.getAccountHolderByUsername(customUserDetails.getUsername());
        destinationAccountHolder = accountHolderService.getAccountHolderByUsername(destinationAccount.getPrimaryOwner().getUsername());

        trx = new Transaction(
                timeStamp,
                "transfer",
                "transfer between account holders",
                transferMoney,
                originAccountHolder,
                originAccount.getId(),
                originAccount.getClass().getSimpleName(),
                destinationAccountHolder,
                destinationAccount.getId(),
                destinationAccount.getClass().getSimpleName()
        );


        //Data persistence in Database
        accountRepository.saveAll(List.of(originAccount, destinationAccount));
        transactionRepository.save(trx);

        return convertTransferToDTO(trx);
    }

    @Override
    public CollectionGetRequestDTO collectFunds(String hashedKey, CollectionPostRequestDTO requestDTO) {
        LocalDateTime timeStamp = LocalDateTime.now();
        ThirdParty thirdParty = null;

        //First validate the Third Party hashedKey
        List<ThirdParty> thirdPartyList = thirdPartyRepository.findAll();
        if (thirdPartyList.size() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Third Parties in the Database");
        }
        boolean exist = false;
        for (ThirdParty tp : thirdPartyList) {
            if (PasswordUtil.verifyPassword(hashedKey, tp.getHashedKey())) {
                exist = true;
                thirdParty = tp;
                break;
            }
        }
        if (!exist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hashed Key does not match any one in Database");
        }

        //Then get the account by ID, validate the secretKey and the Status
        Optional<Account> accountOptional = accountRepository.findAccountById(requestDTO.getAccountId());
        if (accountOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account ID provided does not exist");
        Account account = accountOptional.get();
        //To check the Secret Key, must convert the String from the DTO into a SK and then compare with the one in the DB
        SecretKey inputSecretKey = EncryptorUtil.createSecretKey(requestDTO.getSecretKey());
        if (!inputSecretKey.equals(account.getSecretKey()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret key provided does not match Account's SK");
        if (!account.getStatus().equals(Status.ACTIVE))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is not Active");

        //check for fraud
        if(isFraudCase1(account, timeStamp)) {
            account.setStatus(Status.FROZEN);
            accountRepository.save(account);
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests for the account. Account has been frozen!");
        }
        if(isFraudCase2(account, timeStamp)) {
            account.setStatus(Status.FROZEN);
            accountRepository.save(account);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The account has reached the daily limit. Contact Admin!");
        }



        //Then transaction is generated
        Money funds = new Money(BigDecimal.valueOf(requestDTO.getAmount()), Currency.getInstance(requestDTO.getCurrency()));
        Transaction trx = new Transaction(
                timeStamp,
                "Collection",
                "Collection made by a Third Party",
                funds,
                account.getId(),
                account.getClass().getSimpleName(),
                account.getPrimaryOwner(),
                thirdParty
        );

        //Funds are deducted from the account
        account.getBalance().decreaseAmount(funds);

        //Database persistence
        transactionRepository.save(trx);
        accountRepository.save(account);

        return convertCollectionToDTO(trx);
    }

    @Override
    public DepositGetRequestDTO depositFunds(String hashedKey, DepositPostRequestDTO requestDTO) {
        //No Fraud checking when receiving money...
        LocalDateTime timeStamp = LocalDateTime.now();
        ThirdParty thirdParty = null;

        //First validate the Third Party hashedKey
        List<ThirdParty> thirdPartyList = thirdPartyRepository.findAll();
        if (thirdPartyList.size() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Third Parties in the Database");
        }
        boolean exist = false;
        for (ThirdParty tp : thirdPartyList) {
            if (PasswordUtil.verifyPassword(hashedKey, tp.getHashedKey())) {
                exist = true;
                thirdParty = tp;
                break;
            }
        }
        if (!exist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hashed Key does not match any one in Database");
        }

        //Then get the account by ID
        Optional<Account> accountOptional = accountRepository.findAccountById(requestDTO.getAccountId());
        if (accountOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account ID provided does not exist");
        Account account = accountOptional.get();
        //Validate if the account is Active
        if (!account.getStatus().equals(Status.ACTIVE))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is not Active");


        //Then transaction is generated
        Money funds = new Money(BigDecimal.valueOf(requestDTO.getAmount()), Currency.getInstance(requestDTO.getCurrency()));
        Transaction trx = new Transaction(
                timeStamp,
                "Deposit made by a Third Party",
                funds,
                thirdParty,
                account.getId(),
                account.getClass().getSimpleName(),
                account.getPrimaryOwner()
        );

        //Funds are added to the account
        account.getBalance().increaseAmount(funds);

        //Database persistence
        transactionRepository.save(trx);
        accountRepository.save(account);

        return convertDepositToDTO(trx);
    }

    @Override
    public Map<String, List> getTransactionsByThirdPartyId(Long id) {
        List<DepositGetRequestDTO> depositList = new ArrayList<>();
        List<CollectionGetRequestDTO> collectionList = new ArrayList<>();
        Map<String, List> resultMap = new HashMap<>();

        //First check if the ID corresponds to a Third Party
        Optional<ThirdParty> thirdPartyOptional = thirdPartyRepository.findById(id);
        if(thirdPartyOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID does not match any Third Party");
        ThirdParty thirdParty = thirdPartyOptional.get();

        List<Transaction> transactionList = transactionRepository.findByThirdParty(thirdParty);
        if (transactionList.size() > 0) {
            for (Transaction trx : transactionList) {
                if (trx.getType().equalsIgnoreCase("deposit"))
                    depositList.add(convertDepositToDTO(trx));
                if (trx.getType().equalsIgnoreCase("collection"))
                    collectionList.add(convertCollectionToDTO(trx));
            }
            resultMap.put("deposit", depositList);
            resultMap.put("collection", collectionList);
            return resultMap;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions involving that Third Party");
        }
    }

    @Override
    public Map<String, List> getTransactionsByAccountHolderId(Long id, CustomUserDetails customUserDetails) {
        List<TransferGetRequestDTO> transferList = new ArrayList<>();
        List<DepositGetRequestDTO> depositList = new ArrayList<>();
        List<CollectionGetRequestDTO> collectionList = new ArrayList<>();
        Map<String, List> resultMap = new HashMap<>();

        boolean returnResult = false;

        //First check if the Account Holder corresponds to an existing account.
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(id);
        if (accountHolderOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID does not match any account holder");
        AccountHolder accountHolder = accountHolderOptional.get();

//      Check if the admin is the one sending the request
        GrantedAuthority adminRole = new SimpleGrantedAuthority("ROLE_ADMIN");
        if (customUserDetails.getAuthorities().contains(adminRole)) {
            returnResult = true;

//      If not the admin, first need to check if the account holder matches the actual user
        } else {
            if (accountHolder.getUsername().equals(customUserDetails.getUsername())) {
                returnResult = true;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active user does not match the account holder provided");
            }
        }

        List<Transaction> transactionList = transactionRepository.findByOriginAccountHolder(accountHolder);
        transactionList.addAll(transactionRepository.findByDestinationAccountHolder(accountHolder));
        if (transactionList.size() > 0 && returnResult) {
            for (Transaction trx : transactionList) {
                if (trx.getType().equalsIgnoreCase("transfer"))
                    transferList.add(convertTransferToDTO(trx));
                if (trx.getType().equalsIgnoreCase("deposit"))
                    depositList.add(convertDepositToDTO(trx));
                if (trx.getType().equalsIgnoreCase("collection"))
                    collectionList.add(convertCollectionToDTO(trx));
            }
            resultMap.put("transfer", transferList);
            resultMap.put("deposit", depositList);
            resultMap.put("collection", collectionList);
            return resultMap;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions involving that account holder");
        }
    }

    @Override
    public Map<String, List> getTransactionsByOriginAccountId(Long id, CustomUserDetails customUserDetails) {
        List<TransferGetRequestDTO> transferList = new ArrayList<>();
        List<DepositGetRequestDTO> depositList = new ArrayList<>();
        List<CollectionGetRequestDTO> collectionList = new ArrayList<>();
        Map<String, List> resultMap = new HashMap<>();

        boolean returnResult = false;

        //First check if the ID corresponds to an existing account.
        Optional<Account> accountOptional = accountRepository.findAccountById(id);
        if (accountOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID does not match any account");
        Account account = accountOptional.get();

//      Check if the admin is the one sending the request
        GrantedAuthority adminRole = new SimpleGrantedAuthority("ROLE_ADMIN");
        if (customUserDetails.getAuthorities().contains(adminRole)) {
            returnResult = true;

//      If not the admin, first need to check if the user is the owner of the account
        } else {
            String username = customUserDetails.getUsername();
            AccountHolder accountHolder = accountHolderService.getAccountHolderByUsername(username);
            if (account.getPrimaryOwner().equals(accountHolder)) {
                returnResult = true;
            } else if (account.getSecondaryOwner() != null && account.getSecondaryOwner().equals(accountHolder)) {
                returnResult = true;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active user is not the owner of that account");
            }
        }

        List<Transaction> transactionList = transactionRepository.findByOriginAccountId(id);
        if (transactionList.size() > 0 && returnResult) {
            for (Transaction trx : transactionList) {
                if (trx.getType().equalsIgnoreCase("transfer"))
                    transferList.add(convertTransferToDTO(trx));
                if (trx.getType().equalsIgnoreCase("deposit"))
                    depositList.add(convertDepositToDTO(trx));
                if (trx.getType().equalsIgnoreCase("collection"))
                    collectionList.add(convertCollectionToDTO(trx));
            }
            resultMap.put("transfer", transferList);
            resultMap.put("deposit", depositList);
            resultMap.put("collection", collectionList);
            return resultMap;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions originated by that account");
        }
    }

    @Override
    public Map<String, List> getTransactionsByDestinationAccountId(Long id, CustomUserDetails customUserDetails) {
        List<TransferGetRequestDTO> transferList = new ArrayList<>();
        List<DepositGetRequestDTO> depositList = new ArrayList<>();
        List<CollectionGetRequestDTO> collectionList = new ArrayList<>();
        Map<String, List> resultMap = new HashMap<>();

        boolean returnResult = false;

        //First check if the ID corresponds to an existing account.
        Optional<Account> accountOptional = accountRepository.findAccountById(id);
        if (accountOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID does not match any account");
        Account account = accountOptional.get();

//      Check if the admin is the one sending the request
        GrantedAuthority adminRole = new SimpleGrantedAuthority("ROLE_ADMIN");
        if (customUserDetails.getAuthorities().contains(adminRole)) {
            returnResult = true;


//      If not the admin, first need to check if the user is the owner of the account
        } else {
            String username = customUserDetails.getUsername();
            AccountHolder accountHolder = accountHolderService.getAccountHolderByUsername(username);
            if (account.getPrimaryOwner().equals(accountHolder)) {
                returnResult = true;
            } else if (account.getSecondaryOwner() != null && account.getSecondaryOwner().equals(accountHolder)) {
                returnResult = true;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active user is not the owner of that account");
            }
        }


        List<Transaction> transactionList = transactionRepository.findByDestinationAccountId(id);
        if (transactionList.size() > 0 && returnResult) {
            for (Transaction trx : transactionList) {
                if (trx.getType().equalsIgnoreCase("transfer"))
                    transferList.add(convertTransferToDTO(trx));
                if (trx.getType().equalsIgnoreCase("deposit"))
                    depositList.add(convertDepositToDTO(trx));
                if (trx.getType().equalsIgnoreCase("collection"))
                    collectionList.add(convertCollectionToDTO(trx));
            }
            resultMap.put("transfer", transferList);
            resultMap.put("deposit", depositList);
            resultMap.put("collection", collectionList);
            return resultMap;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions with that account as a destination");
        }
    }


    public TransferGetRequestDTO convertTransferToDTO(Transaction trx) {
        return new TransferGetRequestDTO(
                trx.getId(),
                trx.getTimeStamp(),
                trx.getType(),
                trx.getComments(),
                trx.getFunds(),
                accountHolderService.convertAccountHolderToDTO(trx.getOriginAccountHolder()),
                trx.getOriginAccountId(),
                trx.getOriginAccountType(),
                accountHolderService.convertAccountHolderToDTO(trx.getDestinationAccountHolder()),
                trx.getDestinationAccountId(),
                trx.getDestinationAccountType()
        );
    }

    public CollectionGetRequestDTO convertCollectionToDTO(Transaction trx) {
        return new CollectionGetRequestDTO(
                trx.getId(),
                trx.getTimeStamp(),
                trx.getType(),
                trx.getComments(),
                trx.getFunds(),
                accountHolderService.convertAccountHolderToDTO(trx.getOriginAccountHolder()),
                trx.getOriginAccountId(),
                trx.getOriginAccountType(),
                thirdPartyService.convertThirdPartyToDTO(trx.getThirdParty())
        );
    }

    public DepositGetRequestDTO convertDepositToDTO(Transaction trx) {
        return new DepositGetRequestDTO(
                trx.getId(),
                trx.getTimeStamp(),
                trx.getType(),
                trx.getComments(),
                trx.getFunds(),
                accountHolderService.convertAccountHolderToDTO(trx.getDestinationAccountHolder()),
                trx.getDestinationAccountId(),
                trx.getDestinationAccountType(),
                thirdPartyService.convertThirdPartyToDTO(trx.getThirdParty())
        );
    }

    private boolean isFraudCase1(Account account, LocalDateTime timeStamp){
        LocalTime actualTimeStamp =  timeStamp.toLocalTime();
        List<Transaction> transactionList = transactionRepository.findByOriginAccountId(account.getId());

        for (Transaction trx:transactionList) {
            LocalTime trxTimeStamp =  trx.getTimeStamp().toLocalTime();
            if(trxTimeStamp.until(actualTimeStamp, ChronoUnit.MILLIS) < 1000)
                return true;
        }
        return false;
    }

    private boolean isFraudCase2(Account account, LocalDateTime timeStamp){
        //Gets the sum of the account's transactions in the las 24h (This is why we do it only for Origin accounts)
        BigDecimal last24Sum = BigDecimal.valueOf(transactionRepository.getSumFromTransactionsLast24h(account.getId()));
        //Gets the daily sum of the account's transactions
        List<Double> dailySumDoubles = transactionRepository.getSumFromDailyTransactions(account.getId());
        List<BigDecimal> dailySum = dailySumDoubles.stream().map(BigDecimal::valueOf).collect(Collectors.toList());

        //Comparison of each day sum (increased 150%) to the sum of the last 24h transaction's amount.
        for (BigDecimal daySum:dailySum) {
            if(daySum.multiply(BigDecimal.valueOf(1.5)).compareTo(last24Sum) < 0)
                return true;
        }
        return false;
    }
}
