package com.example.hotelmanagement.models.exception;

public class NoReservationFoundException extends RuntimeException {

    private final Long reservationId;

    public NoReservationFoundException(Long reservationId) {
        super("No Reservation Found with ID: " + reservationId);
        this.reservationId = reservationId;
    }
    public Long getReservationId(){
        return reservationId;
    }

}
