package com.example.config;

import com.example.websocket.ChatHandler;
import com.example.websocket.NotificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册 ChatHandler 处理 /chat 路径的 WebSocket 连接
        registry.addHandler(chatHandler(), "/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // 添加会话拦截器
                .setAllowedOrigins("*"); // 允许特定来源

        // 注册 LeaveHandler 处理 /ws/leave 路径的 WebSocket 连接
        registry.addHandler(notificationHandler(), "/ws/leave")
                .setAllowedOrigins("*") // 设置允许的跨域来源
                .addInterceptors(new HttpSessionHandshakeInterceptor());  // 添加会话拦截器
    }

    @Bean
    public WebSocketHandler notificationHandler() {
        return new NotificationHandler(); // 配置 NotificationHandler 处理 WebSocket 消息
    }

    // 将 ChatHandler 声明为 Bean
    @Bean
    public WebSocketHandler chatHandler() {
        return new ChatHandler();
    }

}