package com.example.service;

import com.example.pojo.ConsultationSession;
import com.example.pojo.SupervisorConsultation;
import com.example.utils.Result;

import java.util.List;
import java.util.Map;

public interface SessionRecordService {
    Result<Map<String,Integer>> getSessionId(int apointmentId);
    Result<Map<String,Integer>> newSession(int consultantId, int userId);
    Result<Map<String,Integer>> newRecord(int consultantId,int sessionId);
    Result<ConsultationSession> evaluate(int sessionId, int rating, String feedback);
    Result<List<ConsultationSession>> getSessionsByConsultantId(int consultantId);
    Result<List<ConsultationSession>> getSessionsByUserId(int userId);
    Result<Map<String,Object>> getEvaluationOfConsultant(int consultantId);
    Result<List<SupervisorConsultation>> getRecordsByConsultantId(int consultantId);
    Result<List<SupervisorConsultation>> getRecordsBySupervisorId(int supervisorId);
}
