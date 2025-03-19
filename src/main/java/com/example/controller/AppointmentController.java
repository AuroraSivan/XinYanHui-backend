package com.example.controller;

import com.example.pojo.Appointment;
import com.example.service.serviceImpl.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AppointmentController {
    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    // 预约咨询
    @PostMapping("/user/book")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return appointmentServiceImpl.bookAppointment(appointment);
    }

    // 获取用户的所有预约
    @GetMapping("/user/{userId}/appointment")
    public List<Appointment> getUserAppointments(@PathVariable Integer userId) {
        return appointmentServiceImpl.getUserAppointments(userId);
    }

    @GetMapping("/internal/consultant/{consultantId}/appointment")
    public List<Appointment> getConsultantAppointments(@PathVariable Integer consultantId) {
        return appointmentServiceImpl.getConsultantAppointments(consultantId);
    }

    // 取消预约
    @PostMapping("/cancel/{appointmentId}")
    public String cancelAppointment(@PathVariable Integer appointmentId, @RequestParam String reason) {
        return appointmentServiceImpl.cancelAppointment(appointmentId, reason);
    }
}