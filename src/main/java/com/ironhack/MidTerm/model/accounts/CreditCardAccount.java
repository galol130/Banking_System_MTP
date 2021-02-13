package com.ironhack.MidTerm.model.accounts;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;

import javax.crypto.SecretKey;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDate;

@Entity
public class CreditCardAccount extends Account {
    private static final Double DEFAULT_creditLimit = 100d;
    private static final Double DEFAULT_interestRate = 0.2;
    private static final Double DEFAULT_penaltyFee = 40d;

    @DecimalMax("100000")
    private Double creditLimit;
    @DecimalMin("0.1")
    private Double interestRate;
    private Double penaltyFee;
    private LocalDate lastDateOfInterestCharge;


    public CreditCardAccount(){}

//  If no creditLimit and no interestRate are provided, DEFAULT are used. Use setters to further change this
//  Two owners
    public CreditCardAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, secondaryOwner, status);
        this.lastDateOfInterestCharge = LocalDate.now();
        this.creditLimit = DEFAULT_creditLimit;
        this.interestRate = DEFAULT_interestRate;
        this.penaltyFee = DEFAULT_penaltyFee;
    }

//  If no creditLimit and no interestRate are provided, DEFAULT are used. Use setters to further change this
//  One owner
    public CreditCardAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, status);
        this.lastDateOfInterestCharge = LocalDate.now();
        this.creditLimit = DEFAULT_creditLimit;
        this.interestRate = DEFAULT_interestRate;
        this.penaltyFee = DEFAULT_penaltyFee;
    }

//  Full constructor
    public CreditCardAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status, @DecimalMax("100000") Double creditLimit, @DecimalMin("0.1") Double interestRate) {
        super(secretKey, balance, primaryOwner, secondaryOwner, status);
        this.lastDateOfInterestCharge = LocalDate.now();
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.penaltyFee = DEFAULT_penaltyFee;
    }

    //  Getters and setters
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

    public Double getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Double penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public LocalDate getLastDateOfInterestCharge() {
        return lastDateOfInterestCharge;
    }

    public void setLastDateOfInterestCharge(LocalDate lastDateOfInterestCharge) {
        this.lastDateOfInterestCharge = lastDateOfInterestCharge;
    }
}
