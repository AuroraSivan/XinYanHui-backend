package com.example.service.serviceImpl;

import com.example.pojo.User;
import com.example.repository.UserDao;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Override
    public User loginService(String uname, String password) {
        // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
        User user = userDao.findByUsernameAndPassword(uname, password);
        // 重要信息置空
        if (user != null) {
            user.setPassword("");
        }
        return user;
    }
    @Override
    public User registerService (User user){
        //当新用户的用户名已存在时
        if(userDao.findByUsername(user.getUsername())!=null){
            // 无法注册
            return null;
        }else{
            // 设置注册日期为当前时间
            user.setRegister_date(new Date());

            // 生成随机盐值
            user.setSalt(UUID.randomUUID().toString());

            // 保存用户并返回创建好的用户对象(带uid)
            User newUser = userDao.save(user);
            newUser.setPassword("");
            return newUser;
        }
    }
}
