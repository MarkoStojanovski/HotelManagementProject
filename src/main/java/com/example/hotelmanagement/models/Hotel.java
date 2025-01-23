package com.example.hotelmanagement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private LocalDate founded_in;

    @Column(nullable = false, unique = true)
    private String website;

    private Double rating;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String photo;

    private String description;

    private Boolean wifi_available;
    private Boolean pet_friendly;
    private Boolean swimming_pool;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Employee> employees;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Room> rooms;

    public Hotel(String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool, List<Employee> employees, List<Room> rooms, String photo) {
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
        this.employees = employees;
        this.rooms = rooms;
        this.photo = photo;
    }

    public Hotel(String name, String country, LocalDate founded_in, String website, Double rating, String phone, String email, String description, Boolean wifi_available, Boolean pet_friendly, Boolean swimming_pool,String photo) {
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
        this.photo = photo;
    }

    public Hotel() {
    }
}
