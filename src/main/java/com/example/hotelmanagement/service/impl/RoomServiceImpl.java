package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.dto.RoomDTO;
import com.example.hotelmanagement.models.enumeration.RoomType;
import com.example.hotelmanagement.models.exception.NoHotelException;
import com.example.hotelmanagement.models.exception.NoRoomException;
import com.example.hotelmanagement.models.exception.NoRoomsException;
import com.example.hotelmanagement.models.exception.RoomSaveException;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.service.RoomService;
import com.example.hotelmanagement.service.specification.RoomSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return this.roomRepository.findAll();
    }

    @Override
    public Page<Room> getRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.roomRepository.findAll(pageable);
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        Optional<Room> optionalRoom = this.roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            throw new NoRoomException(id);
        }
        return optionalRoom;
    }

    @Override
    @Transactional
    public Room saveRoom(int room_number, int floor, RoomType roomType, Double price_per_night, Boolean jacuzzi, Long hotelId, String photo) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new NoHotelException(hotelId));
        Room new_room = new Room(
                room_number,
                floor,
                roomType,
                price_per_night,
                jacuzzi,
                hotel, photo
        );
        try {
            return this.roomRepository.save(new_room);
        } catch (Exception exception) {
            throw new RoomSaveException("Failed to save Room, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Room updateRoom(Long id, int room_number, int floor, RoomType roomType, Double price_per_night, Boolean jacuzzi, Long hotelId, String photo) {
        Room room = this.roomRepository.findById(id).orElseThrow(() -> new NoRoomException(id));
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new NoHotelException(hotelId));
        room.setRoom_number(room_number);
        room.setRoomType(roomType);
        room.setJacuzzi(jacuzzi);
        room.setPricePerNight(price_per_night);
        room.setFloor(floor);
        room.setHotel(hotel);
        room.setPhoto(photo);
        try {
            return this.roomRepository.save(room);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Failed to Update Room, something is not valid.");
        }
    }


    @Transactional
    @Override
    public Room saveRoomApi(RoomDTO roomDTO) {
        if (roomDTO == null) {
            throw new IllegalArgumentException("RoomDTO cannot be null");
        }
        Hotel hotel = this.hotelRepository.findById(roomDTO.getHotelId()).orElseThrow(() -> new NoHotelException(roomDTO.getHotelId()));
        Room room = new Room(
                roomDTO.getRoom_number(),
                roomDTO.getFloor(),
                roomDTO.getRoomType(),
                roomDTO.getPrice_per_night(),
                roomDTO.getJacuzzi(),
                hotel, roomDTO.getPhoto()
        );
        try {
            room = this.roomRepository.save(room);
            return room;
        } catch (Exception exception) {
            throw new RoomSaveException("Failed to save Room. Something is not valid.");
        }
    }

    @Override
    @Transactional
    public Room updateRoomApi(Long id, RoomDTO roomDTO) {
        Room room = this.roomRepository.findById(id).orElseThrow(() -> new NoRoomException(id));
        if (roomDTO == null) {
            throw new IllegalArgumentException("RoomDto cannot be null");
        }
        Hotel hotel = this.hotelRepository.findById(roomDTO.getHotelId()).orElseThrow(() -> new NoHotelException(roomDTO.getHotelId()));
        room.setRoom_number(roomDTO.getRoom_number());
        room.setRoomType(roomDTO.getRoomType());
        room.setFloor(roomDTO.getFloor());
        room.setJacuzzi(roomDTO.getJacuzzi());
        room.setPricePerNight(roomDTO.getPrice_per_night());
        room.setHotel(hotel);
        try {
            room = this.roomRepository.save(room);
            return room;
        } catch (Exception exception) {
            throw new RoomSaveException("Failed to update Room. Something is not valid.");
        }
    }

    @Override
    @Transactional
    public Optional<Room> deleteRoomApi(Long id) {
        Optional<Room> optionalRoom = this.roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            throw new NoRoomException(id);
        }
        this.roomRepository.deleteById(id);
        return optionalRoom;
    }

    @Override
    public void deleteRoom(Long id) {
        if (this.roomRepository.findById(id).isEmpty()) {
            throw new NoRoomException(id);
        }
        this.roomRepository.deleteById(id);
    }

    @Override
    public Page<Room> getRoomsBySpecification(Double pricePerNight, int page, int size) {
        Specification<Room> specification = RoomSpecification.buildSpecification(pricePerNight);
        Pageable pageable = PageRequest.of(page, size);
        return this.roomRepository.findAll(specification, pageable);
    }


}
