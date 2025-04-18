package com.example.service;

import com.example.pojo.Admin;
import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import com.example.utils.Result;

import java.util.List;
import java.util.Map;

public interface AdminService {

    Result<Admin> loginService(Integer AdminId, String password);

    Result<Consultant> addConsultantService(Consultant consultant);

    Result<Supervisor> addSupervisorService(Supervisor supervisor);

    Result<List<Map<String,Object>>> manageConsultantSchedule(List<Map<String,Object>> scheduleList);

    Result<List<Map<String,Object>>> manageSupervisorSchedule(List<Map<String,Object>> scheduleList);
}
