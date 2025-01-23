package com.example.hotelmanagement.models.exception;

public class NoHotelException extends RuntimeException {

    private final Long hotelId;

    public NoHotelException(Long hotelId) {
        super("No Hotel Found with ID: " + hotelId);
        this.hotelId = hotelId;
    }

    public Long getHotelId() {
        return hotelId;
    }
}
