package com.example.config;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NotificationWebSocketConfig extends ServerEndpointConfig.Configurator {

    private static AutowireCapableBeanFactory factory;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        NotificationWebSocketConfig.factory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        // 正确方式：每次连接都创建新实例
        return factory.createBean(clazz);
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        // 读取请求参数
        Map<String, List<String>> parameterMap = request.getParameterMap();

        String token = parameterMap.get("token").get(0);

        sec.getUserProperties().put("token", token);
    }
}
