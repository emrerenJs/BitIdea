package com.emrerenjs.bitidea.DataAccess.Concrete;

import com.emrerenjs.bitidea.DataAccess.Abstract.RoleDAL;
import com.emrerenjs.bitidea.Entity.MySQL.Role;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateRoleDAL implements RoleDAL {

    @Autowired
    EntityManager entityManager;

    @Override
    public Role getRole(String role){
        Session session = entityManager.unwrap(Session.class);
        try{
            Role roleEntity = session.createQuery("from Role where role = :role",Role.class)
                    .setParameter("role",role)
                    .getSingleResult();
            return roleEntity;
        }catch(Exception ex){
            return null;
        }
    }
}
