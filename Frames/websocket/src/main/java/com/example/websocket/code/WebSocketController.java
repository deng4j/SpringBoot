package com.example.websocket.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/ws")
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;

    /***
     * 模拟给指定用户发消息
     * http://localhost:8080/ws/send/zhangsan?msg=hello
     */
    @GetMapping(value = "/send/{userid}")
    public ResponseEntity sendMessage(@PathVariable(value = "userid")String userid, String msg) throws IOException {
        webSocketServer.sendMessage(msg,userid);
        return ResponseEntity.ok("发送成功");
    }
}