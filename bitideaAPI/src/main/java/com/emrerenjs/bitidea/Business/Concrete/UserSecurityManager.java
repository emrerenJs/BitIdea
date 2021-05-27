package com.emrerenjs.bitidea.Business.Concrete;

import com.emrerenjs.bitidea.Business.Abstract.MailService;
import com.emrerenjs.bitidea.Business.Abstract.UserSecurityService;
import com.emrerenjs.bitidea.DataAccess.Abstract.ProfileDAL;
import com.emrerenjs.bitidea.DataAccess.Abstract.RoleDAL;
import com.emrerenjs.bitidea.DataAccess.Abstract.UserSecurityDAL;
import com.emrerenjs.bitidea.Entity.MySQL.Role;
import com.emrerenjs.bitidea.Entity.MySQL.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserSecurityManager implements UserSecurityService {

    @Autowired
    UserSecurityDAL userSecurityDAL;
    @Autowired
    ProfileDAL profileDAL;

    @Autowired
    MailService mailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleDAL roleDAL;

    @Override
    public void createAccount(User user) {
        //mailService.sendMail(new MailModel(user.getEmail(),"Token","E-Mail onayı"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleDAL.getRole("Psittacidae");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoleList(roles);
        userSecurityDAL.createAccount(user);
        profileDAL.createProfile(user);
    }

    @Override
    public void deleteAccount(User user) {
        userSecurityDAL.deleteAccount(user);
    }

    @Override
    public void changePassword(User user) {
        //soon
    }

    @Override
    public void forgotPassword(User user) {
        //soon
    }
}
