package com.example.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.ConsultationSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ConsultationSessionDao extends BaseMapper<ConsultationSession> {

    @Select("select session_id from ConsultationSessions where appointment_id=#{appointmentId}")
    Integer findIdByAppointmentId(Integer appointmentId);

}
