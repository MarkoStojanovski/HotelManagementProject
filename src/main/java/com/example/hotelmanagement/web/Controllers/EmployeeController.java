package com.example.hotelmanagement.web.Controllers;

import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.enumeration.EmployeeStatus;
import com.example.hotelmanagement.models.enumeration.Gender;
import com.example.hotelmanagement.service.EmployeeService;
import com.example.hotelmanagement.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeeController {


    private final EmployeeService employeeService;
    private final HotelService hotelService;

    public EmployeeController(EmployeeService employeeService, HotelService hotelService) {
        this.employeeService = employeeService;
        this.hotelService = hotelService;
    }


    @GetMapping("/employees")
    public String getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "3") int size,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                                  Model model) {
        Page<Employee> employeePage = this.employeeService.getAllEmployeesBySpecification(email,name,dateOfBirth,page,size);
        model.addAttribute("employeePage", employeePage);
        return "employee";
    }

    @GetMapping("/employee/details/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        Employee employee = this.employeeService.getEmployeeById(id).get();
        List<EmployeeStatus> employeeStatuses = Arrays.asList(EmployeeStatus.values());
        model.addAttribute("employee", employee);
        model.addAttribute("statuses", employeeStatuses);
        return "employeeDetails";
    }

    @GetMapping("/employee/add")
    public String addEmployee(Model model) {
        List<EmployeeStatus> employeeStatuses = Arrays.asList(EmployeeStatus.values());
        List<Gender> genders = Arrays.asList(Gender.values());
        model.addAttribute("genders", genders);
        model.addAttribute("statuses", employeeStatuses);
        model.addAttribute("hotels", this.hotelService.getAllHotels());
        return "employeeForm";
    }

    @PostMapping("/employee/delete/{id}")
    public String deleteEmployeeById(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/employee/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Employee employee = this.employeeService.getEmployeeById(id).get();
        List<EmployeeStatus> employeeStatuses = Arrays.asList(EmployeeStatus.values());
        List<Gender> genders = Arrays.asList(Gender.values());
        model.addAttribute("genders", genders);
        model.addAttribute("statuses", employeeStatuses);
        model.addAttribute("hotels", this.hotelService.getAllHotels());
        model.addAttribute("employee", employee);
        return "employeeForm";
    }

    @PostMapping("/employee")
    public String saveEmployee(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                               @RequestParam Gender gender,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam EmployeeStatus employeeStatus,
                               @RequestParam Double salary,
                               @RequestParam Long hotel, Model model) {
        if (this.employeeService.findByEmail(email).isPresent()) {
            model.addAttribute("emailError", "Email Already Exists");
            return "employeeForm";
        }
        if (this.employeeService.findByPhone(phone).isPresent()) {
            model.addAttribute("phoneError", "Phone Already Exists");
            return "employeeForm";
        }
        this.employeeService.saveEmployee(name, surname, dateOfBirth, gender, phone, email, employeeStatus, salary, hotel);
        return "redirect:/employees";
    }

    @PostMapping("/employee/{id}")
    public String saveEmployee(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                               @RequestParam Gender gender,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam EmployeeStatus employeeStatus,
                               @RequestParam Double salary,
                               @RequestParam Long hotel) {
        this.employeeService.updateEmployee(id, name, surname, dateOfBirth, gender, phone, email, employeeStatus, salary, hotel);
        return "redirect:/employees";
    }


}
