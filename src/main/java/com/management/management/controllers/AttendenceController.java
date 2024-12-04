package com.management.management.controllers;


import com.management.management.entity.Attendence;
import com.management.management.services.AttendenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendence")
public class AttendenceController {
    @Autowired
    private AttendenceServiceImpl attendenceService ;


    @PostMapping("/record")
    public String record(Authentication authentication){
        String loggedEmail= authentication.getName();
        return attendenceService.recordAttendenc(loggedEmail);
    }

    @GetMapping("/getattendence/{userId}")
    public ResponseEntity<Attendence> getAttendanceByEmployee(@PathVariable Long userId) {
        Attendence attendanceList = attendenceService.getAttendenceforEmployee(userId);
        return ResponseEntity.ok(attendanceList);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Attendence>> getAttendanceByEmployeeAndDate(
            @RequestParam Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Attendence> attendanceList = attendenceService.getAttendanceByEmployeeAndDate(userId, startDate, endDate);
        return ResponseEntity.ok(attendanceList);
    }
}
