package com.example.hotelmanagement.service;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.dto.RoomDTO;
import com.example.hotelmanagement.models.enumeration.RoomType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> getAllRooms();

    public Page<Room> getRooms(int page, int size);
    Optional<Room> getRoomById(Long id);

    Room saveRoom(int room_number, int floor, RoomType roomType, Double price_per_night, Boolean jacuzzi, Long hotelId,String photo);

    Room updateRoom(Long id, int room_number, int floor, RoomType roomType, Double price_per_night, Boolean jacuzzi, Long hotelId,String photo);

    Room saveRoomApi(RoomDTO roomDTO);

    Room updateRoomApi(Long id, RoomDTO roomDTO);

    Optional<Room> deleteRoomApi(Long id);

    void deleteRoom(Long id);

    Page<Room> getRoomsBySpecification(Double pricePerNight, int page, int size);



}
