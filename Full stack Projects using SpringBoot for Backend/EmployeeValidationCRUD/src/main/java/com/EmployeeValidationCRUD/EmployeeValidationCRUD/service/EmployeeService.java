package com.EmployeeValidationCRUD.EmployeeValidationCRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EmployeeValidationCRUD.EmployeeValidationCRUD.dto.EmployeeDTO;
import com.EmployeeValidationCRUD.EmployeeValidationCRUD.models.EmployeeModel;
import com.EmployeeValidationCRUD.EmployeeValidationCRUD.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<EmployeeModel> getAllEmployees() {
		List<EmployeeModel> employees = employeeRepository.findAll();
		return employees;
	}
	
	public void saveEmployee(EmployeeDTO employeeDTO){
		EmployeeModel employee = new EmployeeModel();
		employee.setName(employeeDTO.getName());
		employee.setAge(employeeDTO.getAge());
		employee.setEmail(employeeDTO.getEmail());
		employee.setAddress(employeeDTO.getAddress());
		employee.setPassword(employeeDTO.getPassword());
		employeeRepository.save(employee);
	}
	
	public void deleteEmployee(Long id) {
	    EmployeeModel employee = employeeRepository.findById(id).get();
	    employeeRepository.delete(employee);
	}

	public Optional<EmployeeModel> findById(Long id) {
		return employeeRepository.findById(id);
	}
	
	public EmployeeDTO editEmployee(Long id) {
		EmployeeModel employee = employeeRepository.findById(id).get() ;
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setName(employee.getName());
		employeeDTO.setAge(employee.getAge());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setAddress(employee.getAddress());
		employeeDTO.setPassword(employee.getPassword());
		return employeeDTO;
	}
	
	public void updateEmployee(EmployeeDTO employeeDTO, Long id ) {
		EmployeeModel employee = employeeRepository.findById(id).get() ;
		employee.setName(employeeDTO.getName());
		employee.setAge(employeeDTO.getAge());
		employee.setEmail(employeeDTO.getEmail());
		employee.setAddress(employeeDTO.getAddress());
		employee.setPassword(employeeDTO.getPassword());
		employeeRepository.save(employee);
	}
	
}
