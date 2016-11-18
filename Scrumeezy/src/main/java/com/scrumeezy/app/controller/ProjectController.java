package com.scrumeezy.app.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scrumeezy.app.dao.ProjectDAO;
import com.scrumeezy.app.dao.TeamDAO;
import com.scrumeezy.app.pojo.Feature;
import com.scrumeezy.app.pojo.Project;
import com.scrumeezy.app.pojo.Team;
import com.scrumeezy.app.pojo.TeamUsers;
import com.scrumeezy.app.pojo.User;
import com.scrumeezy.app.pojo.UserStory;

@Controller
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	TeamDAO teamDAO;

	@RequestMapping(value = "/project/new.htm", method = RequestMethod.GET)
	public String newProjectForm(@ModelAttribute("project") Project project) {

		return "newproject";
	}

	@RequestMapping(value = "/project/save.htm", method = RequestMethod.POST)
	public String newProjectSubmit(@ModelAttribute("project") Project project, ModelMap modelMap, HttpSession session) {

		Long empId = (Long) session.getAttribute("userId");
		User user = teamDAO.getUserFromEmp(empId);

		Team team = new Team();
		team.setProject(project);
		team.setTeamName(project.getProjectName());
		team.addMemberToRole(user, "PO");
		project.setTeam(team);

		try {
			logger.info("New project save requested");
			projectDAO.saveProject(project);
			session.setAttribute("project", project.getId());
			
			teamDAO.updateDefaultTeam(empId, team.getId());
			session.setAttribute("userInRole", "PO");
			session.setAttribute("sprintFound", false);
			
			
		} catch (Exception e) {
			logger.error("New project save error" + e.getCause().getMessage());
			modelMap.addAttribute("error", e.getMessage());
			return "error";
		}
		logger.info("New project save requested completed");

		return "taskboard";
	}

	@RequestMapping(value = "/project/features.htm", method = RequestMethod.GET)
	public String manageFeatures(ModelMap modelMap, HttpSession session) {

		logger.info("manage features invoked");

		Long projId = (Long) session.getAttribute("project");
		Project usersActiveProject = projectDAO.getProjectById(projId);

		if (usersActiveProject != null) {
			modelMap.addAttribute("features", usersActiveProject.getFeatures());
			return "managefeatures";
		}

		return "taskboard";

	}

	@RequestMapping(value = "/project/addFeature.htm")
	public @ResponseBody String addFeature(HttpServletRequest request) {
		logger.info("add feature to project invoked");
		String description = request.getParameter("description");
		Long projId = (Long) request.getSession().getAttribute("project");
		Project usersActiveProject = projectDAO.getProjectById(projId);

		if (usersActiveProject != null) {

			Feature feature = new Feature();
			feature.setDescription(description);

			// TODO - fetch count of features and do +1
			feature.setPriority(0);
			feature.setProject(usersActiveProject);
			usersActiveProject.getFeatures().add(feature);

			try {
				projectDAO.saveProject(usersActiveProject);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "Error adding new feature";
			}

		} else {
			return "Error looking up the project";
		}

	}

	@RequestMapping(value = "/project/addUserStory.htm")
	public @ResponseBody String addUserStory(HttpServletRequest request) {
		logger.info("add user story to feature invoked");

		Long projId = (Long) request.getSession().getAttribute("project");

		String description = request.getParameter("storyDesc");
		String points = request.getParameter("points");
		String featureId = request.getParameter("featureId");

		Project usersActiveProject = projectDAO.getProjectById(projId);

		if (usersActiveProject != null) {

			try {
				Feature feature = projectDAO.getFeatureById(Long.parseLong(featureId));
				if (feature != null) {

					UserStory usStory = new UserStory();
					usStory.setDescription(description);
					usStory.setFeature(feature);
					usStory.setPoints(Integer.parseInt(points));
					feature.getStories().add(usStory);
					projectDAO.saveFeature(feature);
					return "success";
				} else {
					return "Error looking up feature";
				}

			} catch (Exception e) {

				e.printStackTrace();
				return "Error saving user story";
			}

		} else {
			return "Error looking up the project";
		}

	}

	@RequestMapping(value = "/project/listStories.htm")
	public @ResponseBody String fetchUserStories(HttpServletRequest request) {
		logger.info("list user stories invoked");
		Long projId = (Long) request.getSession().getAttribute("project");
		String featureId = request.getParameter("featureId");
		Project usersActiveProject = projectDAO.getProjectById(projId);

		if (usersActiveProject != null) {
			try {
				Feature feature = projectDAO.getFeatureById(Long.parseLong(featureId));
				if (feature != null) {

					Set<UserStory> userStories = feature.getStories();
					StringBuilder json = new StringBuilder("[");
					for (UserStory usrStory : userStories) {
						json.append("{\"description\":\"" + usrStory.getDescription() + "\", \"points\":\""
								+ usrStory.getPoints() + "\"},");
					}
					int indexOfLastComma = json.lastIndexOf(",");
					json = json.replace(indexOfLastComma, indexOfLastComma + 2, "");
					return json.append("]").toString();
				} else {
					return "Error looking up feature";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return "Error looking up stories";
			}
		} else {
			return "Error looking up the project";
		}
	}

	@RequestMapping(value = "/project/getProjectsForUser.htm")
	public @ResponseBody String getTeamsForUser(HttpSession session) {
		logger.info("getProjectsForUser invoked");

		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "Please login again";

		} else {

			User user = teamDAO.getUserFromEmp(userId);

			StringBuilder teamsStr = new StringBuilder("");
			for (TeamUsers teamMembership : user.getTeamMembers()) {

				teamsStr.append(teamMembership.getTeam().getId()).append("-")
						.append(teamMembership.getTeam().getTeamName()).append(",");
			}

			int indexOfLastComma = teamsStr.lastIndexOf(",");
			teamsStr.replace(indexOfLastComma, indexOfLastComma + 2, "");

			return teamsStr.toString();
		}
	}

	@RequestMapping(value = "/project/switchTeam.htm")
	public @ResponseBody String switchTeam(HttpServletRequest request) {
		logger.info("switchTeam invoked");

		
		Long userId = (Long) request.getSession().getAttribute("userId");
		if (userId == null) {
			return "Please login again";

		} else {
			
			String teamId = request.getParameter("teamId");
			Long teamIdLong = Long.parseLong(teamId);

			int count  = teamDAO.updateDefaultTeam(userId, teamIdLong);
			if (count >= 0) {
				return "success";
			} else {
				return "failure";
			}
			
		}

		
	}
}
