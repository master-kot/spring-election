package com.nikolay.election.utils;

import javax.validation.constraints.NotNull;

public class UserRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;

    private Integer id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return "{noop}" + password;
    }

    public String getPasswordConfirm() {
        return "{noop}" + passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public UserRequest() {
    }
}
