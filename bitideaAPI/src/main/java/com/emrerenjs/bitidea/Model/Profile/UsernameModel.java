package com.emrerenjs.bitidea.Model.Profile;

import com.emrerenjs.bitidea.Model.HttpRequestModel;
import lombok.Data;

@Data
public class UsernameModel implements HttpRequestModel {
    private String username;

    @Override
    public boolean isFieldsNull() {
        return this.username == null || this.username.trim() == "";
    }
}
