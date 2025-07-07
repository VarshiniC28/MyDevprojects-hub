package com.example.schoolERP.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.schoolERP.project.dto.UserDTO;
import com.example.schoolERP.project.model.User;
import com.example.schoolERP.project.service.UserService;

import org.springframework.ui.Model;


@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@GetMapping({"","/"})
	public String home() {
		return "home";
	}

	@GetMapping("/manage_classes")
	public String manageClasses() {
		return "admin/manage_classes";
	}
	
	@GetMapping("/manage_faculty")
	public String manageFaculty() {
		return "admin/manage_faculty";
	}
	
	@GetMapping("/manage_students")
	public String manageStudents() {
		return "manage_students";
	}
	
	@GetMapping("/login")
	public String Login() {
		return "login";
	}
	
	@GetMapping("/reports")
	public String reports() {
		return "reports";
	}
	
	@GetMapping("/faculty_dashboard")
	public String facultyDashboard() {
		return "faculty_dashboard";
	}
	
	@GetMapping("/student_dashboard")
	public String studentDashboard() {
		
		return "student_dashboard";
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		UserDTO userDTO = new UserDTO();
	    model.addAttribute("userDTO", new UserDTO());
	    return "register";
	}

	@PostMapping("/register")
	public String processRegistration(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes, Model model) {
	    try {
			userService.StoreRegisteredUser(userDTO);
			redirectAttributes.addFlashAttribute("success","Registered Successfully");
		} catch (Exception e) {
			model.addAttribute("error","Failed to add user");
			return "register";
		}
	    return "redirect:/login";
	}

}
