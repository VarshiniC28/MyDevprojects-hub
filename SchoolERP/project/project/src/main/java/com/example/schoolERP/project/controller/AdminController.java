package com.example.schoolERP.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.schoolERP.project.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/admin_dashboard")
	public String adminDashboard() {
		return "admin/admin_dashboard";
	}
}
