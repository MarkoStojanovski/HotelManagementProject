package com.example.hotelmanagement.models;

import com.example.hotelmanagement.models.enumeration.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String reservationNumber;


    private LocalDate checked_in;
    private LocalDate checked_out;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    @JsonBackReference
    private Guest guest;




    public Reservation(String reservationNumber, LocalDate checked_in, LocalDate checked_out, ReservationStatus reservationStatus, Double totalPrice, Hotel hotel, Room room, Guest guest) {
        this.reservationNumber = reservationNumber;
        this.checked_in = checked_in;
        this.checked_out = checked_out;
        this.reservationStatus = reservationStatus;
        this.totalPrice = totalPrice;
        this.hotel = hotel;
        this.room = room;
        this.guest = guest;
    }

    public Reservation() {

    }

    public Reservation(ReservationStatus reservationStatus, String reservationNumber, LocalDate checked_in, LocalDate checked_out, Double totalPrice, Hotel hotel, Room room, Guest guest) {
        this.reservationNumber = reservationNumber;
        this.checked_in = checked_in;
        this.checked_out = checked_out;
        this.reservationStatus = reservationStatus;
        this.totalPrice = totalPrice;
        this.hotel = hotel;
        this.room = room;
        this.guest = guest;
    }



}
