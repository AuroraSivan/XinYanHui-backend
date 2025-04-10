package com.example.controller;

import com.example.service.AskforLeaveService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/internal/consultant")
public class LeaveRequestController {

    @Autowired
    private AskforLeaveService askforLeaveService;

    @PostMapping("/leave")
    public Result askForLeave(@RequestBody Map<String, Object> requestBody) {
        // 从请求体中提取参数
        Integer consultantId = ((Number) requestBody.get("consultantId")).intValue(); // 转换为Integer
        String date = (String) requestBody.get("date");
        String time = (String) requestBody.get("time");
        String cancellationReason = (String) requestBody.get("cancellationReason");

        // 调用服务层方法
        return askforLeaveService.askforLeave(consultantId, date, time, cancellationReason);
    }
}
