package com.example.hotelmanagement.models;

import com.example.hotelmanagement.models.enumeration.RoomType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "rooms")
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int room_number;
    private int floor;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private Double pricePerNight;

    private Boolean jacuzzi;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    public Room(int room_number, int floor, RoomType roomType, Double pricePerNight, Boolean jacuzzi, Hotel hotel,String photo) {
        this.room_number = room_number;
        this.floor = floor;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.jacuzzi = jacuzzi;
        this.hotel = hotel;
        this.photo = photo;
    }



    public Room() {
    }
}
