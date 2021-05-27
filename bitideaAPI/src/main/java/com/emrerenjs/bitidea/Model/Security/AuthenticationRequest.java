package com.emrerenjs.bitidea.Model.Security;

import com.emrerenjs.bitidea.Model.HttpRequestModel;

public class AuthenticationRequest implements HttpRequestModel {

    private String username;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public boolean isFieldsNull() {
        return this.getUsername() == null
                || this.getUsername().trim().equals("")
                || this.getPassword() == null
                || this.getPassword().trim().equals("");
    }
}
