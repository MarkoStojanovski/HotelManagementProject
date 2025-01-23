package com.example.hotelmanagement.models.dto;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.enumeration.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {

    private String reservationNumber;


    @JsonFormat(pattern = "yyyy-MM-dd")
//    @NotNull(message = "Checked in date cannot be null")
    private LocalDate checked_in;

    @JsonFormat(pattern = "yyyy-MM-dd")
//    @NotNull(message = "Checked out date cannot be null")
    private LocalDate checked_out;

    @NotBlank(message = "Reservation status cannot be blank")
    private ReservationStatus reservationStatus;


    private Double totalPrice;


    private Long hotelId;


    private Long roomId;

    private Long guestId;

    public ReservationDTO(String reservationNumber, LocalDate checked_in, LocalDate checked_out, ReservationStatus reservationStatus, Double totalPrice, Long hotelId, Long roomId, Long guestId) {
        this.reservationNumber = reservationNumber;
        this.checked_in = checked_in;
        this.checked_out = checked_out;
        this.reservationStatus = reservationStatus;
        this.totalPrice = totalPrice;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.guestId = guestId;
    }


}
