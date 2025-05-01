package com.example.controller;

import com.example.pojo.ChatMsg;
import com.example.service.ChatLogService;
import com.example.service.SessionRecordService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/internal/supervisor/chat/history")
    public Result<List<ChatMsg>> getChatHistory(@RequestParam Integer sessionId){
        return chatLogService.getSessionLog(sessionId);
    }
}
