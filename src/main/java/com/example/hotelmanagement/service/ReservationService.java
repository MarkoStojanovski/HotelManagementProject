package com.example.hotelmanagement.service;

import com.example.hotelmanagement.models.Guest;
import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.dto.ReservationDTO;
import com.example.hotelmanagement.models.enumeration.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<Reservation> getAllReservations();
    Optional<Reservation> getReservationById(Long id);

    Page<Reservation> getReservations(int page,int size);
    Reservation saveReservation(String reservationNumber, LocalDate checked_in, LocalDate checked_out, ReservationStatus reservationStatus, Double totalPrice, Long hotelId, Long roomId,String guestEmail);



    Reservation updateReservation(Long id, String reservationNumber, LocalDate checked_in, LocalDate checked_out, ReservationStatus reservationStatus, Double totalPrice, Long hotelId, Long roomId, String guestEmail);

    Reservation saveReservationApi(ReservationDTO reservationDTO);
    Optional<Reservation> findByGuestEmail(String email);



    Page<Reservation> findByReservationStatus(ReservationStatus reservationStatus, int page,int size);

    Page<Reservation> findByReservationSpecification(String reservationStatus, int page, int size);

    Reservation updateReservationApi(Long id, ReservationDTO reservationDTO);


    Optional<Reservation> deleteReservation(Long id);


}
