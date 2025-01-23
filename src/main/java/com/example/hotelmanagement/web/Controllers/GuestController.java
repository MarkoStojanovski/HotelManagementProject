package com.example.hotelmanagement.web.Controllers;

import com.example.hotelmanagement.models.Guest;
import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.service.GuestService;
import com.example.hotelmanagement.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GuestController {


    private final GuestService guestService;
    private final ReservationService reservationService;

    public GuestController(GuestService guestService, ReservationService reservationService) {
        this.guestService = guestService;
        this.reservationService = reservationService;
    }


    @GetMapping( "/guests")
    public String listAllGuests(@RequestParam(defaultValue = "0")int page,
                                @RequestParam(defaultValue = "6")int size,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String phone,
                                Model model) {


        Page<Guest> guestPage = this.guestService.getAllGuestsBySpecification(email, phone, page, size);
        List<Guest> guests = guestPage.getContent();
        List<Reservation> reservations = this.reservationService.getAllReservations();

        for (Guest guest : guests){
            boolean hasReservation = reservations.stream()
                    .anyMatch(reservation -> reservation.getGuest().equals(guest));
            guest.setHasReservation(hasReservation);
        }
        model.addAttribute("guestPage", guestPage);
        return "guest";
    }

    @GetMapping("/guest/details/{id}")
    public String getGuestById(@PathVariable Long id, Model model) {
        Guest guest = this.guestService.getGuestById(id).get();
        List<Reservation> reservations = this.reservationService.getAllReservations();
        boolean hasReservation = reservations.stream().anyMatch(reservation -> reservation.getGuest().equals(guest));
            guest.setHasReservation(hasReservation);
        model.addAttribute("guest", guest);
        return "guestDetails";
    }

    @GetMapping("/guest/{id}")
    public String editGuest(@PathVariable Long id, Model model) {
        Guest guest = this.guestService.getGuestById(id).orElseThrow(() -> new IllegalArgumentException("Invalid guest ID"));
        model.addAttribute("guest", guest);
        model.addAttribute("reservations", this.reservationService.getAllReservations());
        return "guestForm";
    }


//    @GetMapping("/guest/admin/add")
//    public String newGuestAdmin(Model model) {
//        model.addAttribute("reservations", this.reservationService.getAllReservations());
//        return "guestForm";
//    }

    @GetMapping("/guest/new")
    public String newGuest() {
        return "guestForm";
    }


    @PostMapping("/guest/admin")
    public String saveGuestAdmin(@RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                                 @RequestParam String phone,
                                 @RequestParam String email,
                                 @RequestParam String nationality,
                                 @RequestParam(required = false) List<Long> reservations,Model model) {
        if (this.guestService.findByEmail(email).isPresent()) {
            model.addAttribute("emailError", "Email Already Exists");
            return "guestForm";
        }
        if (this.guestService.findByPhone(phone).isPresent()) {
            model.addAttribute("phoneError", "Phone Already Exists");
            return "guestForm";
        }
        this.guestService.saveGuestAdmin(name, surname, dateOfBirth, phone, email, nationality, reservations);
        return "redirect:/guests";
    }

    @PostMapping("/guest/admin/{id}")
    public String editGuestAdmin(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                                 @RequestParam String phone,
                                 @RequestParam String email,
                                 @RequestParam String nationality,
                                 @RequestParam(required = false) List<Long> reservations) {
        this.guestService.updateGuestAdmin(id, name, surname, dateOfBirth, phone, email, nationality, reservations);
        return "redirect:/hotelManagement";
    }


    @PostMapping("/guest")
    public String saveGuest(@RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                            @RequestParam String phone,
                            @RequestParam String email,
                            @RequestParam String nationality, Model model
    ) {

        if (this.guestService.findByEmail(email).isPresent()) {
            model.addAttribute("emailError", "Email Already Exists");
            return "guestForm";
        }
        if (this.guestService.findByPhone(phone).isPresent()) {
            model.addAttribute("phoneError", "Phone Already Exists");
            return "guestForm";
        }
        this.guestService.saveGuest(name, surname, dateOfBirth, phone, email, nationality);
        return "redirect:/guests";
    }

    @PostMapping("/guest/{id}")
    public String editGuest(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                            @RequestParam String phone,
                            @RequestParam String email,
                            @RequestParam String nationality) {
        this.guestService.updateGuest(id, name, surname, dateOfBirth, phone, email, nationality);
        return "redirect:/guests";
    }


    @PostMapping("/guest/delete/{id}")
    public String deleteGuest(@PathVariable Long id) {
        this.guestService.deleteGuest(id);
        return "redirect:/guests";
    }
}