package com.FormValidation.StudentFormValidation.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class StudentDTO {
	@NotBlank(message = "Students name is required")
	@NotEmpty
	private String name;
	
	@Min(10)
	private int age;
	
	@NotBlank(message = "Students email is required") //or the below one
	@Email
	private String email;
	
	@NotBlank(message = "Students address is required")
	private String address;
	
	@NotBlank(message = "Students password is required")
	private String password;
	
	//Why we dont need to add validation to image is if its not mandatory
	private MultipartFile image;
	private MultipartFile document;
	
	
	public MultipartFile getDocument() {
		return document;
	}
	public void setDocument(MultipartFile document) {
		this.document = document;
	}
	//
	//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	
}