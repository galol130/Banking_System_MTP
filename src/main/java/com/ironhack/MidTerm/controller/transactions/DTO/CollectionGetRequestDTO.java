package com.ironhack.MidTerm.controller.transactions.DTO;

import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;
import com.ironhack.MidTerm.model.Money;

import java.time.LocalDateTime;

public class CollectionGetRequestDTO {
    private Long id;
    private LocalDateTime timeStamp;
    private String type;
    private String comments;
    private Money funds;
    private AccountHolderGetRequestDTO AccountHolder;
    private Long accountId;
    private String accountType;

    private ThirdPartyGetRequestDTO collectingEntity;

    public CollectionGetRequestDTO() {

    }

    public CollectionGetRequestDTO(Long id, LocalDateTime timeStamp, String type, String comments, Money funds, AccountHolderGetRequestDTO accountHolder, Long accountId, String accountType, ThirdPartyGetRequestDTO collectingEntity) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.type = type;
        this.comments = comments;
        this.funds = funds;
        AccountHolder = accountHolder;
        this.accountId = accountId;
        this.accountType = accountType;
        this.collectingEntity = collectingEntity;
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

    public AccountHolderGetRequestDTO getAccountHolder() {
        return AccountHolder;
    }

    public void setAccountHolder(AccountHolderGetRequestDTO accountHolder) {
        AccountHolder = accountHolder;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public ThirdPartyGetRequestDTO getCollectingEntity() {
        return collectingEntity;
    }

    public void setCollectingEntity(ThirdPartyGetRequestDTO collectingEntity) {
        this.collectingEntity = collectingEntity;
    }
}
