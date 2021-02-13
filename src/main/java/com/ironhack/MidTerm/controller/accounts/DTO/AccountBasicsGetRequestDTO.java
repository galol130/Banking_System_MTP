package com.ironhack.MidTerm.controller.accounts.DTO;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;

import javax.persistence.*;
import java.time.LocalDate;

public class AccountBasicsGetRequestDTO {
    private Long id;
    private String type;
    private String primaryOwnerName;
    private LocalDate startDate;
    @Embedded
    private Money balance;

    @Enumerated(EnumType.STRING)
    private Status status;


    public AccountBasicsGetRequestDTO(Long id, String type, String primaryOwnerName, LocalDate startDate, Money balance, Status status) {
        this.id = id;
        this.type = type;
        this.primaryOwnerName = primaryOwnerName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrimaryOwnerName() {
        return primaryOwnerName;
    }

    public void setPrimaryOwnerName(String primaryOwnerName) {
        this.primaryOwnerName = primaryOwnerName;
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
