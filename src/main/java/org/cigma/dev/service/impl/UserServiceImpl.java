package org.cigma.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cigma.dev.model.entity.UserEntity;
import org.cigma.dev.model.response.ErrorMessages;
import org.cigma.dev.repository.UserRepository;
import org.cigma.dev.security.UserPrincipal;
import org.cigma.dev.service.UserService;
import org.cigma.dev.shared.Utils;
import org.cigma.dev.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("Record already exists..");
		}
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		String publicUserId = utils.generateUserId(30);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode("test123"));
		userEntity.setUserId(publicUserId);
		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByNickname(nickname);
		if(userEntity == null) throw new UsernameNotFoundException(nickname);
		
		//return new User(userEntity.getNickname(), userEntity.getEncryptedPassword(), new ArrayList<>());
		return new UserPrincipal(userEntity);
	}
	
	@Override
	public UserDto getUser(String nickanme) {
		UserEntity userEntity = userRepository.findByNickname(nickanme);

		if (userEntity == null)
			throw new UsernameNotFoundException(nickanme);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
 
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		UserEntity updateUserDetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updateUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		return returnValue; 
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userRepository.delete(userEntity);
	}
	
	

}
