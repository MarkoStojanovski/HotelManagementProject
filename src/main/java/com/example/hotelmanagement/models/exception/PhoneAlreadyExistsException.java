package com.example.hotelmanagement.models.exception;

public class PhoneAlreadyExistsException extends RuntimeException{

    public PhoneAlreadyExistsException(String message) {
        super(message);
    }
}
