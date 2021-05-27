package com.emrerenjs.bitidea.Business.Concrete;

import com.emrerenjs.bitidea.DataAccess.Abstract.UserSecurityDAL;
import com.emrerenjs.bitidea.Entity.MySQL.User;
import com.emrerenjs.bitidea.Model.Security.UserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsManager implements UserDetailsService {

    @Autowired
    UserSecurityDAL userSecurityDAL;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userSecurityDAL.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Kullanıcı adı veya parola hatalı!");
        }
        return new UserDetailsModel(user);
    }
}
