package com.emrerenjs.bitidea.DataAccess.Concrete;

import com.emrerenjs.bitidea.DataAccess.Abstract.UserSecurityDAL;
import com.emrerenjs.bitidea.Entity.MySQL.User;
import com.emrerenjs.bitidea.Error.RecordNotFoundException;
import com.emrerenjs.bitidea.Error.UniqueConstraintException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@Repository
public class HibernateUserSecurityDAL implements UserSecurityDAL {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        try{
            User user = session.createQuery("from User where username = :username",User.class)
                    .setParameter("username",username).getSingleResult();
            return user;
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        try{
            User user = session.createQuery("from User where email = :email",User.class)
                    .setParameter("email",email)
                    .getSingleResult();
            return user;
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    @Transactional
    public void createAccount(User user) {
        Session session = entityManager.unwrap(Session.class);
        if(this.getUserByUsername(user.getUsername()) != null){
            throw new UniqueConstraintException("Bu kullanıcı adı mevcut!");
        }else if(this.getUserByEmail(user.getEmail()) != null){
            throw new UniqueConstraintException("Bu email hesabı mevcut!");
        }else{
            session.save(user);
        }
    }

    @Override
    @Transactional
    public void deleteAccount(User user) {
        Session session = entityManager.unwrap(Session.class);
        User userToDelete = this.getUserByUsername(user.getUsername());
        if(userToDelete == null){
            throw new RecordNotFoundException("Silinecek kullanıcı bulunamadı!");
        }else{
            session.delete(userToDelete);
        }
    }

    @Override
    @Transactional
    public void updateAccount(User user) {
        //soon
    }

}
