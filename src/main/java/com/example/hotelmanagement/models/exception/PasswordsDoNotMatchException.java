package com.example.hotelmanagement.models.exception;

public class PasswordsDoNotMatchException extends RuntimeException{

    public PasswordsDoNotMatchException() {
        super("The Password and Repeat password fields do not match.");
    }

}
