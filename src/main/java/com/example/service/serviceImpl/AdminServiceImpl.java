package com.example.service.serviceImpl;

import com.example.pojo.Admin;
import com.example.repository.AdminDao;
import com.example.service.AdminService;
import com.example.utils.PasswordHashWithSalt;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
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
                return Result.success(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
