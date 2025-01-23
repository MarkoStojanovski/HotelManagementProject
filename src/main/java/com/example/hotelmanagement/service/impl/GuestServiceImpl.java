package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.models.Guest;
import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.dto.GuestDTO;
import com.example.hotelmanagement.models.exception.*;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.repository.ReservationRepository;

import com.example.hotelmanagement.service.GuestService;
import com.example.hotelmanagement.service.specification.GuestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;



    public GuestServiceImpl(GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Page<Guest> getGuests(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.guestRepository.findAll(pageable);
    }

    @Override
    public List<Guest> getAllGuests() {
        return this.guestRepository.findAll();
    }


    @Override
    public Optional<Guest> getGuestById(Long id) {
        if (this.guestRepository.findById(id).isPresent()) {
            return this.guestRepository.findById(id);
        } else {
            throw new NoGuestException(id);
        }
    }

    @Override
    @Transactional
    public Guest saveGuestAdmin(String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality, List<Long> reservationIds) {
        List<Reservation> reservations = this.reservationRepository.findAllById(reservationIds);
        if (reservations.isEmpty()) {
            throw new NoReservationsException("No Reservations Found.");
        }
        Guest guest = new Guest(name, surname, date_of_birth, phone, email,nationality, reservations);

        try {
            return this.guestRepository.save(guest);
        } catch (Exception exception) {
            throw new GuestSaveException("Failed to Save Guest, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Guest updateGuestAdmin(Long id, String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality, List<Long> reservationIds) {
        Guest guest = this.guestRepository.findById(id).orElseThrow(() -> new NoGuestException(id));
        List<Reservation> reservations = this.reservationRepository.findAllById(reservationIds);
        if (reservations.isEmpty()) {
            throw new NoReservationsException("No Reservations Found.");
        }

        guest.setReservations(reservations);
        guest.setName(name);
        guest.setSurname(surname);
        guest.setEmail(email);
        guest.setNationality(nationality);
        guest.setDate_of_birth(date_of_birth);
        guest.setPhone(phone);
        try {
            return this.guestRepository.save(guest);
        } catch (Exception exception) {
            throw new GuestSaveException("Failed to Update Guest, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Guest saveGuest(String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality) {
        Guest guest = new Guest(name, surname, date_of_birth, phone, email,nationality);

        try {
            return this.guestRepository.save(guest);
        } catch (Exception exception) {
            throw new GuestSaveException("Failed to Save Guest, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Guest updateGuest(Long id, String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality) {
        Guest guest = this.guestRepository.findById(id).orElseThrow(() -> new NoGuestException(id));
        guest.setName(name);
        guest.setSurname(surname);
        guest.setEmail(email);
        guest.setNationality(nationality);
        guest.setDate_of_birth(date_of_birth);
        guest.setPhone(phone);
        try {
            return this.guestRepository.save(guest);
        } catch (Exception exception) {
            throw new GuestSaveException("Failed to Update Guest, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Guest saveGuestApi(GuestDTO guestDTO) {
        if (guestDTO == null) {
            throw new IllegalArgumentException("GuestDTO cannot be null");
        }
        List<Reservation> reservations = this.reservationRepository.findAllById(guestDTO.getReservationIds());
        if (reservations.isEmpty()) {
            throw new NoReservationsException("No Reservations Found.");
        }
        Guest guest = new Guest(guestDTO.getName(),
                guestDTO.getSurname(),
                guestDTO.getNationality(),
                guestDTO.getPhone(),
                guestDTO.getDate_of_birth(),
                guestDTO.getEmail(),
                reservations);
        try {
            return this.guestRepository.save(guest);
        } catch (Exception exception) {
            throw new GuestSaveException("Failed to update Guest, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Guest updateGuestApi(Long id, GuestDTO guestDTO) {
        if (guestDTO == null) {
            throw new IllegalArgumentException("GuestDTO cannot be null");
        }
        Guest guest = this.guestRepository.findById(id).orElseThrow(() -> new NoGuestException(id));
        List<Reservation> reservations = this.reservationRepository.findAllById(guestDTO.getReservationIds());
        if (reservations.isEmpty()) {
            throw new NoReservationsException("No Reservations Found.");
        }
        guest.setName(guestDTO.getName());
        guest.setSurname(guestDTO.getSurname());
        guest.setNationality(guestDTO.getNationality());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhone(guestDTO.getPhone());
        guest.setDate_of_birth(guestDTO.getDate_of_birth());
        guest.setReservations(reservations);
        try {
            return this.guestRepository.save(guest);
        } catch (Exception exception) {
            throw new GuestSaveException("Failed to save Guest, something is not valid.");
        }
    }

    @Override
    public void deleteGuest(Long id) {
        Guest guest = this.guestRepository.findById(id).orElseThrow(()-> new NoGuestException(id));
        this.guestRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Guest> findByEmail(String email) {
        Optional<Guest> optionalGuest = this.guestRepository.findByEmail(email);
        return optionalGuest;
    }

    @Override
    @Transactional
    public Optional<Guest> findByPhone(String phone) {
        Optional<Guest> optionalGuest = this.guestRepository.findByPhone(phone);
        return optionalGuest;
    }


    @Override
    public Page<Guest> getAllGuestsBySpecification(String email, String phone, int page, int size) {
        Specification<Guest> specification = GuestSpecification.buildSpecification(email, phone);
        Pageable pageable = PageRequest.of(page, size);
        return this.guestRepository.findAll(specification,pageable);
    }


    @Override
    @Transactional
    public Optional<Guest> deleteGuestById(Long id) {
        Optional<Guest> optionalGuest = this.guestRepository.findById(id);
        if (optionalGuest.isEmpty()) {
            throw new NoGuestException(id);
        }
        this.guestRepository.deleteById(id);
        return optionalGuest;
    }


}
