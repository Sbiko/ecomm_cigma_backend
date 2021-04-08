package org.cigma.dev.service;

import java.util.List;

import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

	FeedbackMessage createUser(UserDto user);
	UserDto getUser(String nickanme);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto user);
	List<UserDto> getUsers(int page, int limit);
	void deleteUser(String userId);
	boolean requestPasswordReset(String email);
	boolean resetPassword(String token);
	boolean isUserLogged(String userId);
}
