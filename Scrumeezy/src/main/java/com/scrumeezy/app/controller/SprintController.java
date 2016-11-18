package com.scrumeezy.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scrumeezy.app.dao.ProjectDAO;
import com.scrumeezy.app.dao.SprintDAO;
import com.scrumeezy.app.dao.UserDAO;
import com.scrumeezy.app.pojo.Feature;
import com.scrumeezy.app.pojo.Project;
import com.scrumeezy.app.pojo.Sprint;
import com.scrumeezy.app.pojo.Task;
import com.scrumeezy.app.pojo.User;
import com.scrumeezy.app.pojo.UserStory;

@Controller
public class SprintController {

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	SprintDAO sprintDAO;
	
	@Autowired
	UserDAO userDAO;
	

	@RequestMapping(value = "/sprint/next.htm", method = RequestMethod.GET)
	public String nextSprint(HttpSession session, ModelMap modelMap) {

		Project activeProj = projectDAO.getProjectById((Long) session.getAttribute("project"));
		session.setAttribute("sprintLength", activeProj.getSprintLength());
		if (activeProj != null) {
			Map<Long, Feature> featureMap = new HashMap<Long, Feature>();
			for (Feature feature : activeProj.getFeatures()) {

				Long featureId = feature.getId();
				Feature fullFeature = projectDAO.getFeatureById(featureId);

				if (!fullFeature.getStories().isEmpty()) {
					for (UserStory userStory : fullFeature.getStories()) {
						UserStory userStoryFull = sprintDAO.getTasksByStoryId(userStory.getId());
						// take only those stories that have no tasks associated
						// with them
						if (userStoryFull.getTasks().isEmpty()) {
							featureMap.put(featureId, fullFeature);
						}
					}

				}

			}

			modelMap.addAttribute("featureStoryMap", featureMap);
		}

		return "nextsprint";
	}

	@RequestMapping(value = "/sprint/nextSprint.htm", method = RequestMethod.POST)
	public @ResponseBody String saveNextSprint(HttpServletRequest request, HttpSession session, ModelMap modelMap) {

		String storyList = request.getParameter("storyIdList");
		String[] storyIdArr = storyList.split(",");

		List<UserStory> foundStories = new ArrayList<UserStory>();
		Sprint sprint = new Sprint();
		sprint.setStartDate(new Date());
		
		Project activeProj = projectDAO.getProjectById((Long) session.getAttribute("project"));
		
		int sprintLength = activeProj.getSprintLength(); 
		sprint.setEndDate(DateUtils.addWeeks(new Date(), sprintLength));
		sprint.setProject(activeProj);
		for (String storyId : storyIdArr) {
			Long useStoryIdLng = Long.parseLong(storyId);
			UserStory userStory = sprintDAO.getStoryById(useStoryIdLng);

			if (userStory != null) {
				userStory.setSprint(sprint);
				foundStories.add(userStory);
			}
		}

		if (!foundStories.isEmpty()) {

			sprint.getUserStories().addAll(foundStories);
			try {
				sprintDAO.saveSprint(sprint);
				return "success";
			} catch (Exception e) {
				
				e.printStackTrace();
				return "Error saving the sprint details";
			}
		} else {
			return "Invalid Story Numbers received";
		}

		
	}

	@RequestMapping(value = "/sprint/addTask.htm", method = RequestMethod.POST)
	public @ResponseBody String addTask(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
		
		// TODO - try a super class for user validation before action. send to login page in case of failure.

		String storyId = request.getParameter("storyId");
		String taskDescription = request.getParameter("description");
		String dueDate = request.getParameter("dueDate");
		System.out.println(dueDate);
		
		User user = userDAO.getUserById((Long) session.getAttribute("userId"));
		
		UserStory activeStory = sprintDAO.getTasksByStoryId(Long.parseLong(storyId));

		Task task = new Task();
		task.setAssignedTo(user);
		task.setCreatedBy(user);
		task.setCreationDate(new Date());
		task.setDescription(taskDescription);
		
		String pattern = "yyyy-MM-dd";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    try {
	      Date date = format.parse(dueDate);
	      task.setDueDate(date);
	    } catch (ParseException e) {
	    	e.printStackTrace();
	      return "invalid date";
	    }
		
		
		task.setStatus("T");
		task.setUserStory(activeStory);activeStory.getTasks().add(task);
		
		try {
			sprintDAO.saveStory(activeStory);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error saving the task.";
		}
		
		
		
	}
	
	// TODO - delete task
	
	@RequestMapping(value = "/sprint/markProgress.htm", method = RequestMethod.POST)
	public @ResponseBody String markInProgress(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
		
		String taskId = request.getParameter("taskId");
		Task task = sprintDAO.getTaskById(Long.parseLong(taskId));
		task.setDoneDate(null);
		task.setStatus("P");
		try {
			sprintDAO.saveTask(task);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error updating task status";
		}
		
		
		
	}
	
	@RequestMapping(value = "/sprint/markTodo.htm", method = RequestMethod.POST)
	public @ResponseBody String markTodo(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
		
		String taskId = request.getParameter("taskId");
		Task task = sprintDAO.getTaskById(Long.parseLong(taskId));
		task.setDoneDate(null);
		task.setStatus("T");
		try {
			sprintDAO.saveTask(task);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error updating task status";
		}
		
		
		
	}
	

	@RequestMapping(value = "/sprint/markDone.htm", method = RequestMethod.POST)
	public @ResponseBody String markDone(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
		
		String taskId = request.getParameter("taskId");
		Task task = sprintDAO.getTaskById(Long.parseLong(taskId));
		
		task.setStatus("D");
		task.setDoneDate(new Date());
		
		try {
			sprintDAO.saveTask(task);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error updating task status";
		}
		
		
		
	}

}
