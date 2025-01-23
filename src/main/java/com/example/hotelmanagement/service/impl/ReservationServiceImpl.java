package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.models.Guest;
import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.dto.ReservationDTO;
import com.example.hotelmanagement.models.enumeration.ReservationStatus;
import com.example.hotelmanagement.models.exception.*;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.repository.ReservationRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.service.ReservationService;
import com.example.hotelmanagement.service.specification.ReservationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, HotelRepository hotelRepository, RoomRepository roomRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    public double calculateTotalPrice(LocalDate checked_in, LocalDate checked_out, double price_per_night){
        if(checked_in!=null && checked_out!=null){
            long number_nights = ChronoUnit.DAYS.between(checked_in,checked_out);
            if(number_nights==0){
                return  price_per_night;
            }
            return number_nights * price_per_night;
        }
        return 0.0;
    }


    @Override
    public List<Reservation> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        if (this.reservationRepository.findById(id).isEmpty()) {
            throw new NoReservationFoundException(id);
        }
        return this.reservationRepository.findById(id);
    }

    @Override
    public Page<Reservation> getReservations(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.reservationRepository.findAll(pageable);
    }

    private String UniqueReservationNumber() {
        String reservationNumber;
        do {
            reservationNumber = UUID.randomUUID().toString();
        } while (reservationRepository.existsByReservationNumber(reservationNumber));
        return reservationNumber;
    }

    @Override
    @Transactional
    public Reservation saveReservation(String reservationNumber, LocalDate checked_in, LocalDate checked_out, ReservationStatus reservationStatus, Double totalPrice, Long hotelId, Long roomId, String guestEmail) {
        Room room = this.roomRepository.findById(roomId).orElseThrow(()-> new NoRoomException(roomId));
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(()-> new NoHotelException(hotelId));
        Guest guest = this.guestRepository.findByEmail(guestEmail).get();
        Double totalPriceCalculated = calculateTotalPrice(checked_in,checked_out, room.getPricePerNight());
        Reservation new_reservation = new Reservation(UniqueReservationNumber(),checked_in,
                checked_out,reservationStatus,totalPriceCalculated,
                hotel,room,guest
        );
        try{
            return this.reservationRepository.save(new_reservation);
        }catch (Exception exception){
                throw new ReservationSaveException("Failed to Save reservation, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long id, String reservationNumber, LocalDate checked_in, LocalDate checked_out, ReservationStatus reservationStatus, Double totalPrice, Long hotelId, Long roomId, String guestEmail) {
        Optional<Reservation> optionalReservation = this.reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            throw new NoReservationFoundException(id);
        }
        Reservation updated_Reservation = optionalReservation.get();
        Room room = this.roomRepository.findById(roomId).orElseThrow(()-> new NoRoomException(roomId));
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(()-> new NoHotelException(hotelId));
        Guest guest = this.guestRepository.findByEmail(guestEmail).get();
        Double totalPriceCalculated = calculateTotalPrice(checked_in,checked_out,room.getPricePerNight());
        updated_Reservation.setGuest(guest);
        updated_Reservation.setReservationNumber(UniqueReservationNumber());
        updated_Reservation.setReservationStatus(reservationStatus);
        updated_Reservation.setHotel(hotel);
        updated_Reservation.setRoom(room);
        updated_Reservation.setChecked_in(checked_in);
        updated_Reservation.setChecked_out(checked_out);
        updated_Reservation.setTotalPrice(totalPriceCalculated);
        try{
            return this.reservationRepository.save(updated_Reservation);
        }catch (Exception exception){
            throw new ReservationSaveException("Failed to Update Reservation, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Reservation saveReservationApi(ReservationDTO reservationDTO) {
        if (reservationDTO == null) {
            throw new IllegalArgumentException("ReservationDTO cannot be null");
        }

        Hotel hotel = this.hotelRepository.findById(reservationDTO.getHotelId()).orElseThrow(() -> new NoHotelException(reservationDTO.getHotelId()));

        Room room = this.roomRepository.findById(reservationDTO.getRoomId()).orElseThrow(() -> new NoRoomException(reservationDTO.getRoomId()));
        Guest guest = this.guestRepository.findById(reservationDTO.getGuestId()).orElseThrow(() -> new NoGuestException(reservationDTO.getGuestId()));
        Reservation reservation = new Reservation(reservationDTO.getReservationStatus(),
                UniqueReservationNumber(),
                reservationDTO.getChecked_in(),
                reservationDTO.getChecked_out(),
                calculateTotalPrice(reservationDTO.getChecked_in(),reservationDTO.getChecked_out(),room.getPricePerNight()),
                hotel,
                room,
                guest);
        try {
            reservation = this.reservationRepository.save(reservation);
            return reservation;
        } catch (Exception exception) {
            throw new ReservationSaveException("Failed to save Reservation. Something is not valid.");
        }
    }

    @Override
    public Optional<Reservation> findByGuestEmail(String email) {
        return this.reservationRepository.findByGuestEmail(email);
    }

    @Override
    public Page<Reservation> findByReservationStatus(ReservationStatus reservationStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.reservationRepository.findByReservationStatus(reservationStatus,pageable);
    }

    @Override
    public Page<Reservation> findByReservationSpecification(String reservationStatus, int page, int size) {
        Specification<Reservation> specification = ReservationSpecification.buildSpecification(reservationStatus);
        Pageable pageable = PageRequest.of(page, size);
        return this.reservationRepository.findAll(specification, pageable);
    }


    @Override
    @Transactional
    public Reservation updateReservationApi(Long id, ReservationDTO reservationDTO) {
        Reservation reservation = this.reservationRepository.findById(id).orElseThrow(() -> new NoReservationFoundException(id));

        if (reservationDTO == null) {
            throw new IllegalArgumentException("ReservationDTO cannot be null");
        }
        Hotel hotel = this.hotelRepository.findById(reservationDTO.getHotelId()).orElseThrow(() -> new NoHotelException(reservationDTO.getHotelId()));
        Room room = this.roomRepository.findById(reservationDTO.getRoomId()).orElseThrow(() -> new NoRoomException(reservationDTO.getRoomId()));
        Guest guest = this.guestRepository.findById(reservationDTO.getGuestId()).orElseThrow(() -> new NoGuestException(reservationDTO.getGuestId()));

        reservation.setReservationNumber(UniqueReservationNumber());
        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setRoom(room);
        reservation.setHotel(hotel);
        reservation.setTotalPrice(calculateTotalPrice(reservationDTO.getChecked_in(),reservationDTO.getChecked_out(),room.getPricePerNight()));
        reservation.setGuest(guest);
        try {
            reservation = this.reservationRepository.save(reservation);
            return reservation;
        } catch (Exception exception) {
            throw new ReservationSaveException("Failed to update Reservation. Something is not valid.");
        }
    }


    @Override
    @Transactional
    public Optional<Reservation> deleteReservation(Long id) {
        Optional<Reservation> optionalReservation = this.reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            throw new NoReservationFoundException(id);
        }
        this.reservationRepository.deleteById(id);
        return optionalReservation;
    }
}
