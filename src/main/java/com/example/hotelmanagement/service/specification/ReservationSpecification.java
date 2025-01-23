package com.example.hotelmanagement.service.specification;

import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.enumeration.ReservationStatus;
import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification {

    public static Specification<Reservation> buildSpecification(String reservationStatus){
        return Specification.where(withReservationStatus(reservationStatus));
    }

    private static Specification<Reservation> withReservationStatus(String reservationStatus) {
        return (root, query, criteriaBuilder) -> {
            if(reservationStatus == null || reservationStatus.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("reservationStatus")),reservationStatus.toLowerCase());
        };
    }

}
