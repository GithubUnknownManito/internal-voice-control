package com.example.conversation.service.Impl;

import com.example.conversation.dao.AnonymousUserDao;
import com.example.conversation.entity.AnonymousUser;
import com.example.conversation.entity.RoomEntity;
import com.example.conversation.service.IRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomImpl implements IRoom {
    @Autowired
    AnonymousUserDao anonymousUserDao;
    @Override
    public List<RoomEntity> getRoomList() {
        List<RoomEntity> list = new ArrayList<>();
        list.add(new RoomEntity("000000", "公共大厅"));
        list.add(new RoomEntity("000001", "我的世界厅"));
        list.add(new RoomEntity("000002", "APEX大厅"));
        list.add(new RoomEntity("000003", "PUBG大厅"));
        list.add(new RoomEntity("000004", "CSGO大厅"));
        list.add(new RoomEntity("000005", "UNO大厅"));
        list.add(new RoomEntity("000006", "小游戏大厅"));
        list.add(new RoomEntity("000007", "给300块的大厅", false));
        list.forEach(e ->{
            AnonymousUser anonymousUser = new AnonymousUser();
            anonymousUser.setRoomId(e.getId());
            int userNumber = anonymousUserDao.List(anonymousUser).size();
            e.setOnlineNumber(userNumber);
        });
        return list;
    }
}
