package com.ironhack.MidTerm.controller.accounts.DTO;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;

import java.time.LocalDate;

public class SavingsAccountGetRequestDTO {
    private Long id;
    private LocalDate startDate;
    private Money balance;
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    private Status status;
    private Money minimumBalance;
    private Double penaltyFee;
    private Double interestRate;

    public SavingsAccountGetRequestDTO() {
    }

    public SavingsAccountGetRequestDTO(Long id, LocalDate startDate, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status, Money minimumBalance, Double penaltyFee, Double interestRate) {
        this.id = id;
        this.startDate = startDate;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
        this.minimumBalance = minimumBalance;
        this.penaltyFee = penaltyFee;
        this.interestRate = interestRate;
    }

//  Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Double getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Double penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
