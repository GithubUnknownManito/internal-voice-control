package com.example.conversation.controller;

import com.example.conversation.common.Result;
import com.example.conversation.entity.RoomEntity;
import com.example.conversation.service.IRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/module/room")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    IRoom iRoom;
    @RequestMapping("/list")
    public Result list() {

        try {
            return Result.success(iRoom.getRoomList());
        } catch (Exception e) {
            return Result.fail(e);
        }
    }
}
