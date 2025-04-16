package com.example.websocket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;

public class LeaveHandler extends TextWebSocketHandler {

    // 存储所有连接的管理员客户端
    private static final Set<WebSocketSession> adminSessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket 连接建立：" + session.getId());
        adminSessions.add(session);  // 连接默认视为管理员端
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 这里只处理从后台广播给管理员的功能（不解析消息）
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("WebSocket 连接关闭：" + session.getId());
        adminSessions.remove(session);
    }

    // 提供方法给外部使用，用于广播请假信息
    public void notifyAdmins(String leaveMessage) throws Exception {
        for (WebSocketSession session : adminSessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(leaveMessage));
            }
        }
    }
}