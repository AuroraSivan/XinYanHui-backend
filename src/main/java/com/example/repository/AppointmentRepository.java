package com.example.repository;

import com.example.pojo.Appointment;
import com.example.pojo.AppointmentStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
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
}