package com.ironhack.MidTerm.controller.accounts.DTO;

import javax.validation.constraints.*;

public class CreditCardAccountCreationRequestDTO {
    @NotNull(message = "Accounts must have at least one owner!")
    private Long accountHolderId;
    private Long secondaryAccountHolderId;
    @Size(min = 3, max = 3, message = "Use the 3-characters identifier for Currency")
    private String balanceCurrency;
    @Min(value = 0, message = "Credit Card accounts cannot be opened with an initial balance below the Minimum Balance for this type")
    @Max(value= 1000000, message = "Due to internal policies, initial balance cannot be over 1.000.000. Contact Admin")
    private Double balanceAmount;
    @Size(min = 6, max = 20, message = "Your secret key must have between 6-20 characters")
    private String secretKey;
    @DecimalMin(value = "100", message = "Min Credit Limit allowed is 100. Default value is 100 ")
    @DecimalMax(value = "100000", message = "Max Credit Limit allowed is 100.000. Default value is 100 ")
    private Double creditLimit;
    @DecimalMin(value = "0.1", message = "Min Interest Rate allowed is 0.1. Default value is 0.2 ")
    @DecimalMax(value = "0.2", message = "Max Interest Rate allowed is 0.2. Default value is 0.2 ")
    private Double interestRate;



    public CreditCardAccountCreationRequestDTO() {
    }

    public CreditCardAccountCreationRequestDTO(@NotNull(message = "Accounts must have at least one owner!") Long accountHolderId, Long secondaryAccountHolderId, @Size(min = 3, max = 3, message = "Use the 3-characters identifier for Currency") String balanceCurrency, @Min(value = 100, message = "Savings accounts cannot be opened with an initial balance below the Minimum Balance for this type") @Max(value = 1000000, message = "Due to internal policies, initial balance cannot be over 1.000.000. Contact Admin") Double balanceAmount, @Size(min = 6, max = 20, message = "Your secret key must have between 6-20 characters") String secretKey, @DecimalMin(value = "100", message = "Min Credit Limit allowed is 100. Default value is 100 ") @DecimalMax(value = "100000", message = "Max Credit Limit allowed is 100.000. Default value is 100 ") Double creditLimit, @DecimalMin(value = "0.1", message = "Min Interest Rate allowed is 0.1. Default value is 0.2 ") @DecimalMax(value = "0.2", message = "Max Interest Rate allowed is 0.2. Default value is 0.2 ") Double interestRate) {
        this.accountHolderId = accountHolderId;
        this.secondaryAccountHolderId = secondaryAccountHolderId;
        this.balanceCurrency = balanceCurrency;
        this.balanceAmount = balanceAmount;
        this.secretKey = secretKey;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    //  Getters and setters
    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public Long getSecondaryAccountHolderId() {
        return secondaryAccountHolderId;
    }

    public void setSecondaryAccountHolderId(Long secondaryAccountHolderId) {
        this.secondaryAccountHolderId = secondaryAccountHolderId;
    }

    public String getBalanceCurrency() {
        return balanceCurrency;
    }

    public void setBalanceCurrency(String balanceCurrency) {
        this.balanceCurrency = balanceCurrency;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
