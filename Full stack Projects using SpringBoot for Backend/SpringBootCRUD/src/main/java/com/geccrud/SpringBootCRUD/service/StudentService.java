package com.geccrud.SpringBootCRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.geccrud.SpringBootCRUD.dto.StudentDTO;
import com.geccrud.SpringBootCRUD.models.Student;
import com.geccrud.SpringBootCRUD.repository.StudentRepository;
@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	
	
	
	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}
	
	//to get list of students
	public List<Student> getAllStudents() {
		List<Student> students = studentRepository.findAll();
		return students;
	}

	public void saveStudent(StudentDTO studentDTO) {
		Student student = new Student();
		student.setfName(studentDTO.getfName());
		student.setlName(studentDTO.getlName());
		student.setEmail(studentDTO.getEmail());
		student.setPhone(studentDTO.getPhone());
		student.setAddress(studentDTO.getAddress());
		studentRepository.save(student);
	}

	public Student getStudent(Long id) {
		Student student =studentRepository.findById(id).get();
		return student;
	}

	public void updateStudent(StudentDTO studentDTO, Long id) {
	    Student student = studentRepository.findById(id).get();  
	    student.setfName(studentDTO.getfName());
	    student.setlName(studentDTO.getlName());
	    student.setEmail(studentDTO.getEmail());
	    student.setPhone(studentDTO.getPhone());
	    student.setAddress(studentDTO.getAddress());
	    studentRepository.save(student);  
	}
	

	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}
	
}