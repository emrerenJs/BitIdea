package com.emrerenjs.bitidea.DataAccess.Concrete;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.emrerenjs.bitidea.Entity.MySQL.ProfilesCodeGroups;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emrerenjs.bitidea.DataAccess.Abstract.CodeGroupDAL;
import com.emrerenjs.bitidea.Entity.MySQL.CodeGroup;
import com.emrerenjs.bitidea.Error.RecordNotFoundException;

import java.util.List;

@Repository
public class HibernateCodeGroupDAL implements CodeGroupDAL{

	@Autowired
	EntityManager entityManager;
	
	
	@Override
	@Transactional
	public void createGroup(CodeGroup codeGroup) {
		Session session = entityManager.unwrap(Session.class);
		session.save(codeGroup);
	}
	
	@Override
	@Transactional
	public CodeGroup getGroupByGroupName(String groupName) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CodeGroup codeGroup = session.createQuery("FROM CodeGroup where name = :groupName",CodeGroup.class)
					.setParameter("groupName", groupName)
					.getSingleResult();
			return codeGroup;
		}catch(NoResultException e) {
			throw new RecordNotFoundException("Aranan grup bulunamadı!");
		}
	}

	@Override
	@Transactional
	public void removeGroup(CodeGroup codeGroup) {
		Session session = entityManager.unwrap(Session.class);
		session.remove(codeGroup);
	}

	@Override
	@Transactional
	public void updateGroup(CodeGroup codeGroup) {
		Session session = entityManager.unwrap(Session.class);
		session.update(codeGroup);
	}

	@Override
	@Transactional
	public void removeMemberFromGroup(ProfilesCodeGroups profilesCodeGroup) {
		Session session = entityManager.unwrap(Session.class);
		session.remove(profilesCodeGroup);
	}

	@Override
	@Transactional
	public List<CodeGroup> getGroupByKey(String key) {
		Session session = entityManager.unwrap(Session.class);
		List<CodeGroup> codeGroups = session
				.createQuery("FROM CodeGroup as cg where cg.name like '%" + key + "%'",CodeGroup.class)
				.getResultList();
		return codeGroups;
	}
}
