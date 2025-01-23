package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.models.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest,Long> , JpaSpecificationExecutor {
Optional<Guest> findByEmail(String  email);
Optional<Guest> findByPhone(String phone);

Page<Guest> findByEmailContaining(String email, Pageable pageable);
Page<Guest> findByPhoneContaining(String phone, Pageable pageable);
}
