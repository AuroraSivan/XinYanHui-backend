package com.example.service.serviceImpl;

import com.example.pojo.Admin;
import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import com.example.repository.AdminDao;
import com.example.repository.ConsultantDao;
import com.example.repository.SupervisorDao;
import com.example.service.AdminService;
import com.example.utils.IdGenerator;
import com.example.utils.JwtUtil;
import com.example.utils.PasswordHashWithSalt;
import com.example.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;
    private final ConsultantDao consultantDao;
    private final SupervisorDao supervisorDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao, ConsultantDao consultantDao, SupervisorDao supervisorDao) {
        this.adminDao = adminDao;
        this.consultantDao = consultantDao;
        this.supervisorDao = supervisorDao;
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
}
