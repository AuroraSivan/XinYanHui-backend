package com.example.service;

import com.example.pojo.Admin;
import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import com.example.utils.Result;

public interface AdminService {

    Result<Admin> loginService(Integer AdminId, String password);

    Result<Consultant> addConsultantService(Consultant consultant);

    Result<Supervisor> addSupervisorService(Supervisor supervisor);
}
