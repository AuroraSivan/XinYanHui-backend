package com.example.service.serviceImpl;

import com.example.pojo.*;
import com.example.repository.AdminDao;
import com.example.repository.ConsultantDao;
import com.example.repository.ConsultantSchedulesRepository;
import com.example.repository.SupervisorDao;
import com.example.service.AdminService;
import com.example.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;
    private final ConsultantDao consultantDao;
    private final SupervisorDao supervisorDao;
    private final ConsultantSchedulesRepository consultantSchedulesRepository;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao, ConsultantDao consultantDao, SupervisorDao supervisorDao,
                            ConsultantSchedulesRepository consultantSchedulesRepository) {
        this.adminDao = adminDao;
        this.consultantDao = consultantDao;
        this.supervisorDao = supervisorDao;
        this.consultantSchedulesRepository = consultantSchedulesRepository;
    }

    public Result<Admin> loginService(Integer AdminId, String password) {
        String salt = adminDao.getSaltById(AdminId);
        if(salt==null){                 //如果查不到盐值，即账号不存在
            return Result.error("2","账号不存在");
        }
        try {
            String hashPassword = PasswordHashWithSalt.hashPassword(password, salt);  //根据盐值和输入的密码进行哈希
            Admin admin = adminDao.getByIdAndPassword(AdminId, hashPassword);        //根据账号id和哈希后的密码查询管理员
            if(admin==null){                    //查询不到即为密码错误
                return Result.error("2","密码错误");
            }
            else{
                Map<String,Object> claims = new HashMap<>();
                claims.put("type","admin");
                claims.put("id",admin.getAdminId());
                admin.setToken(JwtUtil.generateJwt(claims));
                return Result.success(admin);
            }
        } catch (Exception e) {
            log.error("Admin login service exception:", e);
            return Result.error("未知异常");
        }
    }

    public Result<Consultant> addConsultantService(Consultant consultant) {
        consultant.setConsultantId(IdGenerator.generateId());
        String password = consultant.getPassword();
        String salt = PasswordHashWithSalt.generateSalt();
        consultant.setSalt(salt);
        try{
            String hashPassword = PasswordHashWithSalt.hashPassword(password, salt);
            consultant.setPassword(hashPassword);
        }catch(Exception e){
            return Result.error("未知异常");
        }
        consultantDao.addConsultant(consultant);
        return Result.success(consultant);
    }

    public Result<Supervisor> addSupervisorService(Supervisor supervisor) {
        supervisor.setSupervisorId(IdGenerator.generateId());
        String salt = PasswordHashWithSalt.generateSalt();
        supervisor.setSalt(salt);
        try{
            String hashPassword = PasswordHashWithSalt.hashPassword(supervisor.getPassword(), salt);
            supervisor.setPassword(hashPassword);
        }catch(Exception e){
            return Result.error("未知异常");
        }
        supervisorDao.addSupervisor(supervisor);
        return Result.success(supervisor);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result<List<Map<String,Object>>> manageConsultantSchedule(List<Map<String,Object>> scheduleList) {
        LocalDate firstDay = ScheduleGenerator.getFirstDayOfNextMonth();
        LocalDate lastDay = ScheduleGenerator.getLastDayOfNextMonth(firstDay);
        for(Map<String,Object> map:scheduleList){
            ConsultantSchedule schedule = new ConsultantSchedule();
            if(map.get("consultantId")==null){
                return Result.error("参数错误");
            }
            schedule.setConsultantId((Integer)map.get("consultantId"));
            if(map.get("time").equals("AM")){
                schedule.setStartTime(8);
                schedule.setEndTime(12);
            }
            else{
                schedule.setStartTime(13);
                schedule.setEndTime(17);
            }

            String day = (String)map.get("day");
            if(day==null){
                return Result.error("参数错误");
            }
            for(LocalDate date:ScheduleGenerator.generateDate(firstDay,lastDay,day)){
                schedule.setAvailableDate(date);
                if(consultantSchedulesRepository.insertSchedule(schedule)==0){
                    return Result.error("未知异常");
                }
            }
        }
        return Result.success(scheduleList);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result<List<Map<String,Object>>> manageSupervisorSchedule(List<Map<String,Object>> scheduleList) {
        LocalDate firstDay = ScheduleGenerator.getFirstDayOfNextMonth();
        LocalDate lastDay = ScheduleGenerator.getLastDayOfNextMonth(firstDay);
        for(Map<String,Object> map:scheduleList){
            SupervisorSchedule schedule = new SupervisorSchedule();
            if(map.get("supervisorId")==null){
                return Result.error("参数错误");
            }
            schedule.setSupervisorId((Integer)map.get("supervisorId"));
            if(map.get("time").equals("AM")){
                schedule.setStartTime(8);
                schedule.setEndTime(12);
            }
            else{
                schedule.setStartTime(13);
                schedule.setEndTime(17);
            }

            String day = (String)map.get("day");
            if(day==null){
                return Result.error("参数错误");
            }
            for(LocalDate date:ScheduleGenerator.generateDate(firstDay,lastDay,day)){
                schedule.setAvailableDate(date);
                if(supervisorDao.insertSchedule(schedule)==0){
                    return Result.error("未知异常");
                }
            }
        }
        return Result.success(scheduleList);
    }

    @Override
    public Result<Consultant> deleteConsultant(Integer id) {
        if(consultantDao.dismissal(id)==0){
            return Result.error("删除失败");
        }
        return Result.success();
    }

    @Override
    public Result<Supervisor> deleteSupervisor(Integer id) {
        if(supervisorDao.dismissal(id)==0){
            return Result.error("删除失败");
        }
        return Result.success();
    }
}
