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
public class Feature implements Comparable<Feature>{

	public Feature(){
		stories = new HashSet<UserStory>();
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String description;
	
	// TODO - Status = Pending when set of User Stories is empty OR Task List under a story is empty
	//               = In-progress when any tasks is To-do/In-progress
				//	 = Done when all tasks under all stories are marked Done 	
	
	
	@OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserStory> stories;
	
	@ManyToOne
	private Project project;
	
	// TODO - ajax repaint & db update when priority changes
	private Integer priority;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UserStory> getStories() {
		return stories;
	}

	public void setStories(Set<UserStory> stories) {
		this.stories = stories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	

	// TODO - check order
	@Override
	public int compareTo(Feature other) {
		
		return this.priority.compareTo(other.getPriority());
	}

}
