package com.example.service.serviceImpl;

import com.example.constants.TypeConstant;
import com.example.pojo.ConSessionStatus;
import com.example.pojo.ConsultationSession;
import com.example.pojo.NotifyMsg;
import com.example.pojo.SupervisorConsultation;
import com.example.repository.ConsultantDao;
import com.example.repository.ConsultationSessionDao;
import com.example.repository.SupervisorConsultationDao;
import com.example.repository.UserDao;
import com.example.service.NotifyService;
import com.example.service.SessionRecordService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SessionRecordServiceImpl implements SessionRecordService {
    private final ConsultationSessionDao  consultationSessionDao;
    private final SupervisorConsultationDao supervisorConsultationDao;
    private final ConsultantDao consultantDao;
    private final NotifyService notifyService;
  //  private final UserDao userDao;

    @Autowired
    public SessionRecordServiceImpl(ConsultationSessionDao consultationSessionDao, SupervisorConsultationDao supervisorConsultationDao,
                                 ConsultantDao consultantDao,  NotifyService notifyService /*, UserDao userDao */) {
        this.consultationSessionDao = consultationSessionDao;
        this.supervisorConsultationDao = supervisorConsultationDao;
        this.consultantDao = consultantDao;
        this.notifyService = notifyService;
       // this.userDao = userDao;
    }

    @Override
    public Result<Map<String, Integer>> getSessionId(int apointmentId) {
        Integer id = consultationSessionDao.findIdByAppointmentId(apointmentId);
        return id == null ? Result.error("未找到会话") : Result.success(Map.of("sessionId", id));
    }

    @Override
    @Transactional
    public Result<Map<String, Integer>> newSession(int consultantId, int userId) {
        ConsultationSession cs = new ConsultationSession();
        cs.setConsultantId(consultantId);
        cs.setUserId(userId);
        cs.setStartTime(LocalDateTime.now());
        cs.setSessionStatus(ConSessionStatus.READY);
        if (consultationSessionDao.insert(cs) == 1) {
            Map<String, Integer> map = new HashMap<>();
            map.put("sessionId", cs.getSessionId());
            //通知咨询师
            NotifyMsg msg = NotifyMsg.getSessionMsg(map);
            notifyService.sendMessage(consultantId, TypeConstant.CONSULTANT_INT, msg);
            // 封装返回数据
            map.put("userId",  cs.getUserId());
            map.put("consultantId", cs.getConsultantId());
            return Result.success(map);
        }
        return Result.error("创建会话失败");
    }

    @Override
    @Transactional
    public Result<Map<String, Integer>> newRecord(int consultantId,  int sessionId) {
        SupervisorConsultation sc = new SupervisorConsultation();
        sc.setConsultantId(consultantId);
        Integer supervisorId = consultantDao.findSupervisorIdById(consultantId);
        sc.setSupervisorId(supervisorId);
        sc.setSessionId(sessionId);
        sc.setRequestTime(LocalDateTime.now());
        if (supervisorConsultationDao.insert(sc) == 1) {
            Map<String, Integer> map = new HashMap<>();
            map.put("recordId", sc.getRecordId());
            //通知督导
            NotifyMsg msg = NotifyMsg.getSessionMsg(map);
            notifyService.sendMessage(sc.getSupervisorId(), TypeConstant.SUPERVISOR_INT, msg);
            // 封装返回数据
            map.put("sessionId", sc.getSessionId());
            map.put("consultantId", sc.getConsultantId());
            map.put("supervisorId", sc.getSupervisorId());
            return Result.success(map);
        }
        return Result.error("创建督导会话失败");
    }
}
