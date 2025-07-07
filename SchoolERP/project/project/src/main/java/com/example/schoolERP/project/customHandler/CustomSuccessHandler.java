package com.example.schoolERP.project.customHandler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			
		var authorities = authentication.getAuthorities();
		if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			response.sendRedirect("/admin_dashboard");
		}else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_FACULTY"))){
			response.sendRedirect("/faculty_dashboard");
		}else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))){
			response.sendRedirect("/student_dashboard");
		}
//		else {
//			response.sendRedirect("/error");
//		};
	}
	
}
