package com.management.management.repository;


import com.management.management.entity.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface VacationService {
    Vacation requestvacation(Vacation vacation);
    Page<Vacation> getUserVacationRequest(String email, Pageable pageable);
    Page<Vacation> getAllVacationRequests(int page, int size, LocalDate startDate, LocalDate endDate);
    Vacation changeRequestStatus(Long requestId, String status);
    void deleteVacation(Long id);
}
