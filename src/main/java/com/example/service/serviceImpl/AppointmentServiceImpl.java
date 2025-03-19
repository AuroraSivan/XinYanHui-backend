package com.example.service.serviceImpl;

import com.example.pojo.Appointment;
import com.example.pojo.AppointmentStatus;
import com.example.repository.AppointmentRepository;
import com.example.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // 预约咨询
    @Override
    public Appointment bookAppointment(Appointment appointment) {
        if (appointment.getUserId() == null || appointment.getConsultantId() == null
                || appointment.getAppointmentDate() == null || appointment.getAppointmentTime() == null) {
            throw new IllegalArgumentException("预约信息不完整");
        }

        appointment.setBookingDate(LocalDateTime.now()); // 记录预约创建时间
        appointment.setCancellationTime(null);
        appointment.setCancellationReason(null);
        appointment.setStatus(AppointmentStatus.BOOKED);

        // 存入数据库
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return savedAppointment;
    }


    // 查询用户预约
    @Override
    public List<Appointment> getUserAppointments(Integer userId) {
        return appointmentRepository.findByUserId(userId);
    }

    // 查询咨询师预约
    @Override
    public List<Appointment> getConsultantAppointments(Integer consultantId) {
        return appointmentRepository.findByConsultantId(consultantId);
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
