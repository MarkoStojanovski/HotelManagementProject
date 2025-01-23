package com.example.hotelmanagement.service.specification;

import com.example.hotelmanagement.models.Room;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {

    public static Specification<Room> buildSpecification(Double pricePerNight){
        return Specification.where(withPricePerNightHigherThan(pricePerNight));
    }

    private static Specification<Room> withPricePerNightHigherThan(Double pricePerNight) {
        return (root, query, criteriaBuilder) -> {
            if(pricePerNight == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerNight"),pricePerNight);
        };
    }



}
