package com.example.conversation.entity;

public class RoomEntity {
    public RoomEntity(String id, String name) {
        this.id = id;
        this.name = name;
        this.enable = true;
    }

    public RoomEntity(String id, String name, boolean enable) {
        this.id = id;
        this.name = name;
        this.enable = enable;
    }

    public RoomEntity() {
    }

    private String id;
    private String name;

    private boolean enable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
