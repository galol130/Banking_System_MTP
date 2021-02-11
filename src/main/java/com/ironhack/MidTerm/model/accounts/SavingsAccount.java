package com.ironhack.MidTerm.model.accounts;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;

import javax.crypto.SecretKey;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
public class SavingsAccount extends Account {
    private static final Money DEFAULT_minimumBalance = new Money(new BigDecimal("1000"));
    private static final Double DEFAULT_interestRate = 0.0025;
    private static final Double DEFAULT_penaltyFee = 40d;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "min_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "min_balance_amount")),
    })
    private Money minimumBalance;
    private Double penaltyFee;
    private Double interestRate;


    public SavingsAccount() {
    }

    //  If no minimumBalance and no interestRate are provided, DEFAULT are used. Use setters to further change this
    //  Two owners
    public SavingsAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, secondaryOwner, status);
        this.penaltyFee = DEFAULT_penaltyFee;
        this.minimumBalance = DEFAULT_minimumBalance;
        this.interestRate = DEFAULT_interestRate;
    }

    //  If no minimumBalance and no interestRate are provided, DEFAULT are used. Use setters to further change this
    //  One owner
    public SavingsAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, status);
        this.penaltyFee = DEFAULT_penaltyFee;
        this.minimumBalance = DEFAULT_minimumBalance;
        this.interestRate = DEFAULT_interestRate;
    }

    //  Full constructor
    public SavingsAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status, Money minimumBalance, Double interestRate) {
        super(secretKey, balance, primaryOwner, secondaryOwner, status);
        this.minimumBalance = minimumBalance;
        this.penaltyFee = DEFAULT_penaltyFee;
        this.interestRate = interestRate;
    }


    //  Getters and setters
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
