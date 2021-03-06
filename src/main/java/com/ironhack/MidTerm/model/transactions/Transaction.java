package com.ironhack.MidTerm.model.transactions;

import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.model.users.ThirdParty;
import com.ironhack.MidTerm.model.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timeStamp;
    private String type;
    private String comments;

    @Embedded
    private Money funds;

    @ManyToOne
    @JoinColumn(name = "origin_user_id")
    private AccountHolder originAccountHolder;
    private Long originAccountId;
    private String originAccountType;

    @ManyToOne
    @JoinColumn(name = "destination_user_id")
    private AccountHolder destinationAccountHolder;
    private Long destinationAccountId;
    private String destinationAccountType;

    @ManyToOne
    @JoinColumn(name = "third_party_id")
    private ThirdParty thirdParty;


    public Transaction() {
    }

    //Constructor to be used for bank clients transfers
    public Transaction(LocalDateTime timeStamp, String type, String comments, Money funds, AccountHolder originAccountHolder, Long originAccountId, String originAccountType, AccountHolder destinationAccountHolder, Long destinationAccountId, String destinationAccountType) {
        this.timeStamp = timeStamp;
        this.type = type;
        this.comments = comments;
        this.funds = funds;
        this.originAccountHolder = originAccountHolder;
        this.originAccountId = originAccountId;
        this.originAccountType = originAccountType;
        this.destinationAccountHolder = destinationAccountHolder;
        this.destinationAccountId = destinationAccountId;
        this.destinationAccountType = destinationAccountType;
    }

    //Constructor for Third Parties Deposits
    public Transaction(LocalDateTime timeStamp, String comments, Money funds, ThirdParty thirdParty,Long destinationAccountId, String destinationAccountType, AccountHolder destinationAccountHolder) {
        this.timeStamp = timeStamp;
        this.type = "Deposit";
        this.comments = comments;
        this.funds = funds;
        this.thirdParty = thirdParty;
        this.destinationAccountHolder = destinationAccountHolder;
        this.destinationAccountId =  destinationAccountId;
        this.destinationAccountType = destinationAccountType;
    }

    //Constructor for Third Parties Collections
    public Transaction(LocalDateTime timeStamp, String type, String comments, Money funds,Long originAAccountId, String originAccountType, AccountHolder originAAccountHolder, ThirdParty thirdParty) {
        this.timeStamp = timeStamp;
        this.type = type;
        this.comments = comments;
        this.funds = funds;
        this.originAccountHolder = originAAccountHolder;
        this.originAccountId =  originAAccountId;
        this.originAccountType = originAccountType;
        this.thirdParty = thirdParty;
    }

    //  Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Money getFunds() {
        return funds;
    }

    public void setFunds(Money funds) {
        this.funds = funds;
    }

    public AccountHolder getOriginAccountHolder() {
        return originAccountHolder;
    }

    public void setOriginAccountHolder(AccountHolder originAccountHolder) {
        this.originAccountHolder = originAccountHolder;
    }

    public Long getOriginAccountId() {
        return originAccountId;
    }

    public void setOriginAccountId(Long originAccountId) {
        this.originAccountId = originAccountId;
    }

    public String getOriginAccountType() {
        return originAccountType;
    }

    public void setOriginAccountType(String originAccountType) {
        this.originAccountType = originAccountType;
    }

    public AccountHolder getDestinationAccountHolder() {
        return destinationAccountHolder;
    }

    public void setDestinationAccountHolder(AccountHolder destinationAccountHolder) {
        this.destinationAccountHolder = destinationAccountHolder;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public String getDestinationAccountType() {
        return destinationAccountType;
    }

    public void setDestinationAccountType(String destinationAccountType) {
        this.destinationAccountType = destinationAccountType;
    }

    public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }
}
