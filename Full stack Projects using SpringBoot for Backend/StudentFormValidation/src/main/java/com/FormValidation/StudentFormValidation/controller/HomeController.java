package com.FormValidation.StudentFormValidation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FormValidation.StudentFormValidation.dto.StudentDTO;
import com.FormValidation.StudentFormValidation.models.StudentForm;
import com.FormValidation.StudentFormValidation.repository.StudentRepository;
import com.FormValidation.StudentFormValidation.service.StudentService;

import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	private final StudentService studentService;
	private final StudentRepository studentRepository;
	//constructor injection
	public HomeController(StudentService studentService, StudentRepository studentRepository) {
		super();
		this.studentService = studentService;
		this.studentRepository = studentRepository;
	}

	@GetMapping({"","/"})
	public String Home(Model model) { //here "home" is string so return type is string
		List<StudentForm> students = studentService.getAllStudents();
		model.addAttribute("students" ,students);
		return "home";
	}

	@GetMapping({"/add-student"})
	public String addStudent(Model model) {
		model.addAttribute("studentDTO", new StudentDTO());
		return "add_student";
	}
	
	@PostMapping("/add-student")
		public String addStudent(@Valid @ModelAttribute StudentDTO studentDTO, BindingResult result ,Model model, RedirectAttributes  attributes) {
		StudentForm student = studentRepository.findByEmail(studentDTO.getEmail());
		if(student!= null) {
			result.addError(new FieldError("StudentDTO", "email", "Email is already taken"));
		}
		if(studentDTO.getImage().isEmpty()) {
			result.addError((new FieldError("StudentDTO","image","Image is required")));
		}
		if(result.hasErrors()) {
			return "add_student";
		}
			System.out.println(studentDTO.getName()+"2");
			studentService.saveStudent(studentDTO);
			attributes.addFlashAttribute("success","Student added susccessfully");
			return "redirect:/";
		}
	
	@GetMapping("/std-delete")
	public String deleteStudent(@RequestParam Long rollNo, RedirectAttributes attributes) {
		studentService.deleteStudent(rollNo);
		attributes.addFlashAttribute("success","Deleted student susccessfully");
		return "redirect:/";
	}
	
	@GetMapping("/std-edit")
	public String editStudent(@RequestParam Long rollNo, Model model) {
		StudentDTO studentDTO = studentService.editStudent(rollNo);
		StudentForm student = studentRepository.findById(rollNo).get(); //get() is optional class
		model.addAttribute("studentDTO",studentDTO);
		model.addAttribute("student", student);
		return "edit-student";
	}
	
	@PostMapping("/edit-student")
	public String updateStudent(@Valid @ModelAttribute StudentDTO studentDTO,@RequestParam Long rollNo, BindingResult result , Model model,RedirectAttributes attributes) {
		StudentForm student1 = studentRepository.findByEmail(studentDTO.getEmail());
		if(student1!= null && student1.getRollNo()!=rollNo) {
			result.addError(new FieldError("StudentDTO", "email", "Email is already taken"));
		}
		if(result.hasErrors()) {
			StudentForm student = studentRepository.findById(rollNo).get();
			model.addAttribute("student" ,student);
			return "edit-student";
		}
		studentService.updateStudent(studentDTO,rollNo);
		attributes.addFlashAttribute("success","Edited successfully");
		return "redirect:/";
	}
}