package com.ironhack.MidTerm.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Embeddable
public class Address {
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


    public Address() {
    }

    public Address(@NotEmpty String street,
                   @NotEmpty String doorNumber,
                   @NotEmpty String postalCode,
                   @NotEmpty String country) {
        this.street = street;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Address(@NotEmpty String street,
                   @NotEmpty String doorNumber,
                   String aptNumber,
                   String extraInfo,
                   String city,
                   String stateOrProvince,
                   @NotEmpty String postalCode,
                   @NotEmpty String country) {
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
