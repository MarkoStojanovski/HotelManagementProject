package com.example.hotelmanagement.web.API;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Reservation;
import com.example.hotelmanagement.models.dto.ReservationDTO;
import com.example.hotelmanagement.models.exception.NoReservationFoundException;
import com.example.hotelmanagement.service.GuestService;
import com.example.hotelmanagement.service.HotelService;
import com.example.hotelmanagement.service.ReservationService;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final GuestService guestService;

    public ReservationRestController(ReservationService reservationService, HotelService hotelService, RoomService roomService, GuestService guestService) {
        this.reservationService = reservationService;
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<?> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id).map(reservation -> ResponseEntity.ok().body(reservation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Reservation> deleteReservationById(@PathVariable Long id) {
        return this.reservationService.deleteReservation(id).map(reservation -> ResponseEntity.ok().body(reservation))
                .orElseThrow(() -> new NoReservationFoundException(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(this.reservationService.saveReservationApi(reservationDTO));
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(this.reservationService.updateReservationApi(id,reservationDTO));
    }


}
