package com.ironhack.MidTerm.model.transactions;

import com.ironhack.MidTerm.model.Money;
import com.ironhack.MidTerm.model.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime timeStamp;
    private String type;
    private String comments;

    @Embedded
    private Money funds;

    @ManyToOne
    @JoinColumn(name = "origin_user_id")
    private User originUser;

    @ManyToOne
    @JoinColumn(name = "destination_user_id")
    private User destinationUser;


    public Transaction() {
    }

    //  Only Constructor to be used
    public Transaction(String type, Money funds, User originUser, User destinationUser, String comments) {
        this.timeStamp = LocalDateTime.now();
        this.type = type;
        this.funds = funds;
        this.originUser = originUser;
        this.destinationUser = destinationUser;
        this.comments = comments;
    }


    //  Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Money getFunds() {
        return funds;
    }

    public void setFunds(Money funds) {
        this.funds = funds;
    }

    public User getOriginUser() {
        return originUser;
    }

    public void setOriginUser(User originUser) {
        this.originUser = originUser;
    }

    public User getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(User destinationUser) {
        this.destinationUser = destinationUser;
    }
}
