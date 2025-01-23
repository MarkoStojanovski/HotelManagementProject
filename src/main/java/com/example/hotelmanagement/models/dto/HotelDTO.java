package com.example.hotelmanagement.models.dto;

import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.Room;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HotelDTO {

    private String name;

    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate founded_in;

    private String website;

    private Double rating;

    private String phone;

    private String email;

    private String description;

    private Boolean wifi_available;
    private Boolean pet_friendly;
    private Boolean swimming_pool;


    private List<Long> employeeIds;

    private List<Long> roomsIds;
    private String photo;

    public HotelDTO(String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool, List<Long> employeeIds, List<Long> roomsIds,String photo) {
        this.name = name;
        this.country = country;
        this.founded_in = founded_in;
        this.website = website;
        this.rating = rating;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.wifi_available = wifi_available;
        this.pet_friendly = pet_friendly;
        this.swimming_pool = swimming_pool;
        this.employeeIds = employeeIds;
        this.roomsIds = roomsIds;
        this.photo = photo;
    }
}
