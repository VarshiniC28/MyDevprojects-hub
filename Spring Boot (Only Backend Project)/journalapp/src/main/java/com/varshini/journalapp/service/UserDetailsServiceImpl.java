package com.varshini.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.varshini.journalapp.models.User;
import com.varshini.journalapp.repository.UserRepository;

@Service // Marks the class as a service component to be managed by Spring container
public class UserDetailsServiceImpl implements UserDetailsService{ // inbuilt interface UserDetailsService implemented by our class to override its following method which is required to our method

	@Autowired // Injects the UserRepository dependency to fetch user data from DB
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepository.findByUsername(username);
	    if(user != null) {
	        return org.springframework.security.core.userdetails.User.builder()
	            .username(user.getUsername())
	            .password(user.getPassword())
	            .roles(user.getRoles().split(","))  // Split comma separated roles to String array
	            .build();
	    }
	    throw new UsernameNotFoundException("User not found with this username: " + username);
	}


}
