package com.emrerenjs.bitidea.DataAccess.Concrete;

import com.emrerenjs.bitidea.DataAccess.Abstract.ProfileDAL;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Entity.MySQL.User;
import com.emrerenjs.bitidea.Error.RecordNotFoundException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@Repository
public class HibernateProfileDAL implements ProfileDAL {

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public Profile getProfile(String username) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Profile profile = session.createQuery("FROM Profile where username = :username",Profile.class)
                    .setParameter("username",username).getSingleResult();
            return profile;
        }catch(NoResultException e){
            throw new RecordNotFoundException("Aranan profil bulunamadı!");
        }
    }

    @Override
    @Transactional
    public void createProfile(User user) {
        Session session = entityManager.unwrap(Session.class);
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setUsername(user.getUsername());
        session.save(profile);
    }

    @Override
    @Transactional
    public void updateProfile(Profile profile) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Profile profileUpdated = session.createQuery("FROM Profile where username = :username",Profile.class)
                    .setParameter("username",profile.getUsername()).getSingleResult();
            profileUpdated.setBiography(profile.getBiography());
            profileUpdated.setFirstname(profile.getFirstname());
            profileUpdated.setLastname(profile.getLastname());
            if(profile.getPhoto() != null) {
                profileUpdated.setPhoto(profile.getPhoto());
            }
            profileUpdated.setScore(profile.getScore());
            session.update(profileUpdated);
        }catch(NoResultException exception){
            throw new RecordNotFoundException("Güncellenecek profil bulunamadı!");
        }
    }


}
