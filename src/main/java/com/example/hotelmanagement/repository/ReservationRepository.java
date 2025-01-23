package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.enumeration.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> , JpaSpecificationExecutor {
    boolean existsByReservationNumber(String reservationNumber);
    Optional<Reservation> findByGuestEmail(String email);

    Page<Reservation> findByReservationStatus(ReservationStatus reservationStatus, Pageable pageable);
}
