package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.dto.HotelDTO;
import com.example.hotelmanagement.models.exception.*;
import com.example.hotelmanagement.repository.EmployeeRepository;
import com.example.hotelmanagement.repository.HotelRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.service.HotelService;

import com.example.hotelmanagement.service.specification.HotelSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository, EmployeeRepository employeeRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Hotel> getAllHotels() {
//        if (this.hotelRepository.findAll().isEmpty()) {
//            throw new NoHotelsException("No Hotels Found.");
//        }
        return this.hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> getHotelById(Long id) {
        if (this.hotelRepository.findById(id).isEmpty()) {
            throw new NoHotelException(id);
        }
        return this.hotelRepository.findById(id);
    }


    @Override
    @Transactional
    public Hotel saveHotel(String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool, String photo) {

        Hotel new_hotel = new Hotel(name, country, founded_in, website, rating, phone, email, description, wifi_available, pet_friendly, swimming_pool, photo);
        try {
            return this.hotelRepository.save(new_hotel);
        } catch (Exception exception) {
            throw new HotelSaveException("Failed to save Hotel, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Hotel updateHotel(Long id, String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool, String photo) {
        Optional<Hotel> optionalHotel = this.hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            throw new NoHotelException(id);
        }
        Hotel updated_hotel = optionalHotel.get();
        updated_hotel.setCountry(country);
        updated_hotel.setEmail(email);
        updated_hotel.setName(name);
        updated_hotel.setDescription(description);
        updated_hotel.setFounded_in(founded_in);
        updated_hotel.setPet_friendly(pet_friendly);
        updated_hotel.setPhone(phone);
        updated_hotel.setRating(rating);
        updated_hotel.setSwimming_pool(swimming_pool);
        updated_hotel.setWifi_available(wifi_available);
        updated_hotel.setWebsite(website);
        updated_hotel.setPhoto(photo);
        try {
            return this.hotelRepository.save(updated_hotel);
        } catch (Exception exception) {
            throw new HotelSaveException("Failed to Update Hotel, something is not valid.");
        }
    }


    @Override
    @Transactional
    public Hotel saveHotelApi(HotelDTO hotelDTO) {
        if (hotelDTO == null) {
            throw new IllegalArgumentException("HotelDTO cannot be null");
        }
        List<Employee> employees = this.employeeRepository.findAllById(hotelDTO.getEmployeeIds());
        if (employees.isEmpty()) {
            throw new NoEmployeesException("No Employees Found.");
        }
        List<Room> rooms = this.roomRepository.findAllById(hotelDTO.getRoomsIds());
        if (rooms.isEmpty()) {
            throw new NoRoomsException("No Rooms Found.");
        }
        Hotel hotel = new Hotel(
                hotelDTO.getName(), hotelDTO.getCountry(), hotelDTO.getFounded_in(),
                hotelDTO.getWebsite(), hotelDTO.getRating(), hotelDTO.getPhone(),
                hotelDTO.getEmail(), hotelDTO.getDescription(), hotelDTO.getWifi_available(),
                hotelDTO.getPet_friendly(), hotelDTO.getSwimming_pool(), employees, rooms, hotelDTO.getPhoto()
        );

        employees.forEach(employee -> employee.setHotel(hotel));
        rooms.forEach(room -> room.setHotel(hotel));
        try {
            return this.hotelRepository.save(hotel);
        } catch (Exception exception) {
            throw new HotelSaveException("Failed to save Hotel, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Hotel updateHotelApi(Long id, HotelDTO hotelDTO) {
        Hotel hotel = this.hotelRepository.findById(id).orElseThrow(() -> new NoHotelException(id));
        if (hotelDTO == null) {
            throw new IllegalArgumentException("HotelDTO cannot be null");
        }
        List<Employee> employees = this.employeeRepository.findAllById(hotelDTO.getEmployeeIds());
        if (employees.isEmpty()) {
            throw new NoEmployeesException("No Employees Found.");
        }
        List<Room> rooms = this.roomRepository.findAllById(hotelDTO.getRoomsIds());
        if (rooms.isEmpty()) {
            throw new NoRoomsException("No Rooms Found.");
        }
        hotel.setName(hotelDTO.getName());
        hotel.setCountry(hotelDTO.getCountry());
        hotel.setFounded_in(hotelDTO.getFounded_in());
        hotel.setWebsite(hotelDTO.getWebsite());
        hotel.setRating(hotelDTO.getRating());
        hotel.setPhone(hotelDTO.getPhone());
        hotel.setEmail(hotelDTO.getEmail());
        hotel.setSwimming_pool(hotelDTO.getSwimming_pool());
        hotel.setWifi_available(hotelDTO.getWifi_available());
        hotel.setDescription(hotelDTO.getDescription());
        hotel.setPet_friendly(hotelDTO.getPet_friendly());
        hotel.setRooms(rooms);
        hotel.setEmployees(employees);
        hotel.setPhoto(hotelDTO.getPhoto());
        employees.forEach(employee -> employee.setHotel(hotel));
        rooms.forEach(room -> room.setHotel(hotel));
        try {
            return this.hotelRepository.save(hotel);
        } catch (Exception exception) {
            throw new HotelSaveException("Failed to update Hotel, something is not valid.");
        }
    }

    @Override
    @Transactional
    public Optional<Hotel> deleteHotel(Long id) {
        Hotel hotel = this.hotelRepository.findById(id).orElseThrow(() -> new NoHotelException(id));
        try {
            this.hotelRepository.deleteById(id);
        } catch (Exception exception) {
            throw new HotelDeleteException("Failed to delete Hotel with id : " + id);
        }
        return Optional.of(hotel);
    }

    @Override
    public void deleteHotelById(Long id) {
        if (this.hotelRepository.findById(id).isEmpty()) {
            throw new NoHotelException(id);
        }
        this.hotelRepository.deleteById(id);
    }

    @Override
    public Optional<Hotel> findByEmail(String email) {
        return this.hotelRepository.findByEmail(email);
    }

    @Override
    public Optional<Hotel> findByPhone(String phone) {
        return this.hotelRepository.findByPhone(phone);
    }

    @Override
    public Optional<Hotel> findByWebsite(String website) {
        return this.hotelRepository.findByWebsite(website);
    }

    @Override
    public Optional<Hotel> findByPhoto(String photo) {
        return this.hotelRepository.findByPhoto(photo);
    }

    @Override
    public Page<Hotel> findHotelsByRatingGreater(Double rating, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.hotelRepository.findByRatingGreaterThanEqual(rating, pageable);
    }

    @Override
    public Page<Hotel> findByEmailFilter(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.hotelRepository.findByEmailContaining(email, pageable);
    }

    @Override
    public Page<Hotel> findHotelBySpecification(Double rating, int page, int size) {
        Specification<Hotel> specification = HotelSpecification.buildSpecification(rating);
        Pageable pageable = PageRequest.of(page, size);
        return this.hotelRepository.findAll(specification, pageable);
    }

    public Page<Hotel> getHotels(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.hotelRepository.findAll(pageable);
    }

}