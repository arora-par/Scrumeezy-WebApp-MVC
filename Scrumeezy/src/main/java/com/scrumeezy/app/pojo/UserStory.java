package com.scrumeezy.app.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class UserStory {

	
	public UserStory(){
		
		tasks = new HashSet<Task>();
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String description;
	
	@OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Task> tasks;
	
	@ManyToOne
	private Feature feature;
	
	@ManyToOne
	private Sprint sprint;
	
	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	private Integer points;

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
}
