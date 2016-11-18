package com.scrumeezy.app.pojo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "empId")
public class User extends Employee {

	public User() {
		teamMembers = new HashSet<TeamUsers>();
	}
	
	@Column(unique = true, nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TeamUsers> teamMembers;

	public Set<TeamUsers> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(Set<TeamUsers> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(userName, user.userName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}

}
