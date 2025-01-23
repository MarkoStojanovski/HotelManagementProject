package com.example.hotelmanagement.service;


import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.dto.EmployeeDTO;
import com.example.hotelmanagement.models.enumeration.EmployeeStatus;
import com.example.hotelmanagement.models.enumeration.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(String name, String surname, LocalDate date_of_birth, Gender gender, String phone, String email, EmployeeStatus employeeStatus, Double salary, Long hotelId);
    Employee saveEmployeeApi(EmployeeDTO employeeDTO);

    public Page<Employee> getEmployees(int page,int size);

    Optional<Employee> getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Long id, String name, String surname, LocalDate date_of_birth, Gender gender, String phone, String email, EmployeeStatus employeeStatus, Double salary, Long hotelId);
    Employee updateEmployeeApi(Long id, EmployeeDTO employeeDTO);
    Optional<Employee> deleteEmployee(Long id);

    Optional<Employee> findByPhone(String phone);
    Optional<Employee> findByEmail(String email);

    Page<Employee> getAllEmployeesBySpecification(String email, String phone, LocalDate dateOfBirth, int page, int size);
}
