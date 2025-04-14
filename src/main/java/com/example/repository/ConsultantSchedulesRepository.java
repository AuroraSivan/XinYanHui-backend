package com.example.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper
public interface ConsultantSchedulesRepository {
    @Update("UPDATE ConsultantSchedules SET status = 'request', note = #{leaveNote} WHERE consultant_id = #{consultantId} AND available_date = #{date} AND start_time = #{hour}")
    int updateLeaveRequest(Integer consultantId, String date, int hour, String leaveNote);

    @Update("UPDATE ConsultantSchedules SET status = 'leave' WHERE schedule_id = #{scheduleId}")
    void updateLeaveApproved(Integer scheduleId);

    @Update("UPDATE ConsultantSchedules SET status = 'reject' WHERE schedule_id = #{scheduleId}")
    void updateLeaveRejected(Integer scheduleId);
}
