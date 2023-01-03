package com.example.conversation.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@ServerEndpoint(value = "/data/{room}/{grid}")
public class WebSocketServer extends  WebSocketFunction {
    @OnOpen
    public void OnOpen(@PathParam(value = "room") String room, @PathParam(value = "grid") String grid, Session session) throws IOException {
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        WebSocketSession webSocketSession = new WebSocketSession();
        webSocketSession.setSession(session);
        webSocketSession.setParam(this.room = room);
        webSocketSession.setGrid(this.grid = grid);
        boolean online = copyOnWriteArrayList.stream().filter(e -> e.getGrid().equals(grid)).findAny().isPresent();
        if(online){
            BroadcastCommand(session, 120, "已经登录过了哦，不能在登录哦");
            session.close();
            return;
        }
        copyOnWriteArrayList.add(webSocketSession);
        BroadcastCommandToOthers(session, 100, "有新人加入啦~");
    }

    @OnClose
    public void onClose(Session session) {
        Optional<WebSocketSession> optional = copyOnWriteArrayList.stream().filter(e -> e.getSessionId() == session.getId()).findAny();
        WebSocketSession webSession = null;
        if(optional.isPresent()){
            webSession = optional.get();
        }
        if(webSession!= null){
            copyOnWriteArrayList.remove(webSession);
        }
        BroadcastCommandToOthers(session, 150, "有新人退出啦~");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        JSONObject data = JSON.parseObject(message);
        int code = data.getIntValue("code");
        String model = data.getString("model").toUpperCase();
        String to = data.getString("to");
        String form = data.getString("form");
        String msg = data.getString("msg");
        Long stamp = data.getLong("stamp");
        if(code != 900 && code != 200){
            if(model.equals("ROOM")){
                log.info("时间：[{}]客户端[{}]将消息:[{}]，发送到了房间{}",stamp,form, msg, to);
            } else {
                log.info("时间：[{}]客户端[{}]将消息:[{}]，发送到了个人{}",stamp,form, msg, to);
            }
        }

        if(model.equals("ROOM")){
            RoomSwitch(session, code,to, msg,stamp);
        } else {
            UserSwitch(session, code,to, msg,stamp);
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
    }

}
