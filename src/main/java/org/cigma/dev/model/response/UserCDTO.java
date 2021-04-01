package org.cigma.dev.model.response;


import java.util.List;


public class UserCDTO {
	
	private String userId;
	private String lastName;
	private String firstName;
	private String email;
	private List<RolesCDTO> roles;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<RolesCDTO> getRoles() {
		return roles;
	}
	public void setRoles(List<RolesCDTO> roles) {
		this.roles = roles;
	}
	
	
}
