package com.example.conversation.socket;

import com.alibaba.fastjson.JSON;
import com.example.conversation.common.Result;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebSocketFunction {

    protected String room;
    protected String grid;
    protected static CopyOnWriteArrayList<WebSocketSession> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public static WebSocketSession GetSession(String grid){
        Optional<WebSocketSession> optional = copyOnWriteArrayList.stream().filter(e->e.getGrid() == grid).findAny();
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public static List<WebSocketSession> GetRoomSession(String grid){
        return GetRoomSessionStream(grid).collect(Collectors.toList());
    }
    public static Stream<WebSocketSession> GetRoomSessionStream(String grid){
        return copyOnWriteArrayList.stream().filter(e -> e.getParam() == grid);
    }

    public static void Broadcast(Collection<? extends Session> list, String text){
        list.forEach(e -> {
            Broadcast(e, text);
        });
    }

    public static void Broadcast(Session session, String text){
        try {
            session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void BroadcastCommand(int code, String msg){
        Broadcast(copyOnWriteArrayList, JSON.toJSONString(new Result(code, msg)));
    }

    public static void BroadcastCommand(Session session, int code, String msg){
        Broadcast(copyOnWriteArrayList, JSON.toJSONString(new Result(code, msg)));
    }

    public void BroadcastCommandToOthers(Session session, int code, String msg){
        Broadcast(GetRoomSessionStream(grid).filter(e -> e.getId() != session.getId()).collect(Collectors.toList()), JSON.toJSONString(new Result(code, msg)));
    }

    public void SendCommand(Session session, int code, String msg) throws IOException {
        session.getBasicRemote().sendText(JSON.toJSONString(new Result(code, msg)));
    }

    public void SendTextToUser(int code,String grid, Object content) throws IOException {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setFrom(this.grid);
        webSocketMessage.setModel("ROOM");
        webSocketMessage.setContent(content);
        String Message = JSON.toJSONString(new Result<>(code, webSocketMessage));
        GetSession(grid).getBasicRemote().sendText(Message);
    }

    public void SendTextToRoom(int code,String room, Object content) throws IOException {
        int err = 0;
        List<WebSocketSession> list = GetRoomSession(room);
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setFrom(this.grid);
        webSocketMessage.setContent(content);
        webSocketMessage.setModel("PEOPLE");
        String Message = JSON.toJSONString(new Result<>(code, webSocketMessage));
        for (int i = 0; i < list.size(); i++) {
            Session session = list.get(i);
            try {
                session.getBasicRemote().sendText(Message);
            } catch (IOException e) {
                e.printStackTrace();
                if((err+=1) > list.size()){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected void RoomSwitch(Session session, int code,String to, String msg){
        try {
            switch (code) {
                case 200: {
                    BroadcastCommand(session, 201, "心跳回复");
                    break;
                }
                case 300: {
                    //发送消息
                    SendTextToRoom(301,to , msg);
                }
                case 400:{
                    //语音邀请
                    SendTextToRoom(401,to , msg);
                }
                case 500: {
                    //交手协议
                    SendTextToRoom(501,to , msg);
                }
            }
        } catch (IOException e) {
            BroadcastCommand(session, 1005, "发送失败，房间可能不存在");
        }
    }
    protected void UserSwitch(Session session, int code,String to, String msg){
        try {
            switch (code) {
                case 200: {
                    BroadcastCommand(session, 201, "心跳回复");
                    break;
                }
                case 300: {
                    //发送消息
                    SendTextToRoom(301,to , msg);
                }
                case 400:{
                    //语音邀请
                    SendTextToRoom(401,to , msg);
                }
                case 500: {
                    //交手协议
                    SendTextToRoom(501,to , msg);
                }
                case 900: {
                    //私密消息不记录信息
                    SendTextToRoom(900,to , msg);
                }
            }
        } catch (IOException e) {
            BroadcastCommand(session, 1005, "发送失败，房间可能不存在");
        }
    }


}
