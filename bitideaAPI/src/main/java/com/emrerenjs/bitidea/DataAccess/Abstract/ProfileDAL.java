package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Entity.MySQL.User;

public interface ProfileDAL {
    Profile getProfile(String username);
    void createProfile(User user);
    void updateProfile(Profile profile);
}
