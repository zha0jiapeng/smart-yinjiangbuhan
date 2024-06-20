package com.ruoyi.web.controller.socket;


import com.ruoyi.common.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{id}")
@Slf4j
@Component
public class WebSocketController {

    private static int onlineCount = 0;
    private static Map<String, WebSocketController> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String id;

    /**
     * 用户第一次建立连接的时候执行的方法
     *
     * @param id
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        this.id = id;
        this.session = session;
        if (!webSocketMap.containsKey(id)) {
            //没有建立连接的
            webSocketMap.put(id, this);
            //注意并发
            addOnlineCount();
        }
        log.info("用户" + id + "连接上webSocket,当前连接人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(this.id)) {
            webSocketMap.remove(this.id);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + id + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("用户" + this.id + "发送信息:" + message);
    }

    /**
     * 发生错误的时候调用的方法
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.id + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 向执行某个任务的客户端发送消息
     */
    public void sendMessage(String message) throws IOException {
        synchronized (session) {
            this.session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 向用户id为userId的用户推送消息
     */
    public static void sendInfo(String message, String id) throws IOException {
        log.info("发送消息到:" + id + "，报文:" + message);
        if (id != null && webSocketMap.containsKey(id)) {
            webSocketMap.get(id).sendMessage(message);
        } else {
            log.error("用户" + id + ",不在线！");
        }
    }

    @PostMapping("/send")
    public AjaxResult sendMessage(String message, String id) throws IOException {
        sendInfo(message, id);
        return AjaxResult.success();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }


}