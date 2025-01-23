package com.example.hotelmanagement.service;

import com.example.hotelmanagement.models.Guest;
import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.dto.GuestDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GuestService {

    Page<Guest> getGuests(int page,int size);
    List<Guest> getAllGuests();
    Optional<Guest> getGuestById(Long id);

    Guest saveGuestAdmin(String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality, List<Long> reservationIds);

    Guest updateGuestAdmin(Long id, String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality, List<Long> reservationIds);

    Guest saveGuest(String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality);

    Guest updateGuest(Long id, String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality);


    Optional<Guest> deleteGuestById(Long id);

   Guest saveGuestApi(GuestDTO guestDTO);

    Guest updateGuestApi(Long id, GuestDTO guestDTO);

    void deleteGuest(Long id);

    Optional<Guest> findByEmail(String email);
    Optional<Guest> findByPhone(String phone);

    Page<Guest> getAllGuestsBySpecification(String email, String phone, int page, int size);

}
