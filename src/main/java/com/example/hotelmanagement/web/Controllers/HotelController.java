package com.example.hotelmanagement.web.Controllers;

import com.example.hotelmanagement.models.Hotel;
import com.example.hotelmanagement.service.EmployeeService;
import com.example.hotelmanagement.service.HotelService;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Arrays;

@Controller
public class HotelController {

    private final HotelService hotelService;
    private final EmployeeService employeeService;
    private final RoomService roomService;

    private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";

    public HotelController(HotelService hotelService, EmployeeService employeeService, RoomService roomService) {
        this.hotelService = hotelService;
        this.employeeService = employeeService;
        this.roomService = roomService;
    }

    @GetMapping("/hotelManagement")
    public String getPage() {
        return "management";
    }



    @GetMapping("/hotels")
    public String getAllHotels(@RequestParam(defaultValue = "0")int page,
                               @RequestParam(defaultValue = "6") int size,
                               @RequestParam(required = false) Double rating,
                               Model model) {
            Page<Hotel> hotelPage = this.hotelService.findHotelBySpecification(rating, page, size);
            model.addAttribute("hotelPage",hotelPage);
            return "hotel";
        }



    @GetMapping("/hotel/details/{id}")
    public String getHotelDetails(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", this.hotelService.getHotelById(id).get());
        return "hotelDetails";
    }

    @GetMapping("/hotel/add")
    public String addHotel(Model model) {
        return "hotelForm";
    }

    @GetMapping("/hotel/edit/{id}")
    public String editHotel(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", this.hotelService.getHotelById(id).get());
        return "hotelForm";
    }

    @PostMapping("/hotel")
    public String saveHotel(@RequestParam String name,
                            @RequestParam String country,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate foundedIn,
                            @RequestParam String website,
                            @RequestParam Double rating,
                            @RequestParam String phone,
                            @RequestParam String email,
                            @RequestParam String description,
                            @RequestParam(defaultValue = "true") Boolean wifiAvailable,
                            @RequestParam(defaultValue = "false") Boolean petFriendly,
                            @RequestParam(defaultValue = "false") Boolean swimmingPool,
                            @RequestParam("photo") MultipartFile photoFile
            , Model model
    ) {
        try {
            if (this.hotelService.findByEmail(email).isPresent()) {
                model.addAttribute("emailError", "Email Already Exists");
                return "hotelForm";
            }
            if (this.hotelService.findByPhone(phone).isPresent()) {
                model.addAttribute("phoneError", "Phone Already Exists");
                return "hotelForm";
            }
            if (this.hotelService.findByWebsite(website).isPresent()) {
                model.addAttribute("websiteError", "Website Already Exists");
                return "hotelForm";
            }
            if (photoFile.isEmpty()) {
                model.addAttribute("uploadError", "Please select a file to Upload");
                return "hotelForm";
            }
            if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(photoFile.getContentType())) {
                model.addAttribute("uploadError", "Unsupported file type. Please upload an image file");
                return "hotelForm";
            }
            String fileName = StringUtils.cleanPath(photoFile.getOriginalFilename());
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
            Files.copy(photoFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            String photoPath = "/images/" + fileName;
            this.hotelService.saveHotel(name, country, foundedIn, website, rating, phone, email, description, wifiAvailable, petFriendly, swimmingPool, photoPath);
            return "redirect:/hotels";
        } catch (IOException exception) {
            model.addAttribute("uploadError", "Failed to upload photo");
            return "hotelForm";
        }
    }

    @PostMapping("/hotel/{id}")
    public String updateHotel(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String country,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate foundedIn,
                              @RequestParam String website,
                              @RequestParam Double rating,
                              @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam String description,
                              @RequestParam (defaultValue = "false") Boolean wifiAvailable,
                              @RequestParam (defaultValue = "false") Boolean petFriendly,
                              @RequestParam(defaultValue = "false") Boolean swimmingPool,
                              @RequestParam(value = "photo", required = false) MultipartFile photoFile,
                              Model model
    ) {
        try {
            String photoPath = null;
            if (photoFile != null && !photoFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(photoFile.getOriginalFilename());
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.copy(photoFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                photoPath = "/images/" + fileName;
            }
            this.hotelService.updateHotel(id, name, country, foundedIn, website, rating, phone, email, description, wifiAvailable, petFriendly, swimmingPool, photoPath);
            return "redirect:/hotels";
        } catch (IOException exception) {
            model.addAttribute("uploadError", "Failed to upload photo");
            return "hotelForm";
        }
    }


    @PostMapping("/hotel/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        this.hotelService.deleteHotelById(id);
        return "redirect:/hotels";
    }

}
