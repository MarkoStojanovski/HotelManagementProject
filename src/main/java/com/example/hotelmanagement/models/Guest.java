package com.example.hotelmanagement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "guests")
public class Guest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private LocalDate date_of_birth;

    @Column(nullable = false,unique = true)
    private String phone;

    @Column(nullable = false,unique = true)
    private String email;

    private String nationality;

    @Transient
    private boolean hasReservation;


    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;



    public Guest() {
    }



    public Guest(Long id, String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality, List<Reservation> reservations) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.email = email;
        this.nationality = nationality;
        this.reservations = reservations;
    }


    public Guest(String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality, List<Reservation> reservations) {
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.email = email;
        this.nationality = nationality;
        this.reservations = reservations;
    }

    public Guest(String name, String surname, String nationality, String phone, LocalDate dateOfBirth, String email, List<Reservation> reservations) {
    this.name = name;
    this.surname = surname;
    this.nationality = nationality;
    this.phone = phone;
    this.date_of_birth = dateOfBirth;
    this.email = email;
    this.reservations = reservations;
    }

    public Guest(String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality) {
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.email = email;
        this.nationality = nationality;
    }
}
