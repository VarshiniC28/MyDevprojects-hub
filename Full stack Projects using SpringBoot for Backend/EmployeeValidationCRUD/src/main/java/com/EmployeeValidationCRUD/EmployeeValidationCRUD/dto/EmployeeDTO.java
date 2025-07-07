package com.EmployeeValidationCRUD.EmployeeValidationCRUD.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotNull
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull
    @Min(value = 18, message = "Age must be above 18")
    private int age;

    @NotNull
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @NotBlank(message = "Password is required")
    private String password;

    @NotNull
    @NotBlank(message = "Address is required")
    private String address;

//    private MultipartFile image;  // Image upload field

//    private MultipartFile document; // Document upload field
}
