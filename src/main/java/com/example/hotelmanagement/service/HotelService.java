package com.example.hotelmanagement.service;

import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.dto.HotelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HotelService {

    List<Hotel> getAllHotels();

    Optional<Hotel> getHotelById(Long id);

//    Hotel saveHotel(String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool, List<Long> employeeIds, List<Long> roomIds);
    Hotel saveHotel(String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool,String photo);

//    Hotel updateHotel(Long id, String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool, List<Long> employeeIds, List<Long> roomIds);
    Hotel updateHotel(Long id, String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool,String photo);

    public Page<Hotel> getHotels(int page, int size);
    Hotel saveHotelApi(HotelDTO hotelDTO);

    Hotel updateHotelApi(Long id, HotelDTO hotelDTO);

    Optional<Hotel> deleteHotel(Long id);

    void deleteHotelById(Long id);

    Optional<Hotel> findByEmail(String email);
    Optional<Hotel> findByPhone(String phone);
    Optional<Hotel> findByWebsite(String website);
    Optional<Hotel> findByPhoto(String photo);

    Page<Hotel> findHotelsByRatingGreater(Double rating, int page,int size);

    Page<Hotel> findByEmailFilter(String email, int page,int size);

    Page<Hotel> findHotelBySpecification(Double rating, int page, int size);

}
