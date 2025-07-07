package com.example.schoolERP.project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.schoolERP.project.model.User;
import com.example.schoolERP.project.repository.UserRepository;

public class CustonUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public CustonUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with this email is not found: "+email));
		return new CustomUserDetails(user);
	}

}
