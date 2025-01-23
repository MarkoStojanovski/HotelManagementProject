package com.example.hotelmanagement.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GuestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate date_of_birth;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "nationality cannot be blank")
    private String nationality;

    private List<Long> reservationIds;

    public GuestDTO(String name, String surname, LocalDate date_of_birth, String phone, String email, String nationality, List<Long> reservationIds) {
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.email = email;
        this.nationality = nationality;
        this.reservationIds = reservationIds;
    }


}
