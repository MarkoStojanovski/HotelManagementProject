package com.example.hotelmanagement.service.specification;

import com.example.hotelmanagement.models.Guest;
import org.springframework.data.jpa.domain.Specification;

public class GuestSpecification {

    public static Specification<Guest> buildSpecification(String email, String phone){
        return Specification.where(withEmail(email))
                .and(withPhone(phone));
    }

    private static Specification<Guest> withPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            if( phone == null || phone.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("phone"),phone);
        };
    }

    private static Specification<Guest> withEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if( email == null || email.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%" + email.toLowerCase() + "%");
        };
    }
}
