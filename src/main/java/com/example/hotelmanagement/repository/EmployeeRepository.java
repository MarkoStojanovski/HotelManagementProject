package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> , JpaSpecificationExecutor {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByPhone(String phone);

    Page<Employee> findByEmailContaining(String email, Pageable pageable);
    Page<Employee> findByPhoneContaining(String phone,Pageable pageable);
    Page<Employee> findByName(String name,Pageable pageable);
    Page<Employee> findByDateOfBirthAfter(LocalDate dateOfBirth,Pageable pageable);

}
