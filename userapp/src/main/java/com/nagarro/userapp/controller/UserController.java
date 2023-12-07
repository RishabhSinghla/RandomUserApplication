package com.nagarro.userapp.controller;

/**
 * @author rishabhsinghla
 * User controller class for creating and getting user
 */

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.userapp.model.User;
import com.nagarro.userapp.service.UserService;
import com.nagarro.userapp.validator.Numeric;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<List<User>> createUser(@RequestParam @Numeric int size) {
		List<User> users = userService.generateRandomUsers(size);
		users = userService.verifyUsers(users);
		userService.saveUsersToDB(users);
		return ResponseEntity.ok(users);
	}

	@GetMapping
	public ResponseEntity<List<User>> getUsers(@RequestParam String sortType, @RequestParam String sortOrder,
			@RequestParam @Numeric int limit, @RequestParam @Min(0) int offset) {
		List<User> users = userService.getUsersFromDB(sortType, sortOrder, limit, offset);
		return ResponseEntity.ok(users);
	}
}
