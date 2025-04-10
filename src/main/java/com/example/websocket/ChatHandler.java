package com.example.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatHandler extends TextWebSocketHandler {

    // 存储所有会话的映射：key 为会话ID，value 为会话对象
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 获取 URL 中的参数作为会话ID，示例为 "/chat/{appointmentId}"
        String appointmentId = session.getUri().getPath().split("/")[2];
        sessions.put(appointmentId, session);
        System.out.println("连接建立: " + appointmentId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 获取消息内容
        String content = message.getPayload();
        String appointmentId = session.getUri().getPath().split("/")[2];

        // 获取会话ID对应的另一端会话
        WebSocketSession otherSession = sessions.get(appointmentId);
        if (otherSession != null && otherSession.isOpen()) {
            // 发送消息给另一个用户（咨询师或用户）
            otherSession.sendMessage(new TextMessage(content));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String appointmentId = session.getUri().getPath().split("/")[2];
        sessions.remove(appointmentId);
        System.out.println("连接关闭: " + appointmentId);
    }
}