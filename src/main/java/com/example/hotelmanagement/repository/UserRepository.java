package com.example.hotelmanagement.repository;


import com.example.hotelmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email,String password);

    void deleteByEmail(String email);

}
