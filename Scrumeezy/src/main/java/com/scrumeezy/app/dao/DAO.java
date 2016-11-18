package com.scrumeezy.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class DAO {

	private SessionFactory sessionFactory = null;

	public DAO() {
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	public Session getSession() {

		return sessionFactory.openSession();
	}

	public Session getCurrentSession() {

		return sessionFactory.getCurrentSession();
	}

}
