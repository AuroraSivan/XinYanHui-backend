package com.example.pojo;

import jakarta.persistence.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;

@Entity
public class ConsultationSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;

    @ManyToOne
    private Appointment appointment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Consultant consultant;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String chatLog;

    @Enumerated(EnumType.STRING)
    private ConSessionStatus consessionStatus;
    private Byte rating;
    private String feedback;


}
