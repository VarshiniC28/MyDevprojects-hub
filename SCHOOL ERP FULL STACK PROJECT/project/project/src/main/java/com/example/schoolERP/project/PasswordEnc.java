package com.example.schoolERP.project;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEnc {
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("varsha"));
	}
}
