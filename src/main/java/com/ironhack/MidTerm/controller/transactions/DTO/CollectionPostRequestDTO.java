package com.ironhack.MidTerm.controller.transactions.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CollectionPostRequestDTO {
    @NotNull
    private String currency;
    @Positive
    private Double amount;
    @NotNull
    private Long accountId;
    @NotNull
    private String secretKey;



    public CollectionPostRequestDTO() {
    }

    public CollectionPostRequestDTO(@NotNull String currency, @Positive Double amount, @NotNull Long accountId, @NotNull String secretKey) {
        this.currency = currency;
        this.amount = amount;
        this.accountId = accountId;
        this.secretKey = secretKey;
    }

//  Getters and setters
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

