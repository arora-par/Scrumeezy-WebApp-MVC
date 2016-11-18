package com.scrumeezy.app.dao;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.scrumeezy.app.pojo.Employee;
import com.scrumeezy.app.pojo.Team;
import com.scrumeezy.app.pojo.TeamUsers;
import com.scrumeezy.app.pojo.User;

@Repository
public class TeamDAO extends DAO{

private static final Logger logger = LoggerFactory.getLogger(TeamDAO.class);
	
	public Team saveTeam(Team team) throws Exception {
		logger.info("Begin - TEAM  - save");
		
		Session session = getCurrentSession();
		
		Transaction tx = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			session.saveOrUpdate(team);
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			throw ex;
		} finally {
			
			logger.info("End - TEAM  - save");
		}
		
		return team;
		
	}

	public Employee getEmployeeFromEmail(String emailId) {

		Session session = getCurrentSession();
		
		if (session == null) {
			session = getSession();
			
		}
		
		Transaction tx = null;
		Employee emp = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			Query qry = session.createQuery("from Employee where emailId = :emailId");
			qry.setString("emailId", emailId);
			 emp = (Employee) qry.uniqueResult();
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - getEmployee");
		}
		
		
		return emp;
	}

	public User getUserFromEmp(Long empId) {
		Session session = getCurrentSession();
		
		if (session == null) {
			session = getSession();
			
		}
		
		Transaction tx = null;
		User user = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			user =  session.get(User.class, empId);
			Team activeTeam = null;
			Hibernate.initialize(user.getTeamMembers());
			
			for (TeamUsers teamUsr : user.getTeamMembers()) {
				 if (teamUsr.getIsUsersDefaultTeam()) {
					 activeTeam = teamUsr.getTeam();
					 Hibernate.initialize(activeTeam.getTeamMembers());
				 }
			 }
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - getUserFromEmp");
		}
		
		 
		 return user;
	}

	public Team getTeamById(Long teamId) {
		Session session = getCurrentSession();
		
		Transaction tx = null;
		Team team = null;
		try {
			tx= session.getTransaction();
			tx.begin();
			
			team =  session.get(Team.class, teamId);
			Team activeTeam = null;
			Hibernate.initialize(team.getTeamMembers());
			Hibernate.initialize(team.getProject());
			for (TeamUsers teamUsr : team.getTeamMembers()) {
				 if (teamUsr.getIsUsersDefaultTeam()) {
					 activeTeam = teamUsr.getTeam();
					 Hibernate.initialize(activeTeam.getTeamMembers());
				 }
			 }
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - getUserFromEmp");
		}
		
		 
		 return team;
	}
	
	public int updateDefaultTeam(Long userId, Long teamId){
		
		Session session = getCurrentSession();
		
		Transaction tx = null;
		
		int count = -1; 
		
		try {
			tx= session.getTransaction();
			tx.begin();
			
			Query qry1 = session.createQuery("update TeamUsers tu set tu.isUsersDefaultTeam=:falseVal where tu.user.empId=:userId");
			qry1.setBoolean("falseVal", false);
			qry1.setLong("userId", userId);
			qry1.executeUpdate();
			
			Query qry2 = session.createQuery("update TeamUsers tu set tu.isUsersDefaultTeam=:trueVal where tu.user.empId=:userId and tu.team.id=:teamId");
			qry2.setBoolean("trueVal", true);
			qry2.setLong("userId", userId);
			qry2.setLong("teamId", teamId);
			count = qry2.executeUpdate();
			
			tx.commit();
		} catch (Exception ex) {
			logger.error("Exception: " + ex.getMessage());
			tx.rollback();
			
		} finally {
			
			logger.info("End - getUserFromEmp");
		}
		
		return count;
	}

}
