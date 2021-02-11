package com.ironhack.MidTerm.model.accounts;

import com.ironhack.MidTerm.enums.Status;
import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.AccountHolder;

import javax.crypto.SecretKey;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate startDate;
    private SecretKey secretKey;
    @Embedded
    private Money balance;

    @ManyToOne()
    @JoinColumn(name = "primary_account_owner_id")
    private AccountHolder primaryOwner;
    @ManyToOne()
    @JoinColumn(name = "secondary_account_owner_id")
    private AccountHolder secondaryOwner;

    @Enumerated(EnumType.STRING)
    private Status status;


    public Account() {
    }


    /**
     * Full constructor (two owners)
     **/
    public Account(SecretKey secretKey, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status) {
        this.startDate = LocalDate.now();
        this.secretKey = secretKey;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
    }

    /**
     * Constructor with only one owner
     **/
    public Account(SecretKey secretKey, Money balance, AccountHolder primaryOwner, Status status) {
        this.startDate = LocalDate.now();
        this.secretKey = secretKey;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
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

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
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

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
