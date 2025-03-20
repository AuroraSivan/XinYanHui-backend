package com.example.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
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

}
