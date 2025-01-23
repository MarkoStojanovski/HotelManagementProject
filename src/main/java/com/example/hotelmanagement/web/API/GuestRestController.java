package com.example.hotelmanagement.web.API;

import com.example.hotelmanagement.models.Guest;
import com.example.hotelmanagement.models.dto.GuestDTO;
import com.example.hotelmanagement.models.exception.NoGuestException;
import com.example.hotelmanagement.repository.GuestRepository;
import com.example.hotelmanagement.service.GuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guest")
public class GuestRestController {

    private final GuestService guestService;

    public GuestRestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<?> listAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        return this.guestService.getGuestById(id).map(guest -> ResponseEntity.ok().body(guest))
                .orElseThrow(() -> new NoGuestException(id));
    }


    @PostMapping("/add")
    public ResponseEntity<Guest> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(this.guestService.saveGuestApi(guestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Guest> deleteGuest(@PathVariable Long id) {
        return this.guestService.deleteGuestById(id).map(guest -> ResponseEntity.ok().body(guest))
                .orElseThrow(() -> new NoGuestException(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(this.guestService.updateGuestApi(id,guestDTO));
    }


}
