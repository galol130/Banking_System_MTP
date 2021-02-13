package com.ironhack.MidTerm.controller.transactions.DTO;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferPostRequestDTO {
    @NotNull
    private Long destinationAccountId;
    @NotEmpty
    private String accountOwnerPersonalID;
    @Length(min = 3, max = 3, message = "Use the international 3-letter currency acronym please")
    private String currency;
    @NotNull
    @Positive
    private Double amount;

    public TransferPostRequestDTO() {
    }

    public TransferPostRequestDTO(@NotNull Long destinationAccountId, @NotEmpty String accountOwnerPersonalID, @Length(min = 3, max = 3, message = "Use the international 3-letter currency acronym please") String currency, @NotNull Double amount) {
        this.destinationAccountId = destinationAccountId;
        this.accountOwnerPersonalID = accountOwnerPersonalID;
        this.currency = currency;
        this.amount = amount;
    }

//  Getters and setters
    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public String getAccountOwnerPersonalID() {
        return accountOwnerPersonalID;
    }

    public void setAccountOwnerName(String accountOwnerPersonalID) {
        this.accountOwnerPersonalID = accountOwnerPersonalID;
    }

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
}