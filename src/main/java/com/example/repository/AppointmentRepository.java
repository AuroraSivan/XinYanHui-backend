package com.example.repository;

import com.example.pojo.Appointment;
import com.example.pojo.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByUserIdAndAppointmentDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
    List<Appointment> findByConsultantId(Integer consultantId);
    List<Appointment> findByStatus(AppointmentStatus status);
}