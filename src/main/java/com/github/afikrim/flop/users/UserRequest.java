package com.github.afikrim.flop.users;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.afikrim.flop.accounts.AccountRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRequest {

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("account")
    private Optional<AccountRequest> account;

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Optional<AccountRequest> getAccount() {
        return account;
    }

}
