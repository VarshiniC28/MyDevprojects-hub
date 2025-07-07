package com.FormValidation.StudentFormValidation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FormValidation.StudentFormValidation.models.StudentForm;

@Repository
public interface StudentRepository extends JpaRepository<StudentForm, Long> { //here StudenForm is models file name.
 //Long - datatype of primary data/field/ key in model - here Long Id
	/*
	 * spring data jpa -> jpa -> hibernate -> jdbc(java database connection)
	 * -> datatbase 
	 * 
	 * 1. to get all data -> finAll()
	 * 2. to get single data ->findById()
	 * 3. save(object) - To save 
	 * 4. delete() 
	 * 
	 * difference between jparepository and crud repository is pagination.
	 * Pagination: technique to retrieve the necessary data instead of loading entire dataset which takes lot of memory and causes redundancy.
	 * Linking one page to another
	 * */
	
	/**/
	public StudentForm findByEmail(String email); //Student
}
