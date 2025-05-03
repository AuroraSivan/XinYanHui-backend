package com.example.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.SupervisorConsultation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SupervisorConsultationDao extends BaseMapper<SupervisorConsultation> {

    List<Map<String,Object>> getAbnormalSupervise();
}
