package com.emrerenjs.bitidea.Model.Security;

import com.emrerenjs.bitidea.Model.HttpRequestModel;
import lombok.Data;

@Data
public class RegisterRequest implements HttpRequestModel {
    private String username;
    private String password;
    private String email;

    @Override
    public boolean isFieldsNull() {
        return this.getUsername() == null
                || this.getUsername().trim().equals("")
                || this.getPassword() == null
                || this.getPassword().trim().equals("")
                || this.getEmail() == null
                || this.getEmail().trim().equals("");
    }
}
