package com.example.conversation.socket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
@Slf4j
public class WebSocketSession {
    private String param;
    private String grid;
    private String sessionId;
    private Session session;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
        this.setSessionId(session.getId());
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void sendText(String text){
        log.info("发送客户端[{}]的消息:{}", session.getId(), text);
        try {
            this.session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }
}
