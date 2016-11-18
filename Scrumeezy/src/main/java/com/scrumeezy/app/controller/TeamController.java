package com.scrumeezy.app.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scrumeezy.app.dao.ProjectDAO;
import com.scrumeezy.app.dao.TeamDAO;
import com.scrumeezy.app.pojo.Employee;
import com.scrumeezy.app.pojo.Project;
import com.scrumeezy.app.pojo.Team;
import com.scrumeezy.app.pojo.TeamUsers;
import com.scrumeezy.app.pojo.User;

@Controller
public class TeamController {
	
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	TeamDAO teamDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@RequestMapping(value = "/team/manage.htm", method = RequestMethod.GET)
	public String manageTeam(ModelMap modelMap, HttpSession session) {
		
		logger.info("manage team invoked");
		
		Long usersActiveProjectId = (Long) session.getAttribute("project");
		Project userActiveProject = projectDAO.getProjectById(usersActiveProjectId);
		
		if (userActiveProject != null) {
			
			Team projectTeam = userActiveProject.getTeam();
			Set<TeamUsers> teamMembers = projectTeam.getTeamMembers();
			
			modelMap.addAttribute("teamMembers", teamMembers);
			
			return "manageteam";
		}
		
		return "taskboard";
		
	}

	
	@RequestMapping(value="/team/add.htm")
	public @ResponseBody String addMemberToTeam(HttpServletRequest request) {
		logger.info("add member to team invoked");
		String emailId = request.getParameter("emailId");
		Employee emp = teamDAO.getEmployeeFromEmail(emailId);
		
		if (emp == null) {
			return "invalid email";
			
		}
		
		User newMember = teamDAO.getUserFromEmp(emp.getEmpId());
		Team activeTeam = null;
		 if (newMember != null) {
			 Long managerEmpId = (Long) request.getSession().getAttribute("userId");
			 User manager = teamDAO.getUserFromEmp(managerEmpId);
			 
			 for (TeamUsers teamUsr : manager.getTeamMembers()) {
				 if (teamUsr.getIsUsersDefaultTeam()) {
					 activeTeam = teamUsr.getTeam();
				 }
			 }
			 
			 if (activeTeam != null) {
				 
				 activeTeam.addMemberToRole(newMember, "TM");
				 try {
					teamDAO.saveTeam(activeTeam);
					teamDAO.updateDefaultTeam(newMember.getEmpId(), activeTeam.getId());
					logger.info("save team done");
					return "success";
				} catch (Exception e) {

					logger.error(e.getMessage());
					return "Error adding user to the team";
				}
			 } else {
				 return "Team information not found";
			 }
		 } else {
			 return "Member Not Found";
		 }
	}

}
