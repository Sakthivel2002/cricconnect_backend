package com.sakthi.cricconnect.repository;

import com.sakthi.cricconnect.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRecruiter_Id(Long recruiterId);
    List<Booking> findByPlayer_Id(Long playerId);
}
