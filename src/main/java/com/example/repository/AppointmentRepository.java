package com.example.repository;

import com.example.pojo.Appointment;
import com.example.pojo.AppointmentStatus;
import com.example.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface AppointmentRepository {
    @Select("SELECT * FROM Appointments WHERE user_id = #{userId} AND appointment_date BETWEEN #{startDate} AND #{endDate}")
    List<Appointment> findByUserIdAndAppointmentDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);

    @Select("SELECT * FROM Appointments WHERE consultant_id = #{consultantId} AND appointment_date BETWEEN #{startDate} AND #{endDate}")
    List<Appointment> findByConsultantIdAndAppointmentDateBetween(Integer consultantId, LocalDate startDate, LocalDate endDate);

    @Select("SELECT * FROM Appointments WHERE appointment_id = #{appointmentId} AND status = #{status}")
    Optional<Appointment> findByAppointmentIdAndStatus(Integer appointmentId, AppointmentStatus status);

    @Select("SELECT COUNT(*) FROM Appointments WHERE consultant_id = #{consultantId} AND appointment_date = #{appointmentDate} AND appointment_time = #{appointmentTime}")
    Long countByConsultantIdAndAppointmentDateAndAppointmentTime(Integer consultantId, LocalDate appointmentDate, LocalTime appointmentTime);

    @Select("SELECT COUNT(*) FROM Appointments WHERE user_id = #{userId} AND appointment_date = #{appointmentDate} AND appointment_time = #{appointmentTime}")
    Long countByUserIdAndAppointmentDateAndAppointmentTime(Integer userId, LocalDate appointmentDate, LocalTime appointmentTime);

    @Select("SELECT name FROM consultants WHERE consultant_id = #{consultantId}")
    String findConsultantNameById(Integer consultantId);

    @Update("UPDATE appointments SET status = 'canceled', cancellation_reason = #{reason},cancellation_time = NOW() WHERE appointment_id = #{appointmentId} AND status = 'booked'")
    int cancelAppointment(@Param("appointmentId") Integer appointmentId, @Param("reason") String reason);

    @Insert("INSERT INTO Appointments (user_id, consultant_id, appointment_date, appointment_time, booking_date, status, cancellation_time, cancellation_reason) " +
            "VALUES (#{userId}, #{consultantId}, #{appointmentDate}, #{appointmentTime}, #{bookingDate}, #{status}, #{cancellationTime}, #{cancellationReason})")
    void save(Appointment appointment);
}