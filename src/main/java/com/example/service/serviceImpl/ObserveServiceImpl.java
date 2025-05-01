package com.example.service.serviceImpl;

import com.example.service.ObserveService;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ObserveServiceImpl implements ObserveService {
    private final static Map<Integer,Session> observedSessions = new ConcurrentHashMap<>();

    @Override
    public void addObservedSession(int id, Session session) {
        observedSessions.put(id, session);
    }

    @Override
    public void removeObservedSession(int id) {
        observedSessions.remove(id);
    }

    @Override
    public void sendMessage(int id, String msg) {
        Session session = observedSessions.get(id);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (Exception e) {
                log.error("[SEND] sessionId={}, session={}, message={}", id, session.getId(), msg);
            }
        }
    }

    @Override
    public boolean isObserved(int id) {
        return observedSessions.containsKey(id);
    }
}
