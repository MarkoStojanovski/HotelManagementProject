package com.example.hotelmanagement.service.specification;

import com.example.hotelmanagement.models.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecification {

    public static Specification<Employee> buildSpecification(String email, String name, LocalDate dateOfBirth){
        return Specification.where(withEmail(email))
                .and(withName(name))
                .and(withDateOfBirth(dateOfBirth));
    }

    private static Specification<Employee> withDateOfBirth(LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) -> {
            if( dateOfBirth == null ){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"),dateOfBirth);
        };
    }

    private static Specification<Employee> withName(String name) {
        return (root, query, criteriaBuilder) -> {
            if( name == null || name.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" +  name.toLowerCase() + "%");
        };
    }

    private static Specification<Employee> withEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if( email == null || email.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%" + email.toLowerCase() + "%");
        };
    }


}
