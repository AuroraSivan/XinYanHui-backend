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
    public User loginService(Integer type, String s, String password) {
        User user = null;
        if(type == 0){//username
            user = userDao.findByUsernameAndPassword(s, password);
        }
        if(type == 1) {//phone
            user = userDao.findByPhoneAndPassword(s, password);
        }
        if(type == 2){//email
            user = userDao.findByEmailAndPassword(s, password);
        }
        // 重要信息置空
        if (user != null) {
            user.setPassword("");
        }
        return user;
    }

    @Override
    public User loginService(String uname, String password) {
        return null;
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

            // 生成随机值
            user.setSalt(UUID.randomUUID().toString());

            // 保存用户并返回创建好的用户对象(带uid)
            User newUser = userDao.save(user);
            newUser.setPassword("");
            return newUser;
        }
    }
}
