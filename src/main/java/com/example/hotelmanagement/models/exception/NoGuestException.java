package com.example.hotelmanagement.models.exception;

public class NoGuestException extends RuntimeException{

    private final Long guestId;
    public NoGuestException(Long guestId){
        super("No Guest By ID :  " +  guestId);
        this.guestId = guestId;
    }

    public Long getGuestId(){
        return guestId;
    }


}
