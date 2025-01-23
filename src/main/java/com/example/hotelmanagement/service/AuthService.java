package com.example.hotelmanagement.service;

import com.example.hotelmanagement.models.User;

import java.util.List;

public interface AuthService {


    User login(String email, String password);

    List<User> findAll();


}
