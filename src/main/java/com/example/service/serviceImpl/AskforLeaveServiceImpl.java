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
        int hour;
        if(time.equals("am")){
            hour = 8;
        }else {
            hour = 13;
        }

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

    @Override
    public Result approveLeave(Integer scheduleId, Boolean isApproved) {
        try {
            int rowsAffected;
            if (isApproved) {
                // 审批通过，更新状态为 'leave'
                rowsAffected = consultantSchedulesRepository.updateLeaveApproved(scheduleId);
                if (rowsAffected > 0) {
                    return Result.success("请假审批成功");
                } else {
                    return Result.error("2", "未找到对应的请假记录");
                }
            } else {
                // 审批拒绝，更新状态为 'rejected'
                rowsAffected = consultantSchedulesRepository.updateLeaveRejected(scheduleId);
                if (rowsAffected > 0) {
                    return Result.success("请假审批拒绝成功");
                } else {
                    return Result.error("2", "未找到对应的请假记录");
                }
            }
        } catch (Exception e) {
            // 捕获异常并返回错误信息
            return Result.error("2", "系统错误：" + e.getMessage());
        }
    }
}
