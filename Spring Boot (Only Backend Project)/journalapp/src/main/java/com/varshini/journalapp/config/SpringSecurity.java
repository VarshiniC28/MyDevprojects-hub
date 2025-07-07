package com.varshini.journalapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.varshini.journalapp.service.UserDetailsServiceImpl;


@Configuration //We want to define a bean inside so make it configuration
//@EnableWebSecurity // No longer required in SpringBoot 3/6 but still can use //Now, Spring Security auto-detects your security configuration through the SecurityFilterChain bean.
//This above annotation signals spring to enable web security support and this is what used make our application secured. and used in conjuction with @Configuration.

//public class SpringSecurity extends WebSecurityConfigurerAdapter{ //This class we were extending to is utility class in Spring security of older versions which used to provide default configuration and has config method in it which we were overriding in our class and it provides way to configure how requests are secured .

//You should not extend any class now. Instead, just define beans — mainly SecurityFilterChain and AuthenticationManager.

@EnableWebSecurity
public class SpringSecurity {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean // This tells Spring to manage this method's return value as a bean in the application context
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        // Start configuring HTTP security
	        .authorizeHttpRequests(request -> request
	            // Any request to URLs that start with /journal/ must be authenticated (user must log in) -> ** is a wild card pattern i.e url with journal followed by 1/more character must be authenticated 
	            .requestMatchers("/journal/**","/user/**").authenticated()
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            // All other requests (not matching /journal/**) are allowed without login
	            .anyRequest().permitAll()
	        )
	        // Enable HTTP Basic Authentication (browser popup will ask for username/password)
	        .httpBasic(Customizer.withDefaults())
	        // csrf -cross site request forgery usually needed in web forms for browsers, but for API/basic auth(testing and for personal projects) its often disabled
	        .csrf(AbstractHttpConfigurer::disable) //csrf - if enabled when user sends reuest , spring security excepts a csrf token which is unique per session and prvents attacks.
	        // Finalize and return the built SecurityFilterChain
	        .sessionManagement(session -> session
	        	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 👉 Tell Spring Security: Do NOT create or use an HTTP Session (no server-side login memory) for now. Each request must have credentials separately (good for APIs/JWT).
	        	)

	        .build();
	    
	}
	
	// Older version where we override this method, but in newer versions they have removed this approach
	// @Autowired
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	// }

	// Define a bean for password encoding (no change here)
	@Bean
	public PasswordEncoder passwordEncoder() {
	    // BCryptPasswordEncoder is used to hash passwords securely
	    return new BCryptPasswordEncoder();
	}
	

	// Newer version instead of using AuthenticationManagerBuilder directly
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Create a DaoAuthenticationProvider, which is responsible for authenticating users using a UserDetailsService
	    provider.setUserDetailsService(userDetailsService); // Set the custom UserDetailsService (to load user-specific data)
	    provider.setPasswordEncoder(passwordEncoder());   // Set the password encoder (to verify passwords securely)
	    return provider;   // Return the fully configured provider
	}

	// Define a bean for AuthenticationManager, which is used by Spring Security internally for authentication
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();  // Get and return the AuthenticationManager from AuthenticationConfiguration   == // (Spring automatically wires the DaoAuthenticationProvider into it)
	}


	
}
