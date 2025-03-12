package com.example.service.serviceImpl;

import com.example.pojo.User;
import com.example.repository.UserDao;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
            //返回创建好的用户对象(带uid)
            User newUser = userDao.save(user);
            newUser.setPassword("");
            return newUser;
        }
    }
}
