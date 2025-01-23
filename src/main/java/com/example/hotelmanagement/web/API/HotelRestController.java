package com.example.hotelmanagement.web.API;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.dto.HotelDTO;
import com.example.hotelmanagement.models.exception.NoHotelException;
import com.example.hotelmanagement.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelRestController {

private final HotelService hotelService;

    public HotelRestController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<?> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id){
        return this.hotelService.getHotelById(id).map(hotel -> ResponseEntity.ok().body(hotel))
                .orElseThrow(()-> new NoHotelException(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Hotel> deleteHotelById(@PathVariable Long id){
        return this.hotelService.deleteHotel(id).map(hotel -> ResponseEntity.ok().body(hotel))
                .orElseThrow(()-> new NoHotelException(id));
    }


    @PostMapping("/add")
    public ResponseEntity<Hotel> addHotel(@RequestBody HotelDTO hotelDTO){
        return ResponseEntity.ok(this.hotelService.saveHotelApi(hotelDTO));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO){
        return ResponseEntity.ok(this.hotelService.updateHotelApi(id,hotelDTO));
    }


}
