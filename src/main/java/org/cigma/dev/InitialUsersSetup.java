package org.cigma.dev;

import java.util.Arrays;
import java.util.Collection;

import org.cigma.dev.model.entity.AuthorityEntity;
import org.cigma.dev.model.entity.RoleEntity;
import org.cigma.dev.model.entity.UserEntity;
import org.cigma.dev.repository.AuthorityRepository;
import org.cigma.dev.repository.RoleRepository;
import org.cigma.dev.repository.UserRepository;
import org.cigma.dev.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialUsersSetup {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

		createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
		RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("Adminstrateur");
		adminUser.setLastName("Adminstrateur");
		adminUser.setEmail("admin@demo.com");
		adminUser.setNickname("Addy");
		adminUser.setPhoneNumber("0011223355");
		adminUser.setUserId(utils.generateID(30));
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("test123"));
		adminUser.setRoles(Arrays.asList(roleAdmin));
		UserEntity user = userRepository.findByNickname(adminUser.getNickname());
		if (user != null)
			return;
		userRepository.save(adminUser);
	}

	private AuthorityEntity createAuthority(String name) {
		AuthorityEntity authority = authorityRepository.findByName(name);
		if (authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}

	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
		RoleEntity role = roleRepository.findByName(name);

		if (role == null) {
			role = new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}
		return role;
	}
}
