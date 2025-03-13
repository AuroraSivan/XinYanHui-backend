package com.example.controller;

import org.springframework.web.bind.annotation.*;
import com.example.utils.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<User> loginController(@RequestBody Map<String, Object> params) {
        // 解析参数
        Integer type = (Integer) params.get("type");
        String account = (String) params.get("s");
        String password = (String) params.get("password");

        // 参数校验
        if (type == null || account == null || password == null) {
            return Result.error("400", "请求参数错误！");
        }

        User user = userService.loginService(type, account, password);
        if (user != null) {
            return Result.success(user, "登录成功！");
        } else {
            return Result.error("403", "账号或密码错误！");
        }
    }

    @PostMapping("/register")
    public Result<User> registerController(@RequestBody User newUser) {
        User user = userService.registerService(newUser);
        if (user != null) {
            return Result.success(user, "注册成功！");
        } else {
            return Result.error("456", "用户名已存在！");
        }
    }
}
