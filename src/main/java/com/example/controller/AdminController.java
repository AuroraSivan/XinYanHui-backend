package com.example.controller;

import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import com.example.service.AdminService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
