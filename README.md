接口文档：https://xinyanhuiteam.postman.co/workspace/xinyanhui~c77005d1-b448-408c-93e2-555e484fa8d7/collection/43016002-73a9fe89-25d7-4690-96ff-590b0330658c?action=share&creator=43016002



### WebSocket设计

#### 路径

ws://localhost:8080/chat

#### 参数

| Key       | value | description                                                  |
| --------- | ----- | ------------------------------------------------------------ |
| token     |       | Jwt令牌                                                      |
| sessionId | 2     | 会话Id，对应ConsultationSession表中的session id和SupervisorConsultations表中的record id |
| type      | 0     | 0表示咨询师与来访的对话，1表示咨询师与督导的对话             |

#### 发送消息格式

```json
{
    "msg": "hello"
}
```

#### 接收消息格式

```json
{
    "isSystem": false,     //表示是否是来自系统的消息
    "msg":"hi",
    "time":"2025-03-20T08:26:45.3917069"
}
```

