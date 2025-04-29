package com.example.service;

import com.example.utils.Result;

import java.util.Map;

public interface SessionRecordService {
    Result<Map<String,Integer>> getSessionId(int apointmentId);
    Result<Map<String,Integer>> newSession(int consultantId, int userId);
    Result<Map<String,Integer>> newRecord(int consultantId,int sessionId);
}
