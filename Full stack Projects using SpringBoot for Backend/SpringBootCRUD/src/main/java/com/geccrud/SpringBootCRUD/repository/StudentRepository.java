package com.geccrud.SpringBootCRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geccrud.SpringBootCRUD.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}