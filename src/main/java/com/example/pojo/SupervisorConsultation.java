package com.example.pojo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public ConsultationSession getSession() {
        return session;
    }

    public void setSession(ConsultationSession session) {
        this.session = session;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
    }

    public String getChatLog() {
        return chatLog;
    }

    public void setChatLog(String chatLog) {
        this.chatLog = chatLog;
    }

    @Override
    public String toString() {
        return "SupervisorConsultation{" +
                "recordId=" + recordId +
                ", consultant=" + consultant +
                ", supervisor=" + supervisor +
                ", session=" + session +
                ", requestTime=" + requestTime +
                ", responseTime=" + responseTime +
                ", chatLog='" + chatLog + '\'' +
                '}';
    }
}
