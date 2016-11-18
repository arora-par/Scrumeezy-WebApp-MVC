package com.scrumeezy.app.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sprint implements Comparable<Sprint>{
	
	public Sprint() {
		
		userStories = new HashSet<UserStory>();
	}

	@Id
	@GeneratedValue
	private Long id;
	
	private Date startDate;
	
	// TODO - sort by endDate - confirm order
	private Date endDate;

	private Boolean isForRelease;
	
	@OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserStory> userStories;
	
	@ManyToOne
	private Project project;
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getIsForRelease() {
		return isForRelease;
	}
	public void setIsForRelease(Boolean isForRelease) {
		this.isForRelease = isForRelease;
	}
	public Set<UserStory> getUserStories() {
		return userStories;
	}
	public void setUserStories(Set<UserStory> userStories) {
		this.userStories = userStories;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public int compareTo(Sprint other) {
		
		return other.getEndDate().compareTo(this.endDate); // Descending
	}
}
