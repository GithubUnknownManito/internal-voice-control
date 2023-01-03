package com.example.conversation.socket;

public class WebSocketMessage<T> {
    private String from;
    private String model;
    private T content;

    private Long stamp = System.currentTimeMillis();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getStamp() {
        return stamp;
    }

    public void setStamp(Long stamp) {
        if(stamp != -1){
            this.stamp = stamp;
        }
    }
}
