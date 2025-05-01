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
    private final static Map<Integer,Session> observerSessions = new ConcurrentHashMap<>();
    private final static Map<Session,Object> sessionLocks = new ConcurrentHashMap<>();

    @Override
    public void addObservedSession(int id, Session session) {
        observerSessions.put(id, session);
        sessionLocks.put(session, new Object());
    }

    @Override
    public void removeObservedSession(int id) {
        Session session = observerSessions.remove(id);
        sessionLocks.remove(session);
    }

    @Override
    public void sendMessage(int id, String msg) {
        Session session = observerSessions.get(id);
        if (session != null) {
            Object lock = sessionLocks.get(session);
            if(lock!=null){
                synchronized (lock){
                    try {
                        session.getBasicRemote().sendText(msg);
                    } catch (Exception e) {
                        log.error("[SEND] sessionId={}, session={}, message={}", id, session.getId(), msg);
                    }
                }
            }
        }
    }

    @Override
    public boolean isObserved(int id) {
        return observerSessions.containsKey(id);
    }
}
