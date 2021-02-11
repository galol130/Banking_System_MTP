package com.ironhack.MidTerm.model.users;

import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.accounts.Account;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class AccountHolder extends User {
    private String personalId;
    private LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "primary_street")),
            @AttributeOverride(name = "doorNumber", column = @Column(name = "primary_door_number")),
            @AttributeOverride(name = "aptNumber", column = @Column(name = "primary_apt_number")),
            @AttributeOverride(name = "extraInfo", column = @Column(name = "primary_extra_info")),
            @AttributeOverride(name = "city", column = @Column(name = "primary_city")),
            @AttributeOverride(name = "stateOrProvince", column = @Column(name = "primary_state_or_province")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "primary_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "primary_country")),
    })
    private Address primaryAddress;

    @Embedded()
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "secondary_street")),
            @AttributeOverride(name = "doorNumber", column = @Column(name = "secondary_door_number")),
            @AttributeOverride(name = "aptNumber", column = @Column(name = "secondary_apt_number")),
            @AttributeOverride(name = "extraInfo", column = @Column(name = "secondary_extra_info")),
            @AttributeOverride(name = "city", column = @Column(name = "secondary_city")),
            @AttributeOverride(name = "stateOrProvince", column = @Column(name = "secondary_state_or_province")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "secondary_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "secondary_country")),
    })
    private Address secondaryAddress;

    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> primaryAccountsList;

    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> secondaryAccountsList;


    public AccountHolder() {
    }

    /**
     * Constructor without set of roles, will take the DEFAULT role (STANDARD_USER)
     **/
    public AccountHolder(String username, String password, String firstName, String lastName, String personalId, LocalDate dateOfBirth, Address primaryAddress, Address secondaryAddress) {
        super(username, password, firstName, lastName);
        this.personalId = personalId;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.secondaryAddress = secondaryAddress;
    }

    /**
     * Full constructor
     **/
    public AccountHolder(String username, String password, String firstName, String lastName, Set<Role> roleSet, String personalId, LocalDate dateOfBirth, Address primaryAddress, Address secondaryAddress) {
        super(username, password, firstName, lastName, roleSet);
        this.personalId = personalId;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.secondaryAddress = secondaryAddress;
    }

    //  Getters and setters
    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getSecondaryAddress() {
        return secondaryAddress;
    }

    public void setSecondaryAddress(Address secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }
}
