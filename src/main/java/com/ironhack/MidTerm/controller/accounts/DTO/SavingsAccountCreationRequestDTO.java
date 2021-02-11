package com.ironhack.MidTerm.controller.accounts.DTO;

import javax.validation.constraints.*;
import java.util.UUID;

public class SavingsAccountCreationRequestDTO {
    @NotNull(message = "Accounts must have at least one owner!")
    private UUID accountHolderId;
    @Null
    private UUID secondaryAccountHolderId;
    @Size(min = 3, max = 3, message = "Use the 3-characters identifier for Currency")
    private String balanceCurrency;
    @Min(value = 100, message = "Savings accounts cannot be opened with an initial balance below the Minimum Balance for this type")
    @Max(value= 1000000, message = "Due to internal policies, initial balance cannot be over 1.000.000. Contact Admin")
    private Double balanceAmount;
    @Min(value = 100, message = "Minimum balance is not allowed below 100. To use the Default value, type '0' ")
    private Double minimumBalance;
    @Size(min = 6, max = 20, message = "Your secret key must have between 6-20 characters")
    private String secretKey;
    @DecimalMax(value = "0.5", message = "Max interest rate allowed is 0.5. To use the Default value, type '0'")
    private Double interestRate;


    public SavingsAccountCreationRequestDTO() {
    }

    public SavingsAccountCreationRequestDTO(@NotNull(message = "Accounts must have at least one owner!") UUID accountHolderId, @Null UUID secondaryAccountHolderId, @Size(min = 3, max = 3, message = "Use the 3-characters identifier for Currency") String balanceCurrency, @Min(value = 100, message = "Savings accounts cannot be opened with an initial balance below the Minimum Balance for this type") @Max(value = 1000000, message = "Due to internal policies, initial balance cannot be over 1.000.000. Contact Admin") Double balanceAmount, @Min(value = 100, message = "Minimum balance is not allowed below 100. To use the Default value, type '0' ") Double minimumBalance, @Size(min = 6, max = 20, message = "Your secret key must have between 6-20 characters") String secretKey, @DecimalMax(value = "0.5", message = "Max interest rate allowed is 0.5. To use the Default value, type '0'") Double interestRate) {
        this.accountHolderId = accountHolderId;
        this.secondaryAccountHolderId = secondaryAccountHolderId;
        this.balanceCurrency = balanceCurrency;
        this.balanceAmount = balanceAmount;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.interestRate = interestRate;
    }

    //  Getters and setters
    public UUID getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(UUID accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public UUID getSecondaryAccountHolderId() {
        return secondaryAccountHolderId;
    }

    public void setSecondaryAccountHolderId(UUID secondaryAccountHolderId) {
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

    public Double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
