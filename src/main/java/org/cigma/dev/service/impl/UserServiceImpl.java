package org.cigma.dev.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.cigma.dev.exceptions.UserServiceException;
import org.cigma.dev.model.entity.PasswordResetTokenEntity;
import org.cigma.dev.model.entity.RoleEntity;
import org.cigma.dev.model.entity.UserEntity;
import org.cigma.dev.model.response.CredentialsCDTO;
import org.cigma.dev.model.response.ErrorMessages;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.model.response.RequestOperationStatus;
import org.cigma.dev.repository.PasswordResetTokenRepository;
import org.cigma.dev.repository.RoleRepository;
import org.cigma.dev.repository.UserRepository;
import org.cigma.dev.security.UserPrincipal;
import org.cigma.dev.service.MailService;
import org.cigma.dev.service.UserService;
import org.cigma.dev.shared.Roles;
import org.cigma.dev.shared.Utils;
import org.cigma.dev.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public FeedbackMessage createUser(UserDto user) {
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserServiceException("Record already exists..");
		}
		
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		String publicUserId = utils.generateID(30);
		String password = utils.generateID(7);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(password));
		userEntity.setUserId(publicUserId);
		Collection<RoleEntity> roles = new HashSet<>();
		RoleEntity role = roleRepo.findByName(Roles.ROLE_USER.name());
		if(Optional.ofNullable(role).isPresent()) {
			roles.add(role);
		}
		
		userEntity.setRoles(roles);
		userRepository.save(userEntity);
		
		CredentialsCDTO credentials = modelMapper.map(userEntity, CredentialsCDTO.class);
		credentials.setPassword(password);
		
		mailService.sendUserCredentials(credentials);
	
	
		FeedbackMessage returnValue = new FeedbackMessage();
		returnValue.setOperationName("An email has been sent with your credentials");
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

	
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
	
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
 
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

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
			UserDto userDto = modelMapper.map(userEntity, UserDto.class);
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

	@Override
	public boolean requestPasswordReset(String email) {
		boolean returnValue = false;
		
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) {
			return returnValue;
		}
		
		String token = new Utils().generatePasswordResetToken(userEntity.getUserId());
		
		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserDetails(userEntity);
		PasswordResetTokenEntity savedPasswordResetTokenEntity = passwordResetTokenRepository.save(passwordResetTokenEntity);
		mailService.sendEmail(token, email);
		returnValue = Optional.ofNullable(savedPasswordResetTokenEntity).isPresent();
		return returnValue;
	}

	@Override
	public boolean resetPassword(String token) {
	     boolean returnValue = false;
	        
	        if( Utils.hasTokenExpired(token) )
	        {
	            return returnValue;
	        }
	 
	        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

	        if (passwordResetTokenEntity == null) {
	            return returnValue;
	        }

	        // Prepare new password
	        String password = utils.generateID(7);
	        String encodedPassword = bCryptPasswordEncoder.encode(password);
	        
	        // Update User password in database
	        UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
	        userEntity.setEncryptedPassword(encodedPassword);
	        UserEntity savedUserEntity = userRepository.save(userEntity);
	 
	        // Verify if password was saved successfully
	        if (savedUserEntity != null && savedUserEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
	            returnValue = true;
	        }
	   
	        // Remove Password Reset token from database
	        passwordResetTokenRepository.delete(passwordResetTokenEntity);
	        
	        CredentialsCDTO credentials = modelMapper.map(savedUserEntity, CredentialsCDTO.class);
			credentials.setPassword(password);
			mailService.sendUserCredentials(credentials);
		
		
	        
	        return returnValue;
	}
	
	

}
