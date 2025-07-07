package com.FormValidation.StudentFormValidation.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.FormValidation.StudentFormValidation.dto.StudentDTO;
import com.FormValidation.StudentFormValidation.models.StudentForm;
import com.FormValidation.StudentFormValidation.repository.StudentRepository;

@Service
public class StudentService {
	
	private StudentRepository studentRepository;
	
	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	public List<StudentForm> getAllStudents() {
		List<StudentForm> students = studentRepository.findAll();
		return students;
	}

	public void saveStudent(StudentDTO studentDTO) {
		MultipartFile image = studentDTO.getImage();
		Date createdAt = new Date();
		String storeImageName = createdAt.getTime() + "_" + image.getOriginalFilename();
		System.out.println("Storing image as: " + storeImageName);
		
		// Create the upload directory if it doesn't exist
		try {
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storeImageName), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				System.out.println("Error copying the file: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error creating directory: " + e.getMessage());
		}
		
		StudentForm student = new StudentForm();
		student.setName(studentDTO.getName());
		student.setEmail(studentDTO.getEmail());
		student.setAge(studentDTO.getAge());
		student.setPassword(studentDTO.getPassword());
		student.setAddress(studentDTO.getAddress());
		student.setImagePath(storeImageName);
		studentRepository.save(student);
	}

	public void deleteStudent(Long rollNo) {
		StudentForm student = studentRepository.findById(rollNo).get();
		// Delete the image file before deleting the record
		String uploadDir = "public/images/";
		Path imagePath = Paths.get(uploadDir + student.getImagePath());
		try {
			Files.delete(imagePath);
		} catch (Exception e) {
			System.out.println("Error deleting image: " + e.getMessage());
		}
		studentRepository.delete(student);
	}

	public StudentDTO editStudent(Long rollNo) {
		StudentForm student = studentRepository.findById(rollNo).get();
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setName(student.getName());
		studentDTO.setAge(student.getAge());
		studentDTO.setEmail(student.getEmail());
		studentDTO.setPassword(student.getPassword());
		studentDTO.setAddress(student.getAddress());
		return studentDTO;
	}

	public void updateStudent(StudentDTO studentDTO, Long rollNo) {
		StudentForm student = studentRepository.findById(rollNo).get();
		// For updating, change the image file if provided.
		if (!studentDTO.getImage().isEmpty()) {
			Path oldImagePath = Paths.get("public/images/" + student.getImagePath());
			try {
				Files.delete(oldImagePath);
			} catch (Exception e) {
				System.out.println("Error deleting old image: " + e.getMessage());
			}
			MultipartFile image = studentDTO.getImage();
			Date createdAt = new Date();
			String storeImageName = createdAt.getTime() + "_" + image.getOriginalFilename();
			String uploadDir = "public/images/";
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storeImageName), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				System.out.println("Error updating image file: " + e.getMessage());
			}
			student.setImagePath(storeImageName);
		}
		student.setName(studentDTO.getName());
		student.setEmail(studentDTO.getEmail());
		student.setAge(studentDTO.getAge());
		student.setPassword(studentDTO.getPassword());
		student.setAddress(studentDTO.getAddress());
		studentRepository.save(student);
	}
}
