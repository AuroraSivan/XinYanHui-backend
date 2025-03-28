package com.example.service;

import com.example.pojo.AvailableStatus;
import com.example.pojo.Consultant;
import com.example.utils.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ViewService {
    //view consultant list who are scheduled for the next 6 days or available now when date is null
    //view consultant list who are scheduled for the date when date is not null
    Result<List<Consultant>> viewConsultantsService(LocalDate date);

    //view consultant available time slots for the next 6 days or available now
    Result<Map<LocalDateTime, AvailableStatus>> getAvailableTimeService(Integer Id);
}
