package com.example.service;

import com.example.pojo.Consultant;
import com.example.utils.Result;

import java.time.LocalDate;
import java.util.List;

public interface ViewService {
    //view consultant list who are scheduled for the next 6 days or available now when date is null
    //view consultant list who are scheduled for the date when date is not null
    Result<List<Consultant>> viewConsultantsService(LocalDate date);

}
