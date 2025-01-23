package com.example.hotelmanagement.models.exception;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException() {
        super("Invalid user credentials");
    }
}
