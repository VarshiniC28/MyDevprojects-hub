package com.example.schoolERP.project.config;

import java.security.Provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.example.schoolERP.project.customHandler.CustomSuccessHandler;
import com.example.schoolERP.project.repository.UserRepository;
import com.example.schoolERP.project.service.CustonUserDetailsService;

@Configuration
public class SecurityConfig {
	
	private UserRepository userRepository;
	private CustomSuccessHandler customSuccessHandler;
	public SecurityConfig(UserRepository userRepository, CustomSuccessHandler custonSuccessHandler) {
		super();
		this.userRepository = userRepository;
		this.customSuccessHandler = custonSuccessHandler;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustonUserDetailsService(userRepository);
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .authorizeHttpRequests(request -> request
	            .requestMatchers("/student_dashboard").hasAnyRole("STUDENT", "ADMIN", "FACULTY")
	            .requestMatchers("/faculty_dashboard", "/reports", "/manage_students").hasAnyRole("ADMIN", "FACULTY")
	            .requestMatchers("/manage_classes", "/manage_faculty", "/admin_dashboard").hasRole("ADMIN")
	            .requestMatchers("/", "/contact/", "/about", "/register").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .loginProcessingUrl("/login")
	            .usernameParameter("email")  
	            .successHandler(customSuccessHandler)
	            .permitAll()
	        )
	        .logout(logout->logout
	        		 .logoutUrl("/logout")
	                .logoutSuccessUrl("/login?logout")
					.permitAll())
	        .exceptionHandling(exp -> exp.accessDeniedPage("/error"))
	        .logout(logout -> logout.permitAll())
	        .build();
	}

}

