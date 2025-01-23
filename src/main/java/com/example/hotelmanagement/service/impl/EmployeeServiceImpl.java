package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.dto.EmployeeDTO;
import com.example.hotelmanagement.models.enumeration.EmployeeStatus;
import com.example.hotelmanagement.models.enumeration.Gender;
import com.example.hotelmanagement.models.exception.EmployeeSaveException;
import com.example.hotelmanagement.models.exception.NoEmployeeFoundException;
import com.example.hotelmanagement.models.exception.NoEmployeesException;
import com.example.hotelmanagement.models.exception.NoHotelException;
import com.example.hotelmanagement.repository.EmployeeRepository;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.service.EmployeeService;
import com.example.hotelmanagement.service.specification.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, HotelRepository hotelRepository) {
        this.employeeRepository = employeeRepository;
        this.hotelRepository = hotelRepository;
    }


    @Override
    @Transactional
    public Employee saveEmployee(String name, String surname, LocalDate date_of_birth, Gender gender, String phone, String email, EmployeeStatus employeeStatus, Double salary, Long hotelId) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(()-> new NoHotelException(hotelId));
        Employee employee = new Employee(name,surname,date_of_birth,gender,phone,
                email,employeeStatus,salary,hotel);
        try {
            return this.employeeRepository.save(employee);
        }catch (Exception exception){
            throw new EmployeeSaveException("Failed to save Employee, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Employee saveEmployeeApi(EmployeeDTO employeeDTO) {
        if(employeeDTO==null){
            throw new IllegalArgumentException("EmployeeDTO cannot be null");
        }
        Hotel hotel = this.hotelRepository.findById(employeeDTO.getHotelId()).orElseThrow(()->new NoHotelException(employeeDTO.getHotelId()));
        Employee employee = new Employee(
                employeeDTO.getName(),employeeDTO.getSurname(),employeeDTO.getDate_of_birth(),
                employeeDTO.getGender(), employeeDTO.getPhone(), employeeDTO.getEmail(),
                employeeDTO.getEmployeeStatus(),employeeDTO.getSalary(), hotel
        );
        try{
            return this.employeeRepository.save(employee);
        }catch (Exception exception){
            throw new EmployeeSaveException("Failed to save Employee, something is not valid.");
        }
    }

    @Override
    public Page<Employee> getEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.employeeRepository.findAll(pageable);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        if(this.employeeRepository.findById(id).isEmpty()){
            throw new NoEmployeeFoundException(id);
        }
        return this.employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
//        if(this.employeeRepository.findAll().isEmpty()){
//            throw new NoEmployeesException("No Employees found.");
//        }
        return this.employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long id, String name, String surname, LocalDate date_of_birth, Gender gender, String phone, String email, EmployeeStatus employeeStatus, Double salary, Long hotelId) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()){
            throw new NoEmployeeFoundException(id);
        }
        Employee updated_Employee = optionalEmployee.get();
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(()->new NoHotelException(hotelId));
        updated_Employee.setEmployeeStatus(employeeStatus);
        updated_Employee.setHotel(hotel);
        updated_Employee.setGender(gender);
        updated_Employee.setEmail(email);
        updated_Employee.setDateOfBirth(date_of_birth);
        updated_Employee.setPhone(phone);
        updated_Employee.setName(name);
        updated_Employee.setSurname(surname);
        updated_Employee.setSalary(salary);
        try {
            return this.employeeRepository.save(updated_Employee);
        }catch (Exception exception){
            throw new EmployeeSaveException("Failed to update Employee, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Employee updateEmployeeApi(Long id, EmployeeDTO employeeDTO) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(()-> new NoEmployeeFoundException(id));
        Hotel hotel = this.hotelRepository.findById(employeeDTO.getHotelId()).orElseThrow(()->new NoHotelException(employeeDTO.getHotelId()));
        employee.setEmployeeStatus(employeeDTO.getEmployeeStatus());
        employee.setGender(employeeDTO.getGender());
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDateOfBirth(employeeDTO.getDate_of_birth());
        employee.setEmail(employeeDTO.getEmail());
        employee.setHotel(hotel);
        try{
            return this.employeeRepository.save(employee);
        }catch (Exception exception){
            throw  new EmployeeSaveException("Failed to update Employee, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Optional<Employee> deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);
       if(optionalEmployee.isEmpty()){
           throw new NoEmployeeFoundException(id);
       }
       this.employeeRepository.deleteById(id);
       return optionalEmployee;
    }

    @Override
    public Optional<Employee> findByPhone(String phone) {
        return this.employeeRepository.findByPhone(phone);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return this.employeeRepository.findByEmail(email);
    }

    @Override
    public Page<Employee> getAllEmployeesBySpecification(String email, String phone, LocalDate dateOfBirth, int page, int size) {
        Specification<Employee> specification = EmployeeSpecification.buildSpecification(email, phone, dateOfBirth);
        Pageable pageable = PageRequest.of(page,size);
        return this.employeeRepository.findAll(specification,pageable);
    }

}
