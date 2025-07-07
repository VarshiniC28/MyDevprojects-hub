package com.example.schoolERP.project.dto;

import com.example.schoolERP.project.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String name;
	private String email;
	private String password;
	private String role;
	
}
