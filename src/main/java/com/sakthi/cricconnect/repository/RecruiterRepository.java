package com.sakthi.cricconnect.repository;

import com.sakthi.cricconnect.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    Optional<Recruiter> findByUser_Id(Long userId);
}
