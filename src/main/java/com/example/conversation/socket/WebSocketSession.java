package com.example.conversation.socket;

import javax.websocket.Session;

public abstract class WebSocketSession implements Session {
    private String param;
    private String grid;

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
}
