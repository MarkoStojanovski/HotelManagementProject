package com.example.hotelmanagement.web.Controllers;

import com.example.hotelmanagement.models.Employee;
import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.enumeration.ReservationStatus;
import com.example.hotelmanagement.service.GuestService;
import com.example.hotelmanagement.service.HotelService;
import com.example.hotelmanagement.service.ReservationService;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {

    private final RoomService roomService;
    private final GuestService guestService;
    private final HotelService hotelService;
    private final ReservationService reservationService;

    public ReservationController(RoomService roomService, GuestService guestService, HotelService hotelService, ReservationService reservationService) {
        this.roomService = roomService;
        this.guestService = guestService;
        this.hotelService = hotelService;
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public String getAllReservations(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size,
                                     @RequestParam(required = false) String reservationStatus,
                                     Model model) {
        Page<Reservation> reservationPage = this.reservationService.findByReservationSpecification(reservationStatus, page, size);
        model.addAttribute("reservationPage", reservationPage);
        return "reservation";
    }

    @GetMapping("/reservation/details/{id}")
    public String getReservationDetails(@PathVariable Long id, Model model) {
        Reservation reservation = this.reservationService.getReservationById(id).get();
        model.addAttribute("reservation", reservation);
        return "reservationDetails";
    }

    @GetMapping("/reservation/add")
    public String addReservation(Model model) {
        List<ReservationStatus> statuses = Arrays.asList(ReservationStatus.values());
        model.addAttribute("statuses", statuses);
        model.addAttribute("hotels", this.hotelService.getAllHotels());
        model.addAttribute("rooms", this.roomService.getAllRooms());
        return "reservationForm";
    }

    @GetMapping("/reservation/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model) {
        List<ReservationStatus> statuses = Arrays.asList(ReservationStatus.values());
        model.addAttribute("reservation", this.reservationService.getReservationById(id).get());
        model.addAttribute("statuses", statuses);
        model.addAttribute("hotels", this.hotelService.getAllHotels());
        model.addAttribute("rooms", this.roomService.getAllRooms());
        return "reservationForm";
    }

    @PostMapping("/reservation")
    public String saveReservation(@RequestParam(required = false) String reservationNumber,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checked_in,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checked_out,
                                  @RequestParam(required = false) Double totalPrice,
                                  @RequestParam Long hotel,
                                  @RequestParam Long room,
                                  @RequestParam String guest, Model model
    ) {
        if (this.guestService.findByEmail(guest).isEmpty()) {
            model.addAttribute("emailError", "There is no Guest by that Email, sign in first.");
            return "reservationForm";
        }
        this.reservationService.saveReservation(reservationNumber, checked_in, checked_out, ReservationStatus.PENDING, totalPrice, hotel, room, guest);
        return "redirect:/reservations";
    }

    @PostMapping("/reservation/{id}")
    public String updateReservation(@PathVariable Long id,
                                    @RequestParam(required = false) String reservationNumber,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checked_in,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checked_out,
                                    @RequestParam ReservationStatus reservationStatus,
                                    @RequestParam(required = false) Double totalPrice,
                                    @RequestParam Long hotel,
                                    @RequestParam Long room,
                                    @RequestParam String guest
    ) {
        this.reservationService.updateReservation(id, reservationNumber, checked_in, checked_out, reservationStatus, totalPrice, hotel, room, guest);
        return "redirect:/reservations";
    }


}
