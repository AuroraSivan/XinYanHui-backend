package com.example.pojo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class SupervisorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    @ManyToOne
    private Supervisor supervisor;

    private LocalDate availableDate;
    private Integer startTime;
    private Integer endTime;
    private Integer slotCapacity = 5;

    public SupervisorSchedule() {
    }

    public SupervisorSchedule(Supervisor supervisor, LocalDate availableDate, Integer startTime, Integer endTime) {
        this.supervisor = supervisor;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getSlotCapacity() {
        return slotCapacity;
    }

    public void setSlotCapacity(Integer slotCapacity) {
        this.slotCapacity = slotCapacity;
    }

    @Override
    public String toString() {
        return "SupervisorSchedule{" +
                "scheduleId=" + scheduleId +
                ", supervisor=" + supervisor +
                ", availableDate=" + availableDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", slotCapacity=" + slotCapacity +
                '}';
    }
}
