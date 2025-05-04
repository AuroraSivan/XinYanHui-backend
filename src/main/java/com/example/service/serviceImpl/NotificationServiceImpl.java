package com.example.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pojo.Notification;
import com.example.pojo.NotificationStatus;
import com.example.repository.NotificationDao;
import com.example.service.NotificationService;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDao notificationDao;

    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao)
    {
        this.notificationDao=notificationDao;
    }

    @Override
    public Result<Long> getNewNotificationCount(Integer Id, String role) {
        QueryWrapper<Notification> query = new QueryWrapper<>();
        query.eq("rec_id", Id)
                .eq("rec_role", role)
                .eq("status", NotificationStatus.New);
        return Result.success(notificationDao.selectCount(query));
    }

    @Override
    public Result<List<Notification>> getNotifications(Integer Id, String role) {
        QueryWrapper<Notification> query = new QueryWrapper<>();
        query.eq("rec_id", Id)
                .eq("rec_role", role)
                .orderByDesc("create_time")
                .eq("status", NotificationStatus.New)
                .or()
                .eq("status",  NotificationStatus.Read);
        return Result.success(notificationDao.selectList(query));
    }

    @Override
    public Result<Notification> readNotification(Integer id) {
        Notification notf = new Notification();
        notf.setNotfId(id);
        notf.setStatus(NotificationStatus.Read);
        int row = notificationDao.updateById(notf);
        if (row == 1) {
            return Result.success(notificationDao.selectById(id));
        } else {
            return Result.error( "更新失败");
        }
    }

    @Override
    public Result<Notification> deleteNotification(Integer id) {
        Notification notf = new Notification();
        notf.setNotfId(id);
        notf.setStatus(NotificationStatus.Deleted);
        int row = notificationDao.updateById(notf);
        if (row == 1) {
            return Result.success(notf);
        } else {
            return Result.error( "删除失败");
        }
    }

    @Override
    public boolean sendNotification(Integer recId, String recRole, String content) {
        Notification notf = new Notification();
        notf.setRecId(recId);
        notf.setRecRole(recRole);
        notf.setContent(content);
        notf.setStatus(NotificationStatus.New);
        notf.setCreateTime(LocalDateTime.now());
        return notificationDao.insert(notf) == 1;
    }
}
