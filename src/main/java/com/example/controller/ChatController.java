package com.example.controller;

import com.deepoove.poi.XWPFTemplate;
import com.example.pojo.ChatExportVo;
import com.example.pojo.ChatMsg;
import com.example.pojo.ConsultationSession;
import com.example.pojo.SupervisorConsultation;
import com.example.service.ChatLogService;
import com.example.service.SessionRecordService;
import com.example.utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    public Result<List<Map<String,Object>>> getSessionsByUserId(@RequestParam Integer userId,
                                                                @RequestParam(required = false) LocalDate startDate ,
                                                                @RequestParam(required = false) LocalDate endDate){
        return sessionRecordService.getSessionsWithNameByUserId(userId, startDate, endDate);
    }

    @GetMapping("/internal/session/list")
    public Result<List<Map<String,Object>>> getSessionsByConsultantId(@RequestParam Integer consultantId,
                                                                      @RequestParam(required = false) LocalDate startDate ,
                                                                      @RequestParam(required = false) LocalDate endDate){
        return sessionRecordService.getSessionsWithNameByConsultantId(consultantId,startDate,endDate);
    }

    @GetMapping("/internal/evaluation")
    public Result<Map<String,Object>> getEvaluationOfConsultantByInternal(@RequestParam Integer consultantId){
        return sessionRecordService.getEvaluationOfConsultant(consultantId);
    }

    @GetMapping("/internal/supervise/list/consultant")
    public Result<List<Map<String,Object>>> getRecordsByConsultantId(@RequestParam Integer consultantId,
                                                                     @RequestParam(required = false) LocalDate startDate,
                                                                     @RequestParam(required = false) LocalDate endDate){
        return sessionRecordService.getRecordsWithNameByConsultantId(consultantId,startDate,endDate);
    }

    @GetMapping("/internal/supervise/list/supervisor")
    public Result<List<Map<String,Object>>> getRecordsBySupervisorId(@RequestParam Integer supervisorId,
                                                                     @RequestParam(required = false) LocalDate startDate,
                                                                     @RequestParam(required = false) LocalDate endDate){
        return sessionRecordService.getRecordsWithNameBySupervisorId(supervisorId,startDate,endDate);
    }

    @GetMapping("/internal/session/export")
    public void exportChatHistory(@RequestParam Integer sessionId, HttpServletResponse response) {
        try {
            // 获取包装结果
            Result<List<ChatExportVo>> result = chatLogService.getExportDataBySessionId(sessionId);

            // 判断是否成功
            if (!"1".equals(result.getCode())) {
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("导出失败: " + result.getMsg());
                return;
            }

            List<ChatExportVo> chatList = result.getData();  // 解包获取数据
            System.out.println("chatList size = " + chatList.size());
            if (!chatList.isEmpty()) {
                System.out.println("First message: " + chatList.get(0));
            }

            Map<String, Object> data = new HashMap<>();
            data.put("chatList", chatList);

            ClassPathResource resource = new ClassPathResource("templates/chat_template.docx");
            InputStream inputStream = resource.getInputStream();

            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(data);

            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=chat_session_" + sessionId + ".docx");

            template.writeAndClose(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write("导出失败，服务器异常！");
            } catch (Exception ignored) {}
        }
    }
}
