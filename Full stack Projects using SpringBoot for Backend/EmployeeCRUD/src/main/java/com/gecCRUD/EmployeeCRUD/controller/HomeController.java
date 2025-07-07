package com.gecCRUD.EmployeeCRUD.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gecCRUD.EmployeeCRUD.dto.EmployeeDTO;
import com.gecCRUD.EmployeeCRUD.models.Employee;
import com.gecCRUD.EmployeeCRUD.service.EmployeeService;

@Controller
public class HomeController {
	
	private final EmployeeService employeeService;
	
	public HomeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@GetMapping({"", "/"})
	public String getAllEmployees(Model model) {
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("employees", employees);
		return "employees";
	}
	
	@GetMapping("/add-employee")
	public String addEmployee(Model model) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		model.addAttribute("employeeDTO", employeeDTO);
		return "add_employee";
	}

	@PostMapping("/add-employee")
	public String saveEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
		employeeService.saveEmployee(employeeDTO);
		return "redirect:/";
	}
	
	@GetMapping("/edit-employee")
	public String getEmployee(@RequestParam Long id, Model model) {
		Employee employee = employeeService.getEmployee(id);
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setfName(employee.getfName());
		employeeDTO.setlName(employee.getlName());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setPhone(employee.getPhone());
		employeeDTO.setAddress(employee.getAddress());
		model.addAttribute("employeeDTO", employeeDTO); // Form fields
		model.addAttribute("employee", employee); // For ID
		return "edit-employee";
	}

	@PostMapping("/edit-employee")
	public String updateEmployee(@ModelAttribute EmployeeDTO employeeDTO, @RequestParam Long id) {
		employeeService.updateEmployee(employeeDTO, id);
		return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam Long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/";
	}
}
