package com.example.service;

import com.example.pojo.Appointment;
import com.example.utils.Result;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    Result<Appointment> bookAppointment(Appointment appointment);

    // 查询用户预约
    Result<List<Appointment>> getUserAppointments(Integer userId, LocalDate startDate, LocalDate endDate, String appointmentStatus);

    Result<List<Appointment>> getConsultantAppointments(Integer consultantId);

    String cancelAppointment(Integer appointmentId, String reason);

    // 取消预约
    String cancelAppointment(Integer appointmentId, Integer requestUserId, String reason);
}