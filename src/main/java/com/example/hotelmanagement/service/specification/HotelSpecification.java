package com.example.hotelmanagement.service.specification;

import com.example.hotelmanagement.models.Hotel;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecification {

    public static Specification<Hotel> buildSpecification(Double rating) {
        return Specification.where(withHigherRating(rating));
    }

    private static Specification<Hotel> withHigherRating(Double rating) {
        return (root, query, criteriaBuilder) -> {
            if (rating == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"),rating);
        };
    }

}
