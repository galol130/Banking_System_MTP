package com.ironhack.MidTerm.controller.transactions.DTO;

import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.model.Money;
import java.time.LocalDateTime;

public class TransferGetRequestDTO {
    private Long id;
    private LocalDateTime timeStamp;
    private String type;
    private String comments;
    private Money funds;
    private AccountHolderGetRequestDTO originAccountHolder;
    private Long originAccountId;
    private String originAccountType;
    private AccountHolderGetRequestDTO destinationAccountHolder;
    private Long destinationAccountId;
    private String destinationAccountType;

    public TransferGetRequestDTO(Long id, LocalDateTime timeStamp, String type, String comments, Money funds, AccountHolderGetRequestDTO originAccountHolder, Long originAccountId, String originAccountType, AccountHolderGetRequestDTO destinationAccountHolder, Long destinationAccountId, String destinationAccountType) {
        this.id = id;
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

    public AccountHolderGetRequestDTO getOriginAccountHolder() {
        return originAccountHolder;
    }

    public void setOriginAccountHolder(AccountHolderGetRequestDTO originAccountHolder) {
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

    public AccountHolderGetRequestDTO getDestinationAccountHolder() {
        return destinationAccountHolder;
    }

    public void setDestinationAccountHolder(AccountHolderGetRequestDTO destinationAccountHolder) {
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
}
