package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MySQL.User;

public interface UserSecurityDAL {

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void createAccount(User user);

    void deleteAccount(User user);

    void updateAccount(User user);

}
