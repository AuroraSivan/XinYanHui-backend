package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;

@Entity
@Data
@TableName("ConsultationSessions")
public class ConsultationSession {
    @Id
    @TableId(type = IdType.AUTO)
    private Integer sessionId;

    private Integer appointmentId;
    private Integer userId;
    private Integer consultantId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String chatLog;

    @Enumerated(EnumType.STRING)
    private ConSessionStatus sessionStatus;
    private Integer rating;
    private String feedback;


}
