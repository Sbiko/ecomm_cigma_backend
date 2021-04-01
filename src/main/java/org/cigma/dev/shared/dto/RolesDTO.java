package org.cigma.dev.shared.dto;

import java.util.List;

public class RolesDTO {
	private String name;
	
	private List<AuthorityDTO> authorities;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AuthorityDTO> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityDTO> authorities) {
		this.authorities = authorities;
	}
	
	
	

}
