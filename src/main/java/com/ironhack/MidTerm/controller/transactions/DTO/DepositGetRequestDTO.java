package com.ironhack.MidTerm.controller.transactions.DTO;

import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;
import com.ironhack.MidTerm.model.Money;

import java.time.LocalDateTime;

public class DepositGetRequestDTO {
    private Long id;
    private LocalDateTime timeStamp;
    private String type;
    private String comments;
    private Money funds;
    private AccountHolderGetRequestDTO beneficiaryAccountHolder;
    private Long accountId;
    private String accountType;

    private ThirdPartyGetRequestDTO fundsOrigin;

    public DepositGetRequestDTO() {
    }

    public DepositGetRequestDTO(Long id, LocalDateTime timeStamp, String type, String comments, Money funds, AccountHolderGetRequestDTO beneficiaryAccountHolder, Long accountId, String accountType, ThirdPartyGetRequestDTO fundsOrigin) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.type = type;
        this.comments = comments;
        this.funds = funds;
        this.beneficiaryAccountHolder = beneficiaryAccountHolder;
        this.accountId = accountId;
        this.accountType = accountType;
        this.fundsOrigin = fundsOrigin;
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

    public AccountHolderGetRequestDTO getBeneficiaryAccountHolder() {
        return beneficiaryAccountHolder;
    }

    public void setBeneficiaryAccountHolder(AccountHolderGetRequestDTO beneficiaryAccountHolder) {
        this.beneficiaryAccountHolder = beneficiaryAccountHolder;
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

    public ThirdPartyGetRequestDTO getFundsOrigin() {
        return fundsOrigin;
    }

    public void setFundsOrigin(ThirdPartyGetRequestDTO fundsOrigin) {
        this.fundsOrigin = fundsOrigin;
    }
}
