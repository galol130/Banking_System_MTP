package com.ironhack.MidTerm.controller.users.DTO;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

//By default, assigned the same values to primary and secondary address. Use te
public class AccountHolderCreationRequestDTO {
    @NotEmpty
    private String username;
    @Length(min = 6, max = 10, message = "Password length must be between 6 and 10 characters")
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String personalId;

    private LocalDate dateOfBirth;
    @NotEmpty
    private String street;
    @NotEmpty
    private String doorNumber;
    private String aptNumber;
    private String extraInfo;
    private String city;
    private String stateOrProvince;
    @NotEmpty
    private String postalCode;
    @NotEmpty
    private String country;



    public AccountHolderCreationRequestDTO() {
    }

    public AccountHolderCreationRequestDTO(@NotEmpty String username, @Length(min = 6, max = 10, message = "Password length must be between 6 and 10 characters") String password, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String personalId, LocalDate dateOfBirth, @NotEmpty String street, @NotEmpty String doorNumber, String aptNumber, String extraInfo, String city, String stateOrProvince, @NotEmpty String postalCode, @NotEmpty String country) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.doorNumber = doorNumber;
        this.aptNumber = aptNumber;
        this.extraInfo = extraInfo;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.postalCode = postalCode;
        this.country = country;
    }

    //  Getters and setters
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

//    /** Constructor without set of roles, will take the DEFAULT role (STANDARD_USER)  **/
//    public AccountHolder(String username, String password, String firstName, String lastName, String personalId,
//                          LocalDate dateOfBirth, Address primaryAddress, Address secondaryAddress) {
//        super(username, password, firstName, lastName);
//        this.personalId = personalId;
//        this.dateOfBirth = dateOfBirth;
//        this.primaryAddress = primaryAddress;
//        this.secondaryAddress = secondaryAddress;
//    }