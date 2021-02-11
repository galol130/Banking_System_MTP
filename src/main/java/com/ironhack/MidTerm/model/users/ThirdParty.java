package com.ironhack.MidTerm.model.users;

import javax.persistence.Entity;

@Entity
public class ThirdParty extends User {
    private String personalId;
    private String hashedKey;


    public ThirdParty() {
    }

    /**
     * Only constructor valid for a Third Party user
     **/
    public ThirdParty(String firstName, String lastName, String personalId, String hashedKey) {
        super(firstName, lastName);
        this.personalId = personalId;
        this.hashedKey = hashedKey;
    }

    //  Getters and setters
    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
