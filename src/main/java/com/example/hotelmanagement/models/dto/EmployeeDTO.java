package com.example.hotelmanagement.models.dto;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.enumeration.EmployeeStatus;
import com.example.hotelmanagement.models.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @JsonFormat(pattern = "yyyy-MM-dd")
//    @NotNull(message = "Date of birth cannot be blank")
    @Past(message = "Date of birth must be in the past")
    private LocalDate date_of_birth;

    private Gender gender;

    @NotBlank(message = "Gender cannot be blank")
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Employee Status cannot be blank")
    private EmployeeStatus employeeStatus;


    @NotNull(message = "Salary cannot be null")
    private Double salary;
    private Long hotelId;

    public EmployeeDTO(String name, String surname, LocalDate date_of_birth, Gender gender, String phone, String email, EmployeeStatus employeeStatus, Double salary, Long hotelId) {
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.employeeStatus = employeeStatus;
        this.salary = salary;
        this.hotelId = hotelId;
    }
}
