package com.ironhack.MidTerm.model.users;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class Admin extends User {
    private String personalId;


    public Admin() {
    }

//  Constructor without set of roles, will take the DEFAULT role (STANDARD_USER)
    public Admin(String username, String password, String firstName, String lastName, String personalId) {
        super(username, password, firstName, lastName);
        this.personalId = personalId;
    }

//  Full constructor
    public Admin(String username, String password, String firstName, String lastName, Set<Role> roleSet, String personalId) {
        super(username, password, firstName, lastName, roleSet);
        this.personalId = personalId;
    }

    //  Getters and setters
    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }
}
