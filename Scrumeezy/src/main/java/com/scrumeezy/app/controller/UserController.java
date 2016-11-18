package com.scrumeezy.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scrumeezy.app.dao.ProjectDAO;
import com.scrumeezy.app.dao.SprintDAO;
import com.scrumeezy.app.dao.TeamDAO;
import com.scrumeezy.app.dao.UserDAO;
import com.scrumeezy.app.pojo.Project;
import com.scrumeezy.app.pojo.Sprint;
import com.scrumeezy.app.pojo.Team;
import com.scrumeezy.app.pojo.TeamUsers;
import com.scrumeezy.app.pojo.User;
import com.scrumeezy.app.pojo.UserStory;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	SprintDAO sprintDAO;
	
	@Autowired
	TeamDAO teamDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@ModelAttribute("user") User user) {

		return "home";
	}
	
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String home(@ModelAttribute("user") User user, ModelMap modelMap,HttpSession session) {

		return login(user, modelMap, session);
	}
	
	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public String logout(@ModelAttribute("user") User user, HttpSession session) {
		session.invalidate();
		return "home";
	}
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, ModelMap modelMap, HttpSession session) {
		
		if (session.getAttribute("userId") == null) {
			user = userDAO.validateLogin(user);
			
			if (user == null){
				// TODO - ajax login failure
				modelMap.addAttribute("error", "Username password combination not found");
				return "error";
			}
			
			session.setAttribute("userId", user.getEmpId());
			session.setAttribute("user", user);
			session.setAttribute("userName", user.getUserName());
		}
		
		if (user == null || user.getEmpId()==null) {
			user = (User) session.getAttribute("user");
		}
		
		if (user == null) {
			session.invalidate();
			return "home";
		}
		
		
		User userFresh = teamDAO.getUserFromEmp(user.getEmpId());
		
		
		for (TeamUsers teamMembership : userFresh.getTeamMembers())
		{
			if (teamMembership.getIsUsersDefaultTeam()) {
				logger.info("Active team (project) found");
				
				session.setAttribute("userInRole", teamMembership.getRole());
				
				Team team = teamDAO.getTeamById(teamMembership.getTeam().getId());
				
				Long activeProjectId = team.getProject().getId();
				session.setAttribute("project", activeProjectId);
				Project currentProject = projectDAO.getProjectById(activeProjectId);
				
				if (currentProject != null && !currentProject.getSprints().isEmpty()) {
					List<Sprint> sprintList = new ArrayList<Sprint>(currentProject.getSprints());
					Collections.sort(sprintList);
					Sprint latestSprint = sprintList.get(0);
					logger.info("Active sprint found. End Date:" + latestSprint.getEndDate());
					
					Sprint latestSprintFull = sprintDAO.getSprintById(latestSprint.getId());
					
					List<UserStory> relevantStories = new ArrayList<UserStory>();
					
					for (UserStory story : latestSprintFull.getUserStories()) {
						
						relevantStories.add(sprintDAO.getTasksByStoryId(story.getId()));
					}
					
					session.setAttribute("currentSprintStories", relevantStories);
					session.setAttribute("sprintId", latestSprint.getId());
					session.setAttribute("sprintFound", true);
					
				} else {
					session.setAttribute("sprintStatus", false);
					
				}
				
				break;
			}
		}
		logger.info("User logged in");
		
		return "taskboard";
	}
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public String registerForm(@ModelAttribute("user") User user) {
		
		
		return "register";
	}
	
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public String submitRegisterForm(@ModelAttribute("user") User user, ModelMap modelMap, HttpSession session) {
		
		try {
			// TODO - check if user already exists on both client side and server side
			user = userDAO.saveUser(user);
		} catch (Exception e) {
			modelMap.addAttribute("error", e.getMessage());
			return "error";
		}
		
		session.setAttribute("user", user);
		session.setAttribute("userId", user.getEmpId());
		session.setAttribute("userName", user.getUserName());
		logger.info("User registered");
		return "taskboard";
	}
	
	
}
