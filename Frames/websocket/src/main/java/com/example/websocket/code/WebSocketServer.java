package com.example.websocket.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@ServerEndpoint(value = "/socket/{userid}")
@Component
public class WebSocketServer {

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private static Map<String, Session> sessions=new HashMap<String,Session>();

    //用户唯一标识符
    private String userid;

    /**
     * 连接建立成功调用的方法
     *
     **/
    @OnOpen
    public void onOpen(@PathParam("userid") String userid, Session session) {
        sessions.put(userid,session);
        this.userid=userid;

        webSocketSet.add(this);     //加入set中
        try {
             sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        sessions.remove(userid);       //移除会话
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message) {
        log.info("来自客户端的消息:" + message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生异常处理方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /***
     * 群发
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        for (Map.Entry<String, Session> sessionEntry : sessions.entrySet()) {
            sessionEntry.getValue().getBasicRemote().sendText(message);
        }
    }

    /***
     * 给指定用户发送消息
     * @param message
     * @param userid
     * @throws IOException
     */
    public void sendMessage(String message,String userid) throws IOException {
            sessions.get(userid).getBasicRemote().sendText(message);
    }
    
    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }
}