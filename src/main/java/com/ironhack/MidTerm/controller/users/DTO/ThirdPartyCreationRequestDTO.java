package com.ironhack.MidTerm.controller.users.DTO;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class ThirdPartyCreationRequestDTO {
    @NotEmpty
    private String name;
    @Length(min = 6, max = 10, message = "HashedKey length must be between 6 and 10 characters")
    private String hashedKey;

    public ThirdPartyCreationRequestDTO() {
    }

    public ThirdPartyCreationRequestDTO(@NotEmpty String name, @Length(min = 6, max = 10, message = "HashedKey length must be between 6 and 10 characters") String hashedKey) {
        this.name = name;
        this.hashedKey = hashedKey;
    }

//  Getters and setters
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
}
