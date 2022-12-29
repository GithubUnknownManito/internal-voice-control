package com.example.conversation.controller;

import com.example.conversation.common.Result;
import com.example.conversation.entity.RoomEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/module/room")
@CrossOrigin(origins = "*")
public class RoomController {

    @RequestMapping("/list")
    public Result list() {
        List<RoomEntity> list = new ArrayList<>();
        list.add(new RoomEntity("000000", "公共大厅"));
        list.add(new RoomEntity("000001", "我的世界厅"));
        list.add(new RoomEntity("000002", "APEX大厅"));
        list.add(new RoomEntity("000003", "PUBG大厅"));
        list.add(new RoomEntity("000004", "CSGO大厅"));
        list.add(new RoomEntity("000005", "UNO大厅"));
        list.add(new RoomEntity("000006", "小游戏大厅"));
        list.add(new RoomEntity("000007", "茹300块厅", false));
        try {
            return Result.success(list);
        } catch (Exception e) {
            return Result.fail(e);
        }
    }
}
