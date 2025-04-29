package com.example.repository;

import com.example.pojo.ConsultantSchedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface ConsultantSchedulesRepository {
    @Update("UPDATE ConsultantSchedules SET status = 'request', note = #{leaveNote} WHERE consultant_id = #{consultantId} AND available_date = #{date} AND start_time = #{hour}")
    int updateLeaveRequest(Integer consultantId, String date, Integer hour, String leaveNote);

    @Update("UPDATE ConsultantSchedules SET status = 'leave' WHERE schedule_id = #{scheduleId}")
    int updateLeaveApproved(Integer scheduleId);

    @Update("UPDATE ConsultantSchedules SET status = 'normal' WHERE schedule_id = #{scheduleId}")
    int updateLeaveRejected(Integer scheduleId);

    @Insert("INSERT INTO ConsultantSchedules (consultant_id, available_date, start_time, end_time) VALUES (#{consultantId}, #{availableDate}, #{startTime}, #{endTime})")
    int insertSchedule(ConsultantSchedule schedule);

    @Select("SELECT * FROM ConsultantSchedules WHERE status = #{status}")
    List<ConsultantSchedule> getScheduleByStatus(String status);

    @Select("SELECT * FROM ConsultantSchedules WHERE consultant_id = #{consultantId}")
    List<ConsultantSchedule> getScheduleById(Integer consultantId);
}
