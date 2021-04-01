package org.cigma.dev.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.cigma.dev.model.entity.AuthorityEntity;
import org.cigma.dev.model.entity.RoleEntity;
import org.cigma.dev.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = -7789247707326083094L;

	UserEntity userEntity;
	private String userId;
	
	public UserPrincipal(UserEntity userEntity) {
		this.userEntity = userEntity;
		this.userId = userEntity.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<AuthorityEntity> authoritiesEntities = new ArrayList<>();
		
		//Get user Roles
		Collection<RoleEntity> roles = userEntity.getRoles();
		if(roles == null) return authorities;
		
		roles.forEach((role) -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			authoritiesEntities.addAll(role.getAuthorities());
		});
		
		authoritiesEntities.forEach((authorityEntity) -> {
			authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
		});
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.userEntity.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		return this.userEntity.getNickname();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
