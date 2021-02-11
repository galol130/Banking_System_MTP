package com.ironhack.MidTerm.controller.accounts.DTO;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;

import javax.persistence.*;
import java.time.LocalDate;

public class UserAccountBalanceDTO {
    private Long id;
    private LocalDate startDate;
    @Embedded
    private Money balance;

    @Enumerated(EnumType.STRING)
    private Status status;


    public UserAccountBalanceDTO(LocalDate startDate, Money balance, Status status) {
        this.startDate = startDate;
        this.balance = balance;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
