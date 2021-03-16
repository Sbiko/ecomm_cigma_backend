package org.cigma.dev.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.cigma.dev.model.request.UserDetailsRequestModel;
import org.cigma.dev.model.response.UserRest;
import org.cigma.dev.service.UserService;
import org.cigma.dev.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserRest> getUser(@PathVariable String id ) {
		UserRest returnValue = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);
		return ResponseEntity.ok(returnValue);
	}
	
	@PostMapping
	public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		return ResponseEntity.ok(returnValue);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserRest> updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		return ResponseEntity.ok(returnValue);
	}
	
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
	
	@DeleteMapping
	public String delateUser() {
		return "delete user was called";
	}
}
