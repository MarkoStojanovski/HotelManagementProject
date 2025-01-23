package com.example.hotelmanagement.web.API;

import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.dto.EmployeeDTO;
import com.example.hotelmanagement.models.exception.NoEmployeeFoundException;
import com.example.hotelmanagement.service.EmployeeService;
import com.example.hotelmanagement.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    private final HotelService hotelService;

    public EmployeeRestController(EmployeeService employeeService, HotelService hotelService) {
        this.employeeService = employeeService;
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = this.employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        return this.employeeService.getEmployeeById(id).map(employee -> ResponseEntity.ok().body(employee))
                .orElseThrow(()-> new NoEmployeeFoundException(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id){
        return this.employeeService.deleteEmployee(id).map(employee -> ResponseEntity.ok().body(employee))
                .orElseThrow(()-> new NoEmployeeFoundException(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(this.employeeService.saveEmployeeApi(employeeDTO));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(this.employeeService.updateEmployeeApi(id,employeeDTO));
    }

}
