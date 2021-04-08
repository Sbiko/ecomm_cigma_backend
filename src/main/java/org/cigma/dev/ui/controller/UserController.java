package org.cigma.dev.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.cigma.dev.model.request.PasswordResetRequestCDTO;
import org.cigma.dev.model.request.UserDetailsRequestCDTO;
import org.cigma.dev.model.response.FeedbackMessage;
import org.cigma.dev.model.response.RequestOperationName;
import org.cigma.dev.model.response.RequestOperationStatus;
import org.cigma.dev.model.response.UserCDTO;
import org.cigma.dev.service.UserService;
import org.cigma.dev.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UserController {
	final String URL_USERS = "/api/v0/users";
	@Autowired
	UserService userService;
	
	@Autowired
    ModelMapper modelMapper;

	
	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
	@GetMapping(URL_USERS + "/{id}")
	public ResponseEntity<UserCDTO> getUser(@PathVariable String id ) {
		UserDto userDto = userService.getUserByUserId(id);
		UserCDTO returnValue = modelMapper.map(userDto, UserCDTO.class);
		return ResponseEntity.ok(returnValue);
	}
	
	@PostMapping(URL_USERS)
	public ResponseEntity<FeedbackMessage> createUser(@RequestBody UserDetailsRequestCDTO userDetails) {
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		FeedbackMessage returnValue = userService.createUser(userDto);
		return ResponseEntity.accepted().body(returnValue);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
	@PutMapping(URL_USERS +"/{id}")
	public ResponseEntity<UserCDTO> updateUser(@PathVariable String id, @RequestBody UserDetailsRequestCDTO userDetails) {
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto updatedUser = userService.updateUser(id, userDto);
		UserCDTO returnValue = modelMapper.map(updatedUser, UserCDTO.class);
		
		return ResponseEntity.ok(returnValue);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(URL_USERS)
	public ResponseEntity<List<UserCDTO>> getUsers(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="limit", defaultValue="25") int limit) {
		List<UserCDTO> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);
		
		for(UserDto userDto: users) {
			UserCDTO userRest = modelMapper.map(userDto, UserCDTO.class);
			returnValue.add(userRest);

		}
		return ResponseEntity.ok(returnValue);
	}
	
	@PostMapping(URL_USERS + "/password-reset-request")
	public ResponseEntity<FeedbackMessage> requestRest(@Valid @RequestBody PasswordResetRequestCDTO passwordResetRequestCDTO) {
		FeedbackMessage returnValue = new FeedbackMessage();
		boolean operationResult = userService.requestPasswordReset(passwordResetRequestCDTO.getEmail());
		returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if(operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}
		
		return ResponseEntity.ok(returnValue);
		
	}
	
	 @PostMapping(URL_USERS +  "/password-reset")
	    public ResponseEntity<FeedbackMessage> resetPassword(@RequestParam String token) {
		 FeedbackMessage returnValue = new FeedbackMessage();
	 
	        boolean operationResult = userService.resetPassword(token);
	        
	        returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
	        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
	 
	        if(operationResult)
	        {
	            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
	        }

	        return ResponseEntity.ok(returnValue);
	    }
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
	@DeleteMapping(URL_USERS +"/{id}")
	public FeedbackMessage deleteUser(@PathVariable String id) {
		FeedbackMessage returnValue = new FeedbackMessage();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping(URL_USERS + "/logged/{id}")
	public boolean isUserLogged(@PathVariable String id) {
		boolean returnValue = userService.isUserLogged(id);
		return returnValue;

	}
	
	
}
