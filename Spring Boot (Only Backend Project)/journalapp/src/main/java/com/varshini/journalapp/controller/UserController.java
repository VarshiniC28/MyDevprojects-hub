package com.varshini.journalapp.controller;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varshini.journalapp.models.JournalModels;
import com.varshini.journalapp.models.User;
import com.varshini.journalapp.repository.UserRepository;
import com.varshini.journalapp.service.JournalEntryService;
import com.varshini.journalapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
//	@GetMapping
//	public List<User> geAllUsers(User user){
//		return userService.getAll();
//	} //Will be not there in real world application since we will do it only for admin.

	
	//Moved to  publicController since this should be not authenticted and can be accesses by anyone.
//	@PostMapping
//	public void createUser(@RequestBody User user ) {
//		userService.SaveEntry(user);
//	}
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) { // Receives a User object from the request body (JSON -> User)
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	String username = authentication.getName();
	User userIndb = userService.findByUsername(username); // Looks for an existing user in the database using the provided username
//	    if(userIndb != null) { // Checks if a user with that username exists (i.e., not null)
	userIndb.setUsername(user.getUsername()); // Updates the username of the existing user with the new one from the request
	userIndb.setPassword(user.getPassword()); // Updates the password of the existing user with the new one from the request
	userService.saveNewUser(userIndb); // Saves the updated user back to the database (update operation)
//	    }
	return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns HTTP 204 No Content as a response (indicating success but no body)
	}

	
	@DeleteMapping
	public ResponseEntity<?> deleteByUsername(){
	    Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication.getName() == null) {
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Or other appropriate error code
	    }
	    userService.deleteByUsername(authentication.getName());
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	
}
