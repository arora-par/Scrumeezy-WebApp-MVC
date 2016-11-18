package com.scrumeezy.app.pojo;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Team {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	private String teamName;
	
	@OneToMany(mappedBy="team", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<TeamUsers> teamMembers;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
	private Project project;
	
	public Set<TeamUsers> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(Set<TeamUsers> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Team(){
		teamMembers = new HashSet<TeamUsers>();
	}
	
	public Set<TeamUsers> addMemberToRole(User user, String role){
		TeamUsers teamUser = new TeamUsers();
		teamUser.setRole(role);
		teamUser.setTeam(this);
		teamUser.setUser(user);
		teamUser.setIsUsersDefaultTeam(true);
		teamMembers.add(teamUser);
		return teamMembers;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals( teamName, team.teamName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( teamName );
    }
	
}
