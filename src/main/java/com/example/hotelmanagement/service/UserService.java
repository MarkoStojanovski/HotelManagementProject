package com.example.hotelmanagement.service;



import com.example.hotelmanagement.models.User;
import com.example.hotelmanagement.models.dto.RegistrationDto;
import com.example.hotelmanagement.models.enumeration.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService  extends UserDetailsService {

//   void saveUser(RegistrationDto registrationDto);

   List<User> findAll();
   User findById(String username);
   Optional<User> deleteById(String username);

   Optional<User> createUser(String username,String name, String surname, String password, Role role);
   Optional<User> updateUser(String username,String name, String surname, String password, Role role);

   User register(String username,String password,String repeatPassword,String name,String surname,Role role);


}
