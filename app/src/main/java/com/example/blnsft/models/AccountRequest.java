package com.example.blnsft.models;

public class AccountRequest {
    private String login;
    private String password;

    public AccountRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getEmail() {
        return login;
    }

    public void setEmail(String name) {
        this.login = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}