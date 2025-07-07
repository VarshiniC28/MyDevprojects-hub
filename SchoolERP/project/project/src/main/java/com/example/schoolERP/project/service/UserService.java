package com.example.schoolERP.project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.schoolERP.project.dto.UserDTO;
import com.example.schoolERP.project.model.User;
import com.example.schoolERP.project.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	public void StoreRegisteredUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		 if (userDTO.getRole() != null && 
			        (userDTO.getRole().equals("ROLE_ADMIN") || userDTO.getRole().equals("ROLE_FACULTY") || userDTO.getRole().equals("ROLE_STUDENT"))) {
			        user.setRole(userDTO.getRole());
			    } else {
			        user.setRole("ROLE_STUDENT"); 
			    }
		userRepository.save(user);
	}
}
