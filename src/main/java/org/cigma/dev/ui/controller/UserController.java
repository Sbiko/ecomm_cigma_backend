package org.cigma.dev.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.cigma.dev.model.request.UserDetailsRequestModel;
import org.cigma.dev.model.response.DeleteOperationStatus;
import org.cigma.dev.model.response.RequestOperationName;
import org.cigma.dev.model.response.RequestOperationStatus;
import org.cigma.dev.model.response.UserRest;
import org.cigma.dev.service.UserService;
import org.cigma.dev.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/v0/users")
//@CrossOrigin("*")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
    ModelMapper modelMapper;

	
	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
	@GetMapping("/{id}")
	public ResponseEntity<UserRest> getUser(@PathVariable String id ) {
		//UserRest returnValue = new UserRest();
		
		UserDto userDto = userService.getUserByUserId(id);
		UserRest returnValue = modelMapper.map(userDto, UserRest.class);

		//BeanUtils.copyProperties(userDto, returnValue);
		return ResponseEntity.ok(returnValue);
	}
	
	@PostMapping
	public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		UserRest returnValue = modelMapper.map(createdUser, UserRest.class);
		return ResponseEntity.ok(returnValue);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
	@PutMapping("/{id}")
	public ResponseEntity<UserRest> updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto updatedUser = userService.updateUser(id, userDto);
		UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);
		
		return ResponseEntity.ok(returnValue);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<UserRest>> getUsers(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="limit", defaultValue="25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);
		
		for(UserDto userDto: users) {
			UserRest userRest = new UserRest();
			BeanUtils.copyProperties(userDto, userRest);
			returnValue.add(userRest);

		}
		return ResponseEntity.ok(returnValue);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
	@DeleteMapping("/{id}")
	public DeleteOperationStatus delateUser(@PathVariable String id) {
		DeleteOperationStatus returnValue = new DeleteOperationStatus();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
}
