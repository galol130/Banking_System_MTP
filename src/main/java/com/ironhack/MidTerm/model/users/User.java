package com.ironhack.MidTerm.model.users;

import com.ironhack.MidTerm.enums.RoleName;
import com.ironhack.MidTerm.model.transactions.Transaction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    private static final Role DEFAULT_userRole = new Role(RoleName.STANDARD_USER);
    private static final Role DEFAULT_thirdPartyRole = new Role(RoleName.THIRDPARTY);
    private static final Set<Role> DEFAULT_roleSet = new HashSet<Role>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

    @OneToMany(mappedBy = "originUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactionOriginList;

    @OneToMany(mappedBy = "destinationUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactionDestinationList;



    public User() {
    }

    //  Full constructor with DEFAULT role
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        DEFAULT_roleSet.add(DEFAULT_userRole);
        setRoles(DEFAULT_roleSet);
    }

    //  Full constructor with roles as parameter
    public User(String username, String password, String firstName, String lastName, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    //  Constructor ONLY used by Third Party
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        DEFAULT_roleSet.add(DEFAULT_thirdPartyRole);
        setRoles(DEFAULT_roleSet);
    }

    //  Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
