package com.management.management.services;


import com.management.management.entity.Attendence;
import com.management.management.entity.User;
import com.management.management.repository.AttendenceRepository;
import com.management.management.repository.AttendenceService;
import com.management.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Service
public class AttendenceServiceImpl implements AttendenceService {
    @Autowired
    private AttendenceRepository attendenceRepository;

    @Autowired
    private UserRepository userRepository;

    public String recordAttendenc(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(()->new IllegalArgumentException("User not found with email: " + userEmail));
        LocalDate today = LocalDate.now();
        Optional<Attendence> exitingAttendence = attendenceRepository.findByUserAndDate(user, today);

        if (exitingAttendence.isPresent()) {
            Attendence attendence = exitingAttendence.get();
            if (attendence.getCheckOutTime() == null) {
                attendence.setCheckOutTime(LocalTime.now());
                attendenceRepository.save(attendence);
                return "checkout succssfuly for user" + userEmail;

            } else {
                return "User has already checked out";
            }
        } else {

            Attendence attendence = new Attendence();
            attendence.setUser(user);
            attendence.setDate(today);

            attendence.setCheckInTime(LocalTime.now());

            attendenceRepository.save(attendence);
            return "check in record successfully for user" + userEmail;
        }

    }
    public Attendence getAttendenceforEmployee(Long userID){
        return attendenceRepository.findByUserId(userID).orElseThrow(()->new RuntimeException("user Id not found"));
    }

    public List<Attendence> getAttendanceByEmployeeAndDate(Long userId, LocalDate startDate, LocalDate endDate) {
        return attendenceRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

}




