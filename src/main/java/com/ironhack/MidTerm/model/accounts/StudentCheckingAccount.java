package com.ironhack.MidTerm.model.accounts;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;

import javax.crypto.SecretKey;
import javax.persistence.Entity;

@Entity
public class StudentCheckingAccount extends Account {
    private static final Double DEFAULT_penaltyFee = 40d;

    private Double penaltyFee;


    public StudentCheckingAccount(){}

//  Full constructor
//  Two owners
    public StudentCheckingAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, secondaryOwner, status);
        this.penaltyFee = DEFAULT_penaltyFee;
    }

//  Full constructor
//  One owner
    public StudentCheckingAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, status);
        this.penaltyFee = DEFAULT_penaltyFee;
    }

//  Getters and setters
    public Double getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Double penaltyFee) {
        this.penaltyFee = penaltyFee;
    }
}
