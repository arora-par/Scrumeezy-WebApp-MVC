package com.scrumeezy.app.pojo;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TeamUsers implements Serializable {

	
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
	    @ManyToOne
	    private Team team;

	    @Id
	    @ManyToOne
	    private User user;
	    
	    private Boolean isUsersDefaultTeam;
	    
	    private String role;
	    
	    
		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public Team getTeam() {
			return team;
		}

		public void setTeam(Team team) {
			this.team = team;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Boolean getIsUsersDefaultTeam() {
			return isUsersDefaultTeam;
		}

		public void setIsUsersDefaultTeam(Boolean isUsersDefaultTeam) {
			this.isUsersDefaultTeam = isUsersDefaultTeam;
		}
		
		@Override
	    public boolean equals(Object o) {
	        if ( this == o ) {
	            return true;
	        }
	        if ( o == null || getClass() != o.getClass() ) {
	            return false;
	        }
	        TeamUsers teamUser = (TeamUsers) o;
	        return Objects.equals( team, teamUser.team ) && Objects.equals( user, teamUser.user );
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash( team, user );
	    }
	    
	    
}
