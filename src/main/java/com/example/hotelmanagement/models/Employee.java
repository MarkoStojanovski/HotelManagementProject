package com.example.hotelmanagement.models;


import com.example.hotelmanagement.models.enumeration.EmployeeStatus;
import com.example.hotelmanagement.models.enumeration.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "employees")
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status")
    private EmployeeStatus employeeStatus;

    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;


    public Employee(String name, String surname, LocalDate dateOfBirth, Gender gender, String phone, String email, EmployeeStatus employeeStatus, Double salary, Hotel hotel) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.employeeStatus = employeeStatus;
        this.salary = salary;
        this.hotel = hotel;
    }

    public Employee() {
    }

    public Employee(String name, String surname, LocalDate dateOfBirth, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
    }
}
