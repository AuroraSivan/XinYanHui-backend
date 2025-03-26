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
        appointment.setStatus(AppointmentStatus.BOOKED);

        // 存入数据库
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return Result.success(savedAppointment);
    }
    
    // 查询用户预约
    @Override
    public Result<List<Appointment>> getUserAppointments(Integer userId, LocalDate startDate, LocalDate endDate, String appointmentStatus) {
        List<Appointment> appointments = appointmentRepository.findByUserIdAndAppointmentDateBetween(userId, startDate, endDate);

        if (appointments != null && !appointments.isEmpty() && appointmentStatus != null && !appointmentStatus.isEmpty()) {
            appointments = appointments.stream()
                    .filter(appointment -> appointmentStatus.equals(appointment.getStatus()))
                    .collect(Collectors.toList());
        }

        return Result.success(appointments, "查询成功");
    }

    // 查询咨询师预约
    @Override
    public Result<List<Appointment>> getConsultantAppointments(Integer consultantId) {
        return Result.success(appointmentRepository.findByConsultantId(consultantId), "查询成功");
    }

    @Override
    public String cancelAppointment(Integer appointmentId, String reason) {
        return "";
    }

    // 取消预约
    @Override
    public String cancelAppointment(Integer appointmentId, Integer requestUserId, String reason) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();

            // 校验取消者是否是预约用户或咨询师
            if (!requestUserId.equals(appointment.getUserId()) &&
                    !requestUserId.equals(appointment.getConsultantId())) {
                return "无权限取消该预约";
            }

            if (appointment.getStatus() == AppointmentStatus.BOOKED) {
                appointment.setStatus(AppointmentStatus.CANCELED);
                appointment.setCancellationTime(LocalDateTime.now());
                appointment.setCancellationReason(reason);
                appointmentRepository.save(appointment);
                return "预约已成功取消";
            }
            return "无法取消：预约状态已变更";
        }
        return "预约不存在";
    }
}
