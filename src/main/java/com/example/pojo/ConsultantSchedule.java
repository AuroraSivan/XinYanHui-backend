package com.example.pojo;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
public class ConsultantSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    @ManyToOne
    private Consultant consultant;

    private LocalDate availableDate;
    private Integer startTime;
    private Integer endTime;
    private Integer slotCapacity = 5;

    public ConsultantSchedule() {
    }

    public ConsultantSchedule(Consultant consultant, LocalDate availableDate, Integer startTime, Integer endTime) {
        this.consultant = consultant;
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

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
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
        return "ConsultantSchedule{" +
                "scheduleId=" + scheduleId +
                ", consultant=" + consultant +
                ", availableDate=" + availableDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", slotCapacity=" + slotCapacity +
                '}';
    }
}
