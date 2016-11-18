package com.scrumeezy.app.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.scrumeezy.app.pojo.Feature;
import com.scrumeezy.app.pojo.Project;

@Repository
public class ProjectDAO extends DAO{

private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);
	
	public Project saveProject(Project project) throws Exception {
		logger.info("Begin - PROJECT  - save");
		Session session = getCurrentSession();
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			session.saveOrUpdate(project);			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			throw ex;
		} finally {
			
			logger.info("End - PROJECT  - save");
		}
		
		return project;
		
	}

	public Project getProjectById(Long usersActiveProjectId) {
		logger.info("Begin - PROJECT  - getProjectById");
		Session session = getCurrentSession();
		Transaction tx = null;
		Project project = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			project = session.get(Project.class, usersActiveProjectId);
			Hibernate.initialize(project.getTeam().getTeamMembers());
			Hibernate.initialize(project.getFeatures());
			Hibernate.initialize(project.getSprints());
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - PROJECT  - getProjectById");
		}
		
		return project;
	}



	public Feature getFeatureById(Long featureId) {
		logger.info("Begin - PROJECT  - getFeatureById");
		Session session = getCurrentSession();
		Transaction tx = null;
		Feature feature = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			feature = session.get(Feature.class, featureId);
			
			Hibernate.initialize(feature.getStories());
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - PROJECT  - getFeatureById");
		}
		
		return feature;	}

	public Feature saveFeature(Feature feature) throws Exception {
		logger.info("Begin - Feature  - save");
		Session session = getCurrentSession();
		Transaction tx = null;
		
		try {
			tx= session.getTransaction();
			tx.begin();
			session.saveOrUpdate(feature);			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			throw ex;
		} finally {
			
			logger.info("End - feature  - save");
		}
		
		return feature;
		
	}

}
