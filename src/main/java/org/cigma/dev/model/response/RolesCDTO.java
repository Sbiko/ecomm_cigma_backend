package org.cigma.dev.model.response;

import java.util.List;

public class RolesCDTO {
	private String name;
	
	private List<AuthorityCDTO> authorities;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AuthorityCDTO> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityCDTO> authorities) {
		this.authorities = authorities;
	}
	
	
	

}
