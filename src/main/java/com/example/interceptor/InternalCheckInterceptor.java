package com.example.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.utils.JwtUtil;
import com.example.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@Slf4j
@Order(2)
public class InternalCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        String token = request.getHeader("token");

        log.info("token:{}",token);

        try{
            Map<String, Object> map = JwtUtil.parseJwt(token);
            if(map.get("type").toString().equals("user")){
                Result responseResult = Result.error("无权限");
                String json = JSON.toJSONString(responseResult);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(json);
                return false;
            }
        } catch (Exception e) {
            log.info("parse jwt fail");
            Result responseResult = Result.error("未登录");
            String json = JSON.toJSONString(responseResult);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
        }

        return true;
    }
}
