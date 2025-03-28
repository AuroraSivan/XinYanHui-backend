package com.example.service.serviceImpl;

import com.example.pojo.Supervisor;
import com.example.repository.SupervisorDao;
import com.example.service.SupervisorService;
import com.example.utils.PasswordHashWithSalt;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupervisorServiceImpl implements SupervisorService {
    @Autowired
    private SupervisorDao supervisorDao;

    @Override
    public Result<Supervisor> loginService(Integer Id, String password) {
        String salt = supervisorDao.getSaltById(Id);
        if(salt==null){
            return Result.error("2","账号不存在");
        }
        try{
            String hashPassword = PasswordHashWithSalt.hashPassword(password, salt);
            Supervisor supervisor = supervisorDao.getByIdAndPassword(Id, hashPassword);
            if(supervisor==null){
                return Result.error("2","密码错误");
            }
            else{
                return Result.success(supervisor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("未知异常");
        }
    }
}
