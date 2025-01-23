package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.models.User;
import com.example.hotelmanagement.models.dto.RegistrationDto;
import com.example.hotelmanagement.models.enumeration.Role;
import com.example.hotelmanagement.models.exception.PasswordsDoNotMatchException;
import com.example.hotelmanagement.models.exception.UserSaveProblemException;
import com.example.hotelmanagement.models.exception.UsernameAlreadyExistsException;
import com.example.hotelmanagement.models.exception.UsernameOrPasswordEmptyException;
import com.example.hotelmanagement.repository.UserRepository;
import com.example.hotelmanagement.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        return user;
    }

    @Override
    public Optional<User> deleteById(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email Not Found");
        }
        this.userRepository.deleteByEmail(email);
        return Optional.of(user);
    }

    @Override
    @Transactional
    public Optional<User> createUser(String email, String name, String surname, String password, Role role) {
        User user = new User(email,
                name,
                surname,
                passwordEncoder.encode(password),
                role
        );
        try {
            return Optional.of(this.userRepository.save(user));
        } catch (Exception exception) {
            throw new UserSaveProblemException();
        }
    }

    @Override
    @Transactional
    public Optional<User> updateUser(String email, String name, String surname, String password, Role role) {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not Found");
        }
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setSurname(surname);
        user.setEmail(email);
        try {
            return Optional.of(this.userRepository.save(user));
        } catch (Exception exception) {
            throw new UserSaveProblemException();
        }
    }

    @Override
    @Transactional
    public User register(String email, String password, String repeatPassword, String name, String surname, Role role) {
        if (email.isEmpty() || email == null || password.isEmpty() || password == null) {
            throw new UsernameOrPasswordEmptyException();
        }
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByEmail(email) != null) {
            throw new UsernameAlreadyExistsException("Username Already exists");
        }

        User user = new User(email,
                name,
                surname,
                passwordEncoder.encode(password),
                role
        );
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        return user;
    }
}
