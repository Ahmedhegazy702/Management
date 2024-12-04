package com.management.management.repository;

import com.management.management.entity.Attendence;

import java.time.LocalDate;
import java.util.List;

public interface AttendenceService {
    String recordAttendenc(String userEmail);
    Attendence getAttendenceforEmployee(Long userID);
    List<Attendence> getAttendanceByEmployeeAndDate(Long userId, LocalDate startDate, LocalDate endDate);

}
