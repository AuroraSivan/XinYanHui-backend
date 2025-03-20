package com.example.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SupervisorConsultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;

    @ManyToOne
    private Consultant consultant;

    @ManyToOne
    private Supervisor supervisor;

    @OneToOne
    private ConsultationSession session;

    private LocalDateTime requestTime;
    private LocalDateTime responseTime;
    private String chatLog;

    public SupervisorConsultation() {
    }

    public SupervisorConsultation(Consultant consultant, Supervisor supervisor, ConsultationSession session, LocalDateTime requestTime, LocalDateTime responseTime, String chatLog) {
        this.consultant = consultant;
        this.supervisor = supervisor;
        this.session = session;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
        this.chatLog = chatLog;
    }


}
