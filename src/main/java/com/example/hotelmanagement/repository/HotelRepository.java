package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.models.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> , JpaSpecificationExecutor {
    Optional<Hotel> findByEmail(String email);
    Optional<Hotel> findByPhone(String phone);
    Optional<Hotel> findByWebsite(String website);
    Optional<Hotel> findByPhoto(String photo);

    Page<Hotel> findByRatingGreaterThanEqual(Double rating, Pageable pageable);
    Page<Hotel> findByEmailContaining(String email, Pageable pageable);
}
