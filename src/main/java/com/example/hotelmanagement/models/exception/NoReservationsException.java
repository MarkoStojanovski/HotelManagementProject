package com.example.hotelmanagement.models.exception;

public class NoReservationsException extends RuntimeException{
    public NoReservationsException(String message) {
        super(message);
    }
}
