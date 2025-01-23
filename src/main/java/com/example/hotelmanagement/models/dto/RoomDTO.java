package com.example.hotelmanagement.models.dto;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.enumeration.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class RoomDTO {

    private int room_number;
    private int floor;

    private RoomType roomType;

    private Double price_per_night;

    private Boolean jacuzzi;


    private Long hotelId;
    private String photo;

    public RoomDTO(int room_number, int floor, RoomType roomType, Double price_per_night, Boolean jacuzzi, Long hotelId,String photo) {
        this.room_number = room_number;
        this.floor = floor;
        this.roomType = roomType;
        this.price_per_night = price_per_night;
        this.jacuzzi = jacuzzi;
        this.hotelId = hotelId;
        this.photo = photo;
    }
}
