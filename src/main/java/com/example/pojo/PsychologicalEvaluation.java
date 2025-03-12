package com.example.pojo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PsychologicalEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer evaluationId;

    @ManyToOne
    private User user;

    private LocalDateTime evaluationDate;
    private String questionnaireResults;
    private String autoReport;
    private Integer riskLevel;
    private String suggestions;

    public PsychologicalEvaluation() {
    }

    public PsychologicalEvaluation(User user, LocalDateTime evaluationDate,String questionnaireResults, String autoReport, Integer riskLevel, String suggestions) {
        this.user = user;
        this.evaluationDate = evaluationDate;
        this.questionnaireResults = questionnaireResults;
        this.autoReport = autoReport;
        this.riskLevel = riskLevel;
        this.suggestions = suggestions;
    }

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getQuestionnaireResults() {
        return questionnaireResults;
    }

    public void setQuestionnaireResults(String questionnaireResults) {
        this.questionnaireResults = questionnaireResults;
    }

    public String getAutoReport() {
        return autoReport;
    }

    public void setAutoReport(String autoReport) {
        this.autoReport = autoReport;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public String toString() {
        return "PsychologicalEvaluation{" +
                "evaluationId=" + evaluationId +
                ", user=" + user +
                ", evaluationDate=" + evaluationDate +
                ", questionnaireResults=" + questionnaireResults +
                ", autoReport='" + autoReport + '\'' +
                ", riskLevel=" + riskLevel +
                ", suggestions='" + suggestions + '\'' +
                '}';
    }
}