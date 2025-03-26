package com.example.controller;

import com.example.pojo.Appointment;
import com.example.service.serviceImpl.AppointmentServiceImpl;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
public class AppointmentController {
    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    // 预约咨询
    @PostMapping("/user/book")
    public Result<Appointment> bookAppointment(@RequestBody Appointment appointment) {
        return appointmentServiceImpl.bookAppointment(appointment);
    }

    // 获取用户的所有预约
    @GetMapping("/user/view")
    public Result<List<Appointment>> getUserAppointments(@PathVariable Integer userId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam String appointmentStatus) {
        return appointmentServiceImpl.getUserAppointments(userId, startDate, endDate, appointmentStatus);
    }

    @GetMapping("/internal/consultant/{consultantId}/appointment")
    public Result<List<Appointment>> getConsultantAppointments(@PathVariable Integer consultantId) {
        return appointmentServiceImpl.getConsultantAppointments(consultantId);
    }

    // 取消预约
    @PostMapping("/cancel/{appointmentId}")
    public String cancelAppointment(@PathVariable Integer appointmentId, @RequestParam String reason) {
        return appointmentServiceImpl.cancelAppointment(appointmentId, reason);
    }
}