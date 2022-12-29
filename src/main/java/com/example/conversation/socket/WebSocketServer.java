package com.example.conversation.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Slf4j
@Component
@ServerEndpoint(value = "/data/{room}/{grid}")
public class WebSocketServer extends  WebSocketFunction {

    @OnOpen
    public void OnOpen(WebSocketSession session, @PathParam(value = "room") String room, @PathParam(value = "grid") String grid) throws IOException {
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        session.setParam(this.room = room);
        session.setGrid(this.grid = grid);
        boolean online = copyOnWriteArrayList.stream().filter(e -> e.getGrid().equals(grid)).findAny().isPresent();
        if(online){
            BroadcastCommand(session, 105, "已经登录过了哦，不能在登录哦");
            session.close();
            return;
        }
        copyOnWriteArrayList.add(session);
        BroadcastCommandToOthers(session, 100, "有新人加入啦~");
    }

    @OnClose
    public void onClose(Session session) {
        WebSocketSession webSession = copyOnWriteArrayList.stream().filter(e -> e.getId() == session.getId()).findAny().get();
        if(webSession!= null){
            copyOnWriteArrayList.remove(webSession);
        }
        BroadcastCommandToOthers(session, 105, "有新人退出啦~");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        JSONObject data = JSON.parseObject(message);
        int code = data.getIntValue("code");
        String model = data.getString("model");
        String to = data.getString("to");
        String form = data.getString("form");
        String msg = data.getString("msg");
        if(code != 900){
            if(model.equals("room")){
                log.info("客户端[{}]将消息:[{}]，发送到了房间{}",form, msg, to);
            } else {
                log.info("客户端[{}]将消息:[{}]，发送到了个人{}",form, msg, to);
            }
        }

        if(model.equals("room")){
            RoomSwitch(session, code,to, msg);
        } else {
            UserSwitch(session, code,to, msg);
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

}
