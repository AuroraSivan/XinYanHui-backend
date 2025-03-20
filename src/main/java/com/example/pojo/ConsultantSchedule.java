package com.example.pojo;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Data
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

}
