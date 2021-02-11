package com.ironhack.MidTerm.controller.accounts.DTO;

import javax.validation.constraints.*;
import java.util.UUID;


public class CheckingAccountCreationRequestDTO {
    @NotNull(message = "Accounts must have at least one owner!")
    private Long accountHolderId;
    private Long secondaryAccountHolderId;
    @Size(min = 3, max = 3, message = "Use the 3-characters identifier for Currency")
    private String balanceCurrency;
    @Min(value = 250, message = "Checking accounts cannot be opened with an initial balance below the Minimum Balance for this type")
    @Max(value= 1000000, message = "Due to internal policies, initial balance cannot be over 1.000.000. Contact Admin")
    private Double balanceAmount;
    @Size(min = 6, max = 20, message = "Your secret key must have between 6-20 characters")
    private String secretKey;


    public CheckingAccountCreationRequestDTO() {
    }


    public CheckingAccountCreationRequestDTO(@NotNull(message = "Accounts must have at least one owner!") Long accountHolderId, Long secondaryAccountHolderId, @Size(min = 3, max = 3, message = "Use the 3-characters identifier for Currency") String balanceCurrency, @Min(value = 250, message = "Checking accounts cannot be opened with an initial balance below the Minimum Balance for this type") @Max(value = 1000000, message = "Due to internal policies, initial balance cannot be over 1.000.000. Contact Admin") Double balanceAmount, @Size(min = 6, max = 20, message = "Your secret key must have between 6-20 characters") String secretKey) {
        this.accountHolderId = accountHolderId;
        this.secondaryAccountHolderId = secondaryAccountHolderId;
        this.balanceCurrency = balanceCurrency;
        this.balanceAmount = balanceAmount;
        this.secretKey = secretKey;
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
}
