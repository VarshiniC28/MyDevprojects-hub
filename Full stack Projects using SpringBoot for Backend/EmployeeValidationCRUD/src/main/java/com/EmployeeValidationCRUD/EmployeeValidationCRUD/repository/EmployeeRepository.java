package com.EmployeeValidationCRUD.EmployeeValidationCRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EmployeeValidationCRUD.EmployeeValidationCRUD.models.EmployeeModel;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long>{

	public EmployeeModel findByEmail(String Email);
}
