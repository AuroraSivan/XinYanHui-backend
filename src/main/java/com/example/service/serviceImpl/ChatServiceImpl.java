package com.example.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.example.pojo.ChatMsg;
import com.example.pojo.ConSessionStatus;
import com.example.pojo.ConsultationSession;
import com.example.pojo.SupervisorConsultation;
import com.example.repository.ConsultationSessionDao;
import com.example.repository.SupervisorConsultationDao;
import com.example.service.ChatService;
import com.example.utils.WsChatMsgUtil;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();//thread safe map
    private static final Map<String, Session> onlineConsultants = new ConcurrentHashMap<>();
    private static final Map<String, Session> onlineSupervisors = new ConcurrentHashMap<>();
    // RedisTemplate
    private final  StringRedisTemplate redisTemplate;
    private final ConsultationSessionDao consultationSessionDao;
    private final SupervisorConsultationDao supervisorConsultationDao;

    @Autowired
    public ChatServiceImpl(StringRedisTemplate redisTemplate,ConsultationSessionDao consultationSessionDao,
                           SupervisorConsultationDao supervisorConsultationDao) {
        this.consultationSessionDao = consultationSessionDao;
        this.redisTemplate = redisTemplate;
        this.supervisorConsultationDao = supervisorConsultationDao;
    }


    @Override
    @Transactional
    public void addSession(String sessionId, int id, String userType, int sessionType, Session session) {
        if(userType.equals("user")){
            onlineUsers.put(sessionId, session);
            if(onlineConsultants.containsKey(0+":"+sessionId)){
                //修改数据库中的session状态和开启时间
                activeSession(sessionId);
            }
        }
        else if(userType.equals("supervisor")){
            onlineSupervisors.put(sessionId, session);
            //放入redis
            redisTemplate.opsForSet().add("chat:sessionSet:supervisor:"+id, sessionId);
            if(onlineConsultants.containsKey(1+":"+sessionId)){
                //修改数据库中的session状态和开启时间
                responseSupervise(sessionId);
            }
        }
        else if(userType.equals("consultant")){
            onlineConsultants.put(sessionType+":"+sessionId, session);
            //放入redis
            redisTemplate.opsForSet().add("chat:sessionSet:consultant:"+id+":"+sessionType, sessionId);
            //修改数据库中的session状态和开启时间
            if(sessionType==0 && onlineUsers.containsKey(sessionId)){
                //修改数据库中的session状态和开启时间
                activeSession(sessionId);
            }
            else if(sessionType==1){
                //修改数据库中的session状态和开启时间
                requestSupervise(sessionId);
            }
        }
        //向对话中传递系统消息，该用户上线
        String message = WsChatMsgUtil.getWsChatMsgJson(true, "用户"+id+"已进入会话", LocalDateTime.now());
        try {
            broadcast(sessionId, sessionType, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void removeSession(String sessionId, int id, String userType, int sessionType) {

        if(userType.equals("user")){
            onlineUsers.remove(sessionId);
            //修改数据库中的session状态和结束时间
            inactiveSession(sessionId);
        }
        else if(userType.equals("supervisor")){
            onlineSupervisors.remove(sessionId);
            redisTemplate.opsForSet().remove("chat:sessionSet:supervisor:"+id, sessionId);
            //修改数据库中的session状态和结束时间
        }
        else if(userType.equals("consultant")){
            onlineConsultants.remove(sessionType+":"+sessionId);
            //放入redis
            redisTemplate.opsForSet().remove("chat:sessionSet:consultant:"+id+":"+sessionType, sessionId);
            //修改数据库中的session状态和结束时间
            if(sessionType==0){
                inactiveSession(sessionId);
            }
        }
        //向对话中传递系统消息,对话结束
        String message = WsChatMsgUtil.getWsChatMsgJson(true, "会话已结束", LocalDateTime.now());
        try {
            broadcast(sessionId, sessionType, message);
        } catch (IOException e) {
            log.info("出现IO异常");
        }
        //对聊天记录进行持久化操作
    }

    @Override
    @Transactional
    public void sendMessage(String sessionId, int senderId, String userType, int sessionType, String message) {
        Session session = null;
        if(userType.equals("user") || userType.equals("supervisor")){
            session = onlineUsers.get(sessionType+":"+sessionId);
        }
        else if(sessionType==0){
            session = onlineUsers.get(sessionId);
        }
        else{
            session = onlineSupervisors.get(sessionId);
        }

        message = JSON.parseObject(message, ChatMsg.class).getMsg();

        //缓存消息
        LocalDateTime time = LocalDateTime.now();
        ChatMsg chatMsg = new ChatMsg(message, userType.equals("consultant"), time);
        redisTemplate.opsForList().leftPush("chat:msg:list:"+sessionType+":"+sessionId, JSON.toJSONString(chatMsg));
        redisTemplate.opsForValue().set("chat:msg:last:"+sessionType+":"+sessionId, JSON.toJSONString(chatMsg));
        //发送消息
        String msg = WsChatMsgUtil.getWsChatMsgJson(false, message, time);
        try{
            session.getBasicRemote().sendText(msg);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void broadcast(String sessionId, int sessionType, String message) throws IOException {
        Session session  = onlineConsultants.get(sessionType+":"+sessionId);
        //发送消息
        if(session!=null) {
            session.getBasicRemote().sendText(message);
        }
        if(sessionType==0){
            session = onlineUsers.get(sessionId);
            //发送消息
            if(session!=null) {
                session.getBasicRemote().sendText(message);
            }
        }
        else if(sessionType==1){
            session = onlineSupervisors.get(sessionId);
            //发送消息
            if(session!=null) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    @Override
    public void broadcast(String message) throws IOException{

        for(Session session:onlineUsers.values()){
            //发送消息
            session.getBasicRemote().sendText(message);
        }
        for(Session session:onlineSupervisors.values()){
            //发送消息
            session.getBasicRemote().sendText(message);
        }
        for(Session session:onlineConsultants.values()){
            //发送消息
            session.getBasicRemote().sendText(message);
        }
    }

    private void activeSession(String sessionId){
        ConsultationSession cs = new ConsultationSession();
        cs.setSessionId(Integer.parseInt(sessionId));
        cs.setStartTime(LocalDateTime.now());
        cs.setSessionStatus(ConSessionStatus.ACTIVE);
        consultationSessionDao.updateById(cs);
    }

    private void inactiveSession(String sessionId){
        ConsultationSession cs = new ConsultationSession();
        cs.setSessionId(Integer.parseInt(sessionId));
        cs.setEndTime(LocalDateTime.now());
        cs.setSessionStatus(ConSessionStatus.COMPLETED);
        consultationSessionDao.updateById(cs);
    }

    private void requestSupervise(String sessionId){
        SupervisorConsultation sc = new SupervisorConsultation();
        sc.setRecordId(Integer.parseInt(sessionId));
        sc.setRequestTime(LocalDateTime.now());
        supervisorConsultationDao.updateById(sc);
    }

    private void responseSupervise(String sessionId){
        SupervisorConsultation sc = new SupervisorConsultation();
        sc.setRecordId(Integer.parseInt(sessionId));
        sc.setResponseTime(LocalDateTime.now());
        supervisorConsultationDao.updateById(sc);
    }
}
