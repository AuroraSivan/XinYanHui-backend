package com.example.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import com.example.utils.JwtUtil;

public class NotificationHandler extends TextWebSocketHandler {

    // 存储所有管理员的 WebSocket 会话
    private static final CopyOnWriteArrayList<WebSocketSession> adminSessions = new CopyOnWriteArrayList<>();
    // 存储所有咨询师的 WebSocket 会话
    private static final CopyOnWriteArrayList<WebSocketSession> consultantSessions = new CopyOnWriteArrayList<>();

    // 连接建立时加入相应的会话列表
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = (String) session.getAttributes().get("token");

        if (token == null || token.isEmpty()) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        // 从 token 参数中获取用户角色
        Map<String, Object> claims = JwtUtil.parseJwt(token);
        String role = (String) claims.get("type");  // type 字段表示角色

        // 根据角色区分管理员和咨询师
        if ("admin".equals(role)) {
            adminSessions.add(session);
        } else if ("consultant".equals(role)) {
            consultantSessions.add(session);
        } else {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        super.afterConnectionEstablished(session);
    }

    // 连接断开时移除会话
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String token = (String) session.getAttributes().get("token");
        Map<String, Object> claims = JwtUtil.parseJwt(token);
        String role = (String) claims.get("type");

        if ("admin".equals(role)) {
            adminSessions.remove(session);
        } else if ("consultant".equals(role)) {
            consultantSessions.remove(session);
        }

        super.afterConnectionClosed(session, status);
    }

    // 发送消息给所有管理员
    public static void sendLeaveNotificationToAdmin(String message) {
        for (WebSocketSession session : adminSessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 发送消息给所有咨询师
    public static void sendLeaveNotificationToConsultant(String message) {
        for (WebSocketSession session : consultantSessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 收到消息的处理，可以根据需要进行修改
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }
}
