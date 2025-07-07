package com.EmployeeValidationCRUD.EmployeeValidationCRUD.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EmployeeValidationCRUD.EmployeeValidationCRUD.dto.EmployeeDTO;
import com.EmployeeValidationCRUD.EmployeeValidationCRUD.models.EmployeeModel;
import com.EmployeeValidationCRUD.EmployeeValidationCRUD.repository.EmployeeRepository;
import com.EmployeeValidationCRUD.EmployeeValidationCRUD.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String getAllEmployees(Model model) {
        List<EmployeeModel> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "home";
    }

    @GetMapping("/add-employee")
    public String addEmployee(Model model) {
        model.addAttribute("employeeDTO", new EmployeeDTO());
        return "add-employee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@Valid @ModelAttribute EmployeeDTO employeeDTO, BindingResult result, RedirectAttributes attributes) {
        EmployeeModel existingEmployee = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (existingEmployee != null) {
            result.addError(new FieldError("employeeDTO", "email", "Email already taken"));
        }

        if (result.hasErrors()) {
            return "add-employee";
        }

        employeeService.saveEmployee(employeeDTO);
        attributes.addFlashAttribute("success", "Employee added successfully");
        return "redirect:/";
    }

    @GetMapping("/delete-employee")
    public String deleteEmployee(@RequestParam Long id, RedirectAttributes attributes) {
        employeeService.deleteEmployee(id);
        attributes.addFlashAttribute("success", "Employee deleted successfully");
        return "redirect:/";
    }

    @GetMapping("/edit-employee")
    public String editEmployee(@RequestParam Long id, Model model) {
        EmployeeDTO employeeDTO = employeeService.editEmployee(id);
        EmployeeModel employee = employeeService.findById(id).get();
        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/edit-employee")
    public String updateEmployee(@RequestParam Long id, @Valid @ModelAttribute EmployeeDTO employeeDTO,
                                 BindingResult result, Model model, RedirectAttributes attributes) {

        EmployeeModel existingEmployee = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (existingEmployee != null && !existingEmployee.getId().equals(id)) {
            result.addError(new FieldError("employeeDTO", "email", "Email already taken"));
        }

        if (result.hasErrors()) {
            EmployeeModel existingEmp = employeeService.findById(id).get();
            model.addAttribute("employeeDTO", employeeDTO);
            model.addAttribute("employee", existingEmp);
            return "edit-employee";
        }

        employeeService.updateEmployee(employeeDTO, id);
        attributes.addFlashAttribute("success", "Employee updated successfully");
        return "redirect:/";
    }
}

