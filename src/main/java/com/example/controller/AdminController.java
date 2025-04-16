package com.example.controller;

import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import com.example.service.AdminService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/consultant")
    public Result<Consultant> addConsultant(@RequestParam(name="username") String name,
                                @RequestParam(required = false) String professionalInfo,
                                @RequestParam String password) {
        if(name==null || password==null || name.isEmpty() || password.isEmpty()){
            return Result.error("参数错误");
        }
        Consultant consultant = new Consultant();
        consultant.setName(name);
        consultant.setProfessionalInfo(professionalInfo);
        consultant.setPassword(password);
        return adminService.addConsultantService(consultant);
    }

    @PostMapping("/supervisor")
    public Result<Supervisor> addSupervisor(@RequestParam(name="username") String name,
                                            @RequestParam(required = false) String professionalInfo,
                                            @RequestParam String password) {
        if(name==null || password==null || name.isEmpty() || password.isEmpty()){
            return Result.error("参数错误");
        }
        Supervisor supervisor = new Supervisor();
        supervisor.setName(name);
        supervisor.setProfessionalInfo(professionalInfo);
        supervisor.setPassword(password);
        return adminService.addSupervisorService(supervisor);
    }

    @PostMapping("/schedule/consultant")
    public Result<List<Map<String,Object>>> manageConsultantSchedule(@RequestBody Map<String,Object> body) {
        if(!body.containsKey("schedule")){
            return Result.error("参数错误");
        }

        List<Map<String,Object>> schedule = (List<Map<String,Object>>) body.get("schedule");
        if(schedule==null || schedule.isEmpty()){
            return Result.error("参数错误");
        }
        return adminService.manageConsultantSchedule(schedule);
    }
}
