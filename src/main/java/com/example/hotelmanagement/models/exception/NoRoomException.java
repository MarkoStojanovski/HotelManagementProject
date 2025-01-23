package com.example.hotelmanagement.models.exception;

public class NoRoomException extends RuntimeException{
    private final Long roomId;
    public NoRoomException(Long roomId) {
        super("No Room with ID: "+ roomId);
        this.roomId = roomId;
    }

    public Long getRoomId(){
        return roomId;
    }
}
