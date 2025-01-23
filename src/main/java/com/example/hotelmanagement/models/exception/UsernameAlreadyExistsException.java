package com.example.hotelmanagement.models.exception;

public class UsernameAlreadyExistsException extends RuntimeException{

    public UsernameAlreadyExistsException(String username) {
        super(String.format("User with username: %s already exists ",username));
    }
}
