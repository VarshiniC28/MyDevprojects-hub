package com.varshini.journalapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.varshini.journalapp.models.JournalModels;
import com.varshini.journalapp.models.User;
import com.varshini.journalapp.repository.JournalEntryRepository;
import com.varshini.journalapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service //holds business logic of our app
public class UserService {
	@Autowired
	private UserRepository userRepository; //dependency injection of repo in service 

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	//Its a method created to save - save()
	//Manually adding ADMIN in db
	public void saveNewUser(User user) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    if (user.getRoles() == null || user.getRoles().isBlank()) {
	        user.setRoles("USER"); // default if not explicitly set
	    }
	    userRepository.save(user);
	}
	
	//While saving user add as admin
	public void saveAdmin(User user) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    if (user.getRoles() == null || user.getRoles().isBlank()) {
	        user.setRoles("USER,ADMIN"); // default if not explicitly set
	    }
	    userRepository.save(user);
	}

	//Save method to add passwordEncoder
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	//Now to get all entries we use findAll()
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	//Now to get the entry by its id:  - findById()
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
		
	}
	
	 @Transactional
	    public void deleteByUsername(String username) {
	        userRepository.deleteByUsername(username);
	    }
	
	//To delete by id   -deleteById()f
	public void deleteById(Long id) {
		userRepository.deleteById(id);  //This method in JPA repo doesnt return anything
	}
}
