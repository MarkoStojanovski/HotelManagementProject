package com.example.hotelmanagement.models.exception;

public class UserSaveProblemException extends RuntimeException{

    public UserSaveProblemException() {
        super("Something is not valid.");
    }
}
