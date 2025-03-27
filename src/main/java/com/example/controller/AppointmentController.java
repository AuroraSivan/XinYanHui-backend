package com.example.controller;

import com.example.pojo.Appointment;
import com.example.pojo.AppointmentStatus;
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
    @GetMapping("/user/appointments")
    public Result getUserAppointments(@RequestParam Integer userId,
                                      @RequestParam(required = false) LocalDate startDate,
                                      @RequestParam(required = false) LocalDate endDate,
                                      @RequestParam(required = false) String appointmentStatus) {

        AppointmentStatus statusEnum = null;
        try {
            // 通过自定义的 fromString 方法进行转换，忽略大小写
            if(appointmentStatus != null) {
                statusEnum = AppointmentStatus.fromString(appointmentStatus);
            }
        } catch (IllegalArgumentException e) {
            return Result.error("3", "无效的预约状态: " + appointmentStatus);
        }

        return appointmentServiceImpl.getUserAppointments(userId, startDate, endDate, statusEnum);
    }

    @GetMapping("/internal/appointments")
    public Result getConsultantAppointments(@RequestParam Integer consultantId,
                                            @RequestParam(required = false) LocalDate startDate,
                                            @RequestParam(required = false) LocalDate endDate,
                                            @RequestParam(required = false) String appointmentStatus)  {

        AppointmentStatus statusEnum = null;
        try {
            // 通过自定义的 fromString 方法进行转换，忽略大小写
            if(appointmentStatus != null) {
                statusEnum = AppointmentStatus.fromString(appointmentStatus);
            }
        } catch (IllegalArgumentException e) {
            return Result.error("3", "无效的预约状态: " + appointmentStatus);
        }

        return appointmentServiceImpl.getConsultantAppointments(consultantId, startDate, endDate, statusEnum);
    }

    /*// 取消预约
    @PostMapping("/cancel/{appointmentId}")
    public String cancelAppointment(@PathVariable Integer appointmentId, @RequestParam String reason) {
        return appointmentServiceImpl.cancelAppointment(appointmentId, reason);
    }*/
}