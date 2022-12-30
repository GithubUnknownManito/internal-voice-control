package com.example.conversation.socket;

import javax.websocket.Session;

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
        this.session.getAsyncRemote().sendText(text);
    }
}
