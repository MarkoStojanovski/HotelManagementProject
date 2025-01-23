package com.example.hotelmanagement.models.exception;

public class EmailAlreadyExists extends RuntimeException{

    public EmailAlreadyExists(String message) {
        super(message);
    }
}
