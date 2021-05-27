package com.emrerenjs.bitidea.Business.Abstract;

import com.emrerenjs.bitidea.Entity.MySQL.User;

public interface UserSecurityService {

    void createAccount(User user);

    void deleteAccount(User user);

    void changePassword(User user);

    void forgotPassword(User user);

}
