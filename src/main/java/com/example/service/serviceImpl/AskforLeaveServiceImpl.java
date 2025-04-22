package com.example.service.serviceImpl;

import com.example.repository.ConsultantSchedulesRepository;
import com.example.service.AskforLeaveService;
import com.example.utils.Result;
import com.example.websocket.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskforLeaveServiceImpl implements AskforLeaveService {

    @Autowired
    private ConsultantSchedulesRepository consultantSchedulesRepository;

    @Override
    public Result askforLeave(Integer consultantId, String date, String time, String cancellationReason) {
        int hour;
        if (time.equals("AM")) {
            hour = 8;  // AM
        }
        else if(time.equals("PM")){
            hour = 13; // PM
        }
        else{
            return Result.error("参数错误");
        }

        // 格式化请假信息，并存入 note 字段
        String leaveNote = String.format("Date: %s, Time: %d, Reason: %s", date, hour, cancellationReason);

        // 更新 ConsultantSchedules 表格中对应 consultantId 和时间段的 status 为 request，并将请假信息存入 note
        int rowsAffected = consultantSchedulesRepository.updateLeaveRequest(consultantId, date, hour, leaveNote);

        if (rowsAffected > 0) {
            // 发送通知给管理员
            NotificationHandler.sendLeaveNotificationToAdmin("新的请假申请：咨询师ID " + consultantId + " 申请请假，时间：" + date + " " + time + ", 原因：" + cancellationReason);
            return Result.success("请假申请成功");
        } else {
            return Result.error("2", "请假申请失败");
        }
    }

    @Override
    public Result approveLeave(Integer scheduleId, Boolean isApproved) {
        try {
            int rowsAffected;
            String approvalMessage;
            if (isApproved) {
                // 审批通过，更新状态为 'leave'
                rowsAffected = consultantSchedulesRepository.updateLeaveApproved(scheduleId);
                approvalMessage = "请假申请已通过";
                NotificationHandler.sendLeaveNotificationToConsultant(approvalMessage + "，请假记录ID：" + scheduleId);
                return Result.success("请假审批成功");
            } else {
                // 审批拒绝，更新状态为 'rejected'
                rowsAffected = consultantSchedulesRepository.updateLeaveRejected(scheduleId);
                approvalMessage = "请假申请已拒绝";
                NotificationHandler.sendLeaveNotificationToConsultant(approvalMessage + "，请假记录ID：" + scheduleId);
                return Result.success("请假审批已拒绝");
            }
        } catch (Exception e) {
            return Result.error("500", "审批过程中出现错误：" + e.getMessage());
        }
    }
}
