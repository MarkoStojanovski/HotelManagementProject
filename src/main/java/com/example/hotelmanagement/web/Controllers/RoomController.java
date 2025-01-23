package com.example.hotelmanagement.web.Controllers;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.models.Room;
import com.example.hotelmanagement.models.enumeration.RoomType;
import com.example.hotelmanagement.service.HotelService;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.PatternSpec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Controller
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;

    private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";

    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @GetMapping("/rooms")
    public String getAllRooms(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "6") int size,
                              @RequestParam(required = false) Double pricePerNight,
                              Model model) {
        Page<Room> roomPage = this.roomService.getRoomsBySpecification(pricePerNight, page, size);
        model.addAttribute("roomPage", roomPage);
        return "room";
    }


    @GetMapping("/room/details/{id}")
    public String getRoomById(@PathVariable Long id, Model model) {
        model.addAttribute("room", this.roomService.getRoomById(id));
        return "roomDetails";
    }

    @PostMapping("/room/delete/{id}")
    public String deleteRoomById(@PathVariable Long id) {
        this.roomService.deleteRoom(id);
        return "redirect:/rooms";
    }

    @GetMapping("/room/add")
    public String addRoom(Model model) {
        model.addAttribute("hotels", this.hotelService.getAllHotels());
        List<RoomType> roomTypes = Arrays.asList(RoomType.values());
        model.addAttribute("roomTypes", roomTypes);
        return "roomForm";
    }

    @GetMapping("/room/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {
        Room room = this.roomService.getRoomById(id).get();
        List<RoomType> roomTypes = Arrays.asList(RoomType.values());
        model.addAttribute("hotels", this.hotelService.getAllHotels());
        model.addAttribute("roomTypes", roomTypes);
        model.addAttribute("room", room);
        return "roomForm";
    }

    @PostMapping("/room")
    public String saveRoom(@RequestParam int roomNumber,
                           @RequestParam int floor,
                           @RequestParam RoomType roomType,
                           @RequestParam Double pricePerNight,
                           @RequestParam(defaultValue = "false") Boolean jacuzzi,
                           @RequestParam Long hotel,
                           @RequestParam("photo") MultipartFile photoFile, Model model) {
        try {
            String fileName = StringUtils.cleanPath(photoFile.getOriginalFilename());
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
            Files.copy(photoFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            String photoPath = "/images/" + fileName;
            this.roomService.saveRoom(roomNumber, floor, roomType, pricePerNight, jacuzzi, hotel, photoPath);
            return "redirect:/rooms";
        } catch (IOException exception) {
            model.addAttribute("uploadError", "Failed to upload photo");
            return "hotelForm";
        }

    }

    @PostMapping("/room/{id}")
    public String updateRoom(@PathVariable Long id,
                             @RequestParam int roomNumber,
                             @RequestParam int floor,
                             @RequestParam RoomType roomType,
                             @RequestParam Double pricePerNight,
                             @RequestParam(defaultValue = "false") Boolean jacuzzi,
                             @RequestParam Long hotel,
                             @RequestParam(value = "photo", required = false) MultipartFile photoFile,
                             Model model) {
        try {
            String photoPath = null;
            if (photoFile != null && !photoFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(photoFile.getOriginalFilename());
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.copy(photoFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                photoPath = "/images/" + fileName;
            }
            this.roomService.updateRoom(id, roomNumber, floor, roomType, pricePerNight, jacuzzi, hotel, photoPath);
            return "redirect:/rooms";
        } catch (IOException exception) {
            model.addAttribute("uploadError", "Failed to upload photo");
            return "hotelForm";
        }
    }


}
