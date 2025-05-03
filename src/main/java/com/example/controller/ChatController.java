package com.example.controller;

import com.example.pojo.ChatMsg;
import com.example.pojo.ConsultationSession;
import com.example.pojo.SupervisorConsultation;
import com.example.service.ChatLogService;
import com.example.service.SessionRecordService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    private final  SessionRecordService sessionRecordService;
    private final ChatLogService chatLogService;

    @Autowired
    public ChatController(SessionRecordService sessionRecordService, ChatLogService chatLogService) {
        this.sessionRecordService = sessionRecordService;
        this.chatLogService = chatLogService;
    }

    @GetMapping("/user/session")
    public Result<Map<String,Integer>> getSessionIdByUser(@RequestParam Integer appointmentId){
        return sessionRecordService.getSessionId(appointmentId);
    }

    @GetMapping("/internal/consultant/session")
    public Result<Map<String,Integer>> getSessionIdByConsultant(@RequestParam Integer appointmentId){
        return sessionRecordService.getSessionId(appointmentId);
    }

    @PostMapping("/user/session")
    public Result<Map<String,Integer>> newSession(@RequestParam Integer consultantId,
                                                  @RequestParam Integer userId){
        return sessionRecordService.newSession(consultantId,userId);
    }

    @PostMapping("/internal/consultant/supervise")
    public Result<Map<String,Integer>> newRecord(@RequestParam Integer consultantId,
                                                 @RequestParam Integer sessionId){
        return sessionRecordService.newRecord(consultantId,sessionId);
    }

    @GetMapping("/internal/session/history")
    public Result<List<ChatMsg>> getChatHistory(@RequestParam Integer sessionId){
        return chatLogService.getSessionLog(sessionId);
    }

    @GetMapping("/user/session/history")
    public Result<List<ChatMsg>> getChatHistoryByUser(@RequestParam Integer sessionId){
        return chatLogService.getSessionLog(sessionId);
    }

    @GetMapping("/internal/supervise/history")
    public Result<List<ChatMsg>> getSuperviseHistory(@RequestParam Integer recordId){
        return chatLogService.getRecordLog(recordId);
    }

    @PutMapping("/user/session/evaluate")
    public Result<ConsultationSession> evaluate(@RequestBody Map<String,Object> params){
        Integer sessionId = (Integer)params.get("sessionId");
        Integer rate = (Integer)params.get("rate");
        String feedback = (String)params.get("feedback");
        if(sessionId == null || rate == null || feedback == null){
            return Result.error("2","请求参数错误！");
        }
        return sessionRecordService.evaluate(sessionId,  rate, feedback);
    }

    @GetMapping("/user/consultant/evaluation")
    public Result<Map<String,Object>> getEvaluationOfConsultant(@RequestParam Integer consultantId){
        return sessionRecordService.getEvaluationOfConsultant(consultantId);
    }

    @GetMapping("/user/session/list")
    public Result<List<ConsultationSession>> getSessionsByUserId(@RequestParam Integer userId){
        return sessionRecordService.getSessionsByUserId(userId);
    }

    @GetMapping("/internal/session/list")
    public Result<List<ConsultationSession>> getSessionsByConsultantId(@RequestParam Integer consultantId){
        return sessionRecordService.getSessionsByConsultantId(consultantId);
    }

    @GetMapping("/internal/evaluation")
    public Result<Map<String,Object>> getEvaluationOfConsultantByInternal(@RequestParam Integer consultantId){
        return sessionRecordService.getEvaluationOfConsultant(consultantId);
    }

    @GetMapping("/internal/supervise/list/consultant")
    public Result<List<SupervisorConsultation>> getRecordsByConsultantId(@RequestParam Integer consultantId){
        return sessionRecordService.getRecordsByConsultantId(consultantId);
    }

    @GetMapping("/internal/supervise/list/supervisor")
    public Result<List<SupervisorConsultation>> getRecordsBySupervisorId(@RequestParam Integer supervisorId){
        return sessionRecordService.getRecordsBySupervisorId(supervisorId);
    }

}
