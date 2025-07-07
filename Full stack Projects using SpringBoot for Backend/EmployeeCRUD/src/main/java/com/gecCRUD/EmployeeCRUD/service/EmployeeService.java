package com.gecCRUD.EmployeeCRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gecCRUD.EmployeeCRUD.dto.EmployeeDTO;
import com.gecCRUD.EmployeeCRUD.models.Employee;
import com.gecCRUD.EmployeeCRUD.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}
	
	// to get list of employees
	public List<Employee> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	public void saveEmployee(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		employee.setfName(employeeDTO.getfName());
		employee.setlName(employeeDTO.getlName());
		employee.setEmail(employeeDTO.getEmail());
		employee.setPhone(employeeDTO.getPhone());
		employee.setAddress(employeeDTO.getAddress());
		employeeRepository.save(employee);
	}

	public Employee getEmployee(Long id) {
		Employee employee = employeeRepository.findById(id).get();
		return employee;
	}

	public void updateEmployee(EmployeeDTO employeeDTO, Long id) {
	    Employee employee = employeeRepository.findById(id).get();  
	    employee.setfName(employeeDTO.getfName());
	    employee.setlName(employeeDTO.getlName());
	    employee.setEmail(employeeDTO.getEmail());
	    employee.setPhone(employeeDTO.getPhone());
	    employee.setAddress(employeeDTO.getAddress());
	    employeeRepository.save(employee);  
	}

	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
}
