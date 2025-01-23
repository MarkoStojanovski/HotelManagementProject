package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> , JpaSpecificationExecutor {
    Page<Room> findByPricePerNightGreaterThan(Double pricePerNight, Pageable pageable);

}
