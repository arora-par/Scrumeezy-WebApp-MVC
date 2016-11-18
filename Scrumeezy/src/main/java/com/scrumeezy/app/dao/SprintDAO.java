package com.scrumeezy.app.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.scrumeezy.app.pojo.Sprint;
import com.scrumeezy.app.pojo.Task;
import com.scrumeezy.app.pojo.UserStory;

@Repository
public class SprintDAO extends DAO {

	private static final Logger logger = LoggerFactory.getLogger(SprintDAO.class);

	public UserStory getTasksByStoryId(Long storyId) {
		logger.info("Begin - Sprint  - getTasksByStoryId");
		Session session = getCurrentSession();
		Transaction tx = null;
		UserStory story = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			story = session.get(UserStory.class, storyId);
			Hibernate.initialize(story.getTasks());
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - Sprint  - getTasksByStoryId");
		}
		
		return story;
	}

	public UserStory getStoryById(Long useStoryIdLng) {
		logger.info("Begin - Sprint  - getStoryById");
		Session session = getCurrentSession();
		Transaction tx = null;
		UserStory userStory = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			userStory = session.get(UserStory.class, useStoryIdLng);
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - Sprint  - getStoryById");
		}
		
		return userStory;
	}
	
	public Sprint getSprintById(Long sprintId) {
		logger.info("Begin - Sprint  - getSprintById");
		Session session = getCurrentSession();
		Transaction tx = null;
		Sprint sprint = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			sprint = session.get(Sprint.class, sprintId);
			Hibernate.initialize(sprint.getUserStories());
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - Sprint  - getSprintById");
		}
		
		return sprint;
	}

	public Sprint saveSprint(Sprint sprint) throws Exception {
		logger.info("Begin - Sprint  - save");
		Session session = getCurrentSession();
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			session.saveOrUpdate(sprint);			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			throw ex;
		} finally {
			
			logger.info("End - Sprint  - save");
		}
		
		return sprint;
		
	}
	
	public UserStory saveStory(UserStory story) throws Exception {
		logger.info("Begin - UserStory  - save");
		Session session = getCurrentSession();
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			session.saveOrUpdate(story);			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			throw ex;
		} finally {
			
			logger.info("End - UserStory  - save");
		}
		
		return story;
		
	}
	
	public Task saveTask(Task task) throws Exception {
		logger.info("Begin - Task  - save");
		Session session = getCurrentSession();
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			session.saveOrUpdate(task);			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			throw ex;
		} finally {
			
			logger.info("End - Task  - save");
		}
		
		return task;
		
	}
	
	public Task getTaskById(Long storyId) {
		logger.info("Begin - Sprint  - getTasksByStoryId");
		Session session = getCurrentSession();
		Transaction tx = null;
		Task task = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			task = session.get(Task.class, storyId);
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - Sprint  - getTasksByStoryId");
		}
		
		return task;
	}

}
