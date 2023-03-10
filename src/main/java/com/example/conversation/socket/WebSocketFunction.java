package com.example.conversation.socket;

import com.alibaba.fastjson.JSON;
import com.example.conversation.common.Result;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
        return copyOnWriteArrayList.stream().filter(e -> Objects.nonNull(e.getParam()) && e.getParam().equals(grid));
    }

    public static void Broadcast(Collection<WebSocketSession> list, String text){
        list.forEach(e -> {
            Broadcast(e, text);
        });
    }

    public static void Broadcast(WebSocketSession session, String text){
        session.sendText(text);
    }

    public static void BroadcastCommand(int code, String msg){
        Broadcast(copyOnWriteArrayList, JSON.toJSONString(new Result(code, msg)));
    }

    public static void BroadcastCommand(Session session, int code, String msg){
        Broadcast(copyOnWriteArrayList, JSON.toJSONString(new Result(code, msg)));
    }

    public void BroadcastCommandToOthers(Session session, int code, String msg){
        Broadcast(GetRoomSessionStream(this.room).filter(e -> e.getSessionId() != session.getId()).collect(Collectors.toList()), JSON.toJSONString(new Result(code, msg)));
    }

    public void SendCommand(Session session, int code, String msg) throws IOException {
        synchronized (session) {
            session.getBasicRemote().sendText(JSON.toJSONString(new Result(code, msg)));
        }
    }

    public void SendTextToUser(int code,String grid, Object content,Long stamp) throws IOException {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setFrom(this.grid);
        webSocketMessage.setModel("PEOPLE");
        webSocketMessage.setContent(content);
        webSocketMessage.setStamp(stamp);
        String Message = JSON.toJSONString(new Result<>(code, webSocketMessage));
        GetSession(grid).sendText(Message);
    }

    public void SendTextToRoom(int code,String room, Object content,Long stamp) throws IOException {
        List<WebSocketSession> list = GetRoomSession(room);
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setFrom(this.grid);
        webSocketMessage.setContent(content);
        webSocketMessage.setModel("ROOM");
        webSocketMessage.setStamp(stamp);
        String Message = JSON.toJSONString(new Result<>(code, webSocketMessage));
        for (int i = 0; i < list.size(); i++) {
            WebSocketSession session = list.get(i);
            session.sendText(Message);
        }
    }

    protected void RoomSwitch(Session session, int code,String to, String msg, Long stamp){
        try {
            switch (code) {
                case 200: {
                    BroadcastCommand(session, 200, "????????????");
                    break;
                }
                case 300: {
                    //????????????
                    SendTextToRoom(300,to , msg, stamp);
                }
                case 400:{
                    //????????????,????????????
                    SendTextToRoom(400,to , msg, stamp);
                }
                case 420:{
                    //???????????????????????????????????????
                    SendTextToRoom(420,to , msg, stamp);
                }
                case 490:{
                    //????????????
                    SendTextToRoom(401,to , msg, stamp);
                }
                case 500: {
                    //????????????
                    SendTextToRoom(500,to , msg, stamp);
                }
            }
        } catch (IOException e) {
            BroadcastCommand(session, 1005, "????????????????????????????????????");
        }
    }
    protected void UserSwitch(Session session, int code,String to, String msg, Long stamp){
        try {
            switch (code) {
                case 200: {
                    BroadcastCommand(session, 200, "????????????");
                    break;
                }
                case 300: {
                    //????????????
                    SendTextToUser(300,to , msg, stamp);
                }
                case 400:{
                    //????????????
                    SendTextToUser(400,to , msg, stamp);
                }
                case 411:{
                    //????????????-??????
                    SendTextToUser(401,to , msg, stamp);
                }
                case 412:{
                    //????????????-??????
                    SendTextToUser(402,to , msg, stamp);
                }
                case 490:{
                    //????????????
                    SendTextToUser(402,to , msg, stamp);
                }
                case 500: {
                    //????????????
                    SendTextToUser(500,to , msg, stamp);
                }
                case 900: {
                    //???????????????????????????
                    SendTextToUser(900,to , msg, stamp);
                }
            }
        } catch (IOException e) {
            BroadcastCommand(session, 1005, "????????????????????????????????????");
        }
    }


}
