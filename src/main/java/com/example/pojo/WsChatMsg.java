package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WsChatMsg {
    private boolean isSystem;
    private String msg;
    private LocalDateTime time;
}
