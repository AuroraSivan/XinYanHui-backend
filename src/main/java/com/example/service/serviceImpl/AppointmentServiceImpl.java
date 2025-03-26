package com.example.service.serviceImpl;

import com.example.pojo.Appointment;
import com.example.pojo.AppointmentStatus;
import com.example.repository.AppointmentRepository;
import com.example.service.AppointmentService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // 预约咨询
    @Override
    public Result<Appointment> bookAppointment(Appointment appointment) {
        if (appointment.getUserId() == null || appointment.getConsultantId() == null
                || appointment.getAppointmentDate() == null || appointment.getAppointmentTime() == null) {
            return Result.error("2", "预约信息不完整");
        }

        appointment.setBookingDate(LocalDateTime.now()); // 记录预约创建时间
        appointment.setCancellationTime(null);
        appointment.setCancellationReason(null);
        appointment.setStatus(AppointmentStatus.booked);

        // 存入数据库
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return Result.success(savedAppointment);
    }

    // 查询用户预约
    @Override
    public Result<List<Appointment>> getUserAppointments(Integer userId, LocalDate startDate, LocalDate endDate, AppointmentStatus appointmentStatus) {
        startDate = (startDate != null) ? startDate : LocalDate.MIN;
        endDate = (endDate != null) ? endDate : LocalDate.now();

        // 查询用户的所有预约记录
        List<Appointment> appointments = appointmentRepository.findByUserIdAndAppointmentDateBetween(userId, startDate, endDate);
        if (appointments == null || appointments.isEmpty()) {
            return Result.error("2", "用户预约记录不存在");
        }

        // 如果 appointmentStatus 不为空，则过滤出对应状态的预约记录
        if (appointmentStatus != null) {
            appointments = appointments.stream()
                    .filter(appointment -> appointmentStatus.equals(appointment.getStatus()))
                    .collect(Collectors.toList());
        }

        return Result.success(appointments, "查询成功");
    }


    // 查询咨询师预约
    @Override
    public Result<List<Appointment>> getConsultantAppointments(Integer consultantId, LocalDate startDate, LocalDate endDate, AppointmentStatus appointmentStatus) {
        startDate = (startDate != null) ? startDate : LocalDate.MIN;
        endDate = (endDate != null) ? endDate : LocalDate.now();

        // 查询用户的所有预约记录
        List<Appointment> appointments = appointmentRepository.findByConsultantIdAndAppointmentDateBetween(consultantId, startDate, endDate);
        if (appointments == null || appointments.isEmpty()) {
            return Result.error("2", "咨询师预约记录不存在");
        }

        // 如果 appointmentStatus 不为空，则过滤出对应状态的预约记录
        if (appointmentStatus != null) {
            appointments = appointments.stream()
                    .filter(appointment -> appointmentStatus.equals(appointment.getStatus()))
                    .collect(Collectors.toList());
        }

        return Result.success(appointments, "查询成功");
    }

    /*// 取消预约
    @Override
    public Result cancelAppointment(Integer appointmentId, String reason) {
        return "";
    }*/
}