package com.management.management.repository;


import com.management.management.entity.Attendence;
import com.management.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface AttendenceRepository extends JpaRepository<Attendence,Long> {

    Optional<Attendence> findByUserAndDate(User user, LocalDate date);
    Optional<Attendence> findByUserId(Long userId);
    List<Attendence>findByUserIdAndDateBetween(Long userId, LocalDate startDate,LocalDate endDate);
}
