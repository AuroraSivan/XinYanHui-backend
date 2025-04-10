package com.example.service.serviceImpl;

import com.example.repository.ConsultantSchedulesRepository;
import com.example.service.AskforLeaveService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AskforLeaveServiceImpl implements AskforLeaveService {

    @Autowired
    private ConsultantSchedulesRepository consultantSchedulesRepository;

    @Override
    public Result askforLeave(Integer consultantId, String date, String time, String cancellationReason) {
        // 将时间转换为小时部分，返回的是整数
        int hour = convertTimeToHour(time);

        // 格式化请假信息，并存入 note 字段
        String leaveNote = String.format("Date: %s, Time: %d, Reason: %s", date, hour, cancellationReason);

        // 更新 ConsultantSchedules 表格中对应 consultantId 和时间段的 status 为 request，并将请假信息存入 note
        int rowsAffected = consultantSchedulesRepository.updateLeaveRequest(consultantId, date, hour, leaveNote);

        if (rowsAffected > 0) {
            return Result.success("请假申请成功");
        } else {
            return Result.error("2", "请假申请失败");
        }
    }

    // 将时间字符串（如 12:00:00）转换为小时（如 12）
    private int convertTimeToHour(String time) {
        // 解析时间并获取小时部分
        LocalTime localTime = LocalTime.parse(time);
        return localTime.getHour();  // 返回小时部分（int 类型）
    }
}
