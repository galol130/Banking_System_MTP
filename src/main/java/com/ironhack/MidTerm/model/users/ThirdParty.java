package com.ironhack.MidTerm.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.MidTerm.model.transactions.Transaction;

import javax.persistence.*;
import java.util.List;

@Entity
public class ThirdParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String hashedKey;

    @OneToMany(mappedBy = "thirdParty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactionDestinationList;


    public ThirdParty() {
    }


    /**
     * Only constructor valid for a Third Party user
     **/
    public ThirdParty(String name, String hashedKey) {
        this.name = name;
        this.hashedKey = hashedKey;
    }


    //  Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    public List<Transaction> getTransactionDestinationList() {
        return transactionDestinationList;
    }

    public void setTransactionDestinationList(List<Transaction> transactionDestinationList) {
        this.transactionDestinationList = transactionDestinationList;
    }
}
