package com.varshini.journalapp.HealthCheck;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varshini.journalapp.models.User;
import com.varshini.journalapp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create-user")
	public void createUser(@RequestBody User user ) {
		userService.saveNewUser(user);
	}
	
	@GetMapping("/health-check")
	public String healthCheck() {
		return "OK";	
	}
}
