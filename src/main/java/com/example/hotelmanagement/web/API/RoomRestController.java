package com.example.hotelmanagement.web.API;

import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.dto.RoomDTO;
import com.example.hotelmanagement.models.exception.NoRoomException;
import com.example.hotelmanagement.service.HotelService;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {


    private final RoomService roomService;
    private final HotelService hotelService;

    public RoomRestController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }


    @GetMapping
    public ResponseEntity<?> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id){
        return this.roomService.getRoomById(id).map(room -> ResponseEntity.ok().body(room))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Room> deleteRoomById(@PathVariable Long id){
        return this.roomService.deleteRoomApi(id).map(room -> ResponseEntity.ok().body(room))
                .orElseThrow(()-> new NoRoomException(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody RoomDTO roomDTO){
        return ResponseEntity.ok(this.roomService.saveRoomApi(roomDTO));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id,@RequestBody RoomDTO roomDTO){
        return ResponseEntity.ok(this.roomService.updateRoomApi(id,roomDTO));
    }



}
