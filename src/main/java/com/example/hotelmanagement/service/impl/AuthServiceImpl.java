package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.models.User;
import com.example.hotelmanagement.models.exception.InvalidUserCredentialsException;
import com.example.hotelmanagement.models.exception.UsernameOrPasswordEmptyException;
import com.example.hotelmanagement.repository.UserRepository;
import com.example.hotelmanagement.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String email, String password) {
        if (email == null || email.isEmpty() || password.isEmpty() || password == null) {
            throw new UsernameOrPasswordEmptyException();
        }
        User user = this.userRepository.findByEmailAndPassword(email, password);
        if(user==null){
            throw new InvalidUserCredentialsException();
        }
       return this.userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
