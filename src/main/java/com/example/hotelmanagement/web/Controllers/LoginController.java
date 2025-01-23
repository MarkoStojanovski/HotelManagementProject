package com.example.hotelmanagement.web.Controllers;

import com.example.hotelmanagement.models.User;
import com.example.hotelmanagement.models.exception.InvalidArgumentsException;
import com.example.hotelmanagement.models.exception.InvalidUserCredentialsException;
import com.example.hotelmanagement.models.exception.InvalidUsernameOrPasswordException;
import com.example.hotelmanagement.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;

        try {
            user = authService.login(request.getParameter("username"), request.getParameter("password"));
        } catch (InvalidUsernameOrPasswordException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/hotelManagement";
    }

}
