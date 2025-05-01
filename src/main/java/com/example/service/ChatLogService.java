package com.example.service;

import com.example.pojo.ChatMsg;
import com.example.utils.Result;

import java.util.List;

public interface ChatLogService {
    Result<List<ChatMsg>> getSessionLog(Integer sessionId);
    Result<List<ChatMsg>> getRecordLog(Integer recordId);
}
