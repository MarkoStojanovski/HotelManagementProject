package com.example.hotelmanagement.models.exception;

public class UsernameOrPasswordEmptyException extends RuntimeException{
    public UsernameOrPasswordEmptyException() {
        super("Username or Password empty");
    }
}
