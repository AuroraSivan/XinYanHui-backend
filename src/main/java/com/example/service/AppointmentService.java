package com.example.service;

import com.example.pojo.Appointment;
import com.example.pojo.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(Appointment appointment);

    List<Appointment> getUserAppointments(Integer userId);

    List<Appointment> getConsultantAppointments(Integer consultantId);

    String cancelAppointment(Integer appointmentId, String reason);

    // 取消预约
    String cancelAppointment(Integer appointmentId, Integer requestUserId, String reason);
}