package com.ironhack.MidTerm.controller.accounts.DTO;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;

import java.time.LocalDate;

//  DTO used for both Checking and Student Checking Accounts
public class CheckingAccountGetRequestDTO {
    private String type;
    private Long id;
    private LocalDate startDate;
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    private Money balance;
    private Money minimumBalance;
    private Double monthlyMaintenanceFee;
    private Double penaltyFee;
    private Status status;


    public CheckingAccountGetRequestDTO() {

    }

//  Constructor for Checking Account
    public CheckingAccountGetRequestDTO(String type, Long id, LocalDate startDate, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, Double monthlyMaintenanceFee, Double penaltyFee, Status status) {
        this.type = type;
        this.id = id;
        this.startDate = startDate;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.penaltyFee = penaltyFee;
        this.status = status;
    }

//  Constructor for Student Checking Account
    public CheckingAccountGetRequestDTO(String type, Long id, LocalDate startDate, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Double penaltyFee, Status status) {
        this.type = type;
        this.id = id;
        this.startDate = startDate;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.balance = balance;
        this.penaltyFee = penaltyFee;
        this.status = status;
    }

    //  Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Double getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Double monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public Double getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Double penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
