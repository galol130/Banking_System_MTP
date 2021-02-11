package com.ironhack.MidTerm.model.accounts;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;

import javax.crypto.SecretKey;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class CheckingAccount extends Account {
    private static final Money DEFAULT_minimumBalance = new Money(new BigDecimal("250"));
    private static final Double DEFAULT_monthlyMaintenanceFee = 12d;
    private static final Double DEFAULT_penaltyFee = 40d;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "min_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "min_balance_amount")),
    })
    private Money minimumBalance;
    private Double penaltyFee;
    private Double monthlyMaintenanceFee;


    public CheckingAccount() {
    }

    //  If no minimumBalance and no monthlyMaintenanceFee are provided, DEFAULT are used. Use setters to further change this
    //  Two owners
    public CheckingAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, secondaryOwner, status);
        this.penaltyFee = DEFAULT_penaltyFee;
        this.minimumBalance = DEFAULT_minimumBalance;
        this.monthlyMaintenanceFee = DEFAULT_monthlyMaintenanceFee;
    }

    //  If no minimumBalance and no monthlyMaintenanceFee are provided, DEFAULT are used. Use setters to further change this
    //  Only one owner
    public CheckingAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, Status status) {
        super(secretKey, balance, primaryOwner, status);
        this.penaltyFee = DEFAULT_penaltyFee;
        this.minimumBalance = DEFAULT_minimumBalance;
        this.monthlyMaintenanceFee = DEFAULT_monthlyMaintenanceFee;
    }

    //  Full constructor
    public CheckingAccount(Money balance, SecretKey secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status, Money minimumBalance, Double monthlyMaintenanceFee) {
        super(secretKey, balance, primaryOwner, secondaryOwner, status);
        this.minimumBalance = minimumBalance;
        this.penaltyFee = DEFAULT_penaltyFee;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
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

    public Double getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Double monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }


}
