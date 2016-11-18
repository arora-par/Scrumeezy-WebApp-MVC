package com.scrumeezy.app.dao;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import com.scrumeezy.app.pojo.User;

@Repository
public class UserDAO extends DAO{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	public User saveUser(User user) throws Exception {
		logger.info("Begin - USER  - save");
		Session session = getCurrentSession();
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			session.save(user);
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			throw ex;
		} finally {
			
			logger.info("End - USER  - save");
		}
		
		return user;
		
	}

	public User validateLogin(User user) {
		Session session = getCurrentSession();
		
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			String hql = "from User where userName=:userName";
			
			Query query = session.createQuery(hql);
			query.setProperties(user);
			
			//query.setMaxResults(1); - no need
			User foundUser = (User) query.uniqueResult();
			Hibernate.initialize(foundUser.getTeamMembers());
			
			if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
				return foundUser;
			}
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - USER  - save");
		}
		
		
		
		return null;
	}
	
	public User getUserById(Long userId) {
		Session session = getCurrentSession();
		User foundUser = null;
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			
			foundUser = session.get(User.class, userId);
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - USER  - save");
		}
		
		return foundUser;
	}
	
}
