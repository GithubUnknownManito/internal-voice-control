package com.example.conversation.controller;

import com.example.conversation.common.Result;
import com.example.conversation.entity.AnonymousUser;
import com.example.conversation.service.IAnonymousLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/module/anonymous")
@CrossOrigin(origins = "*")

public class AnonymousLoginController {

    @Autowired
    IAnonymousLogin iAnonymousLogin;

    @PostMapping("/register")
    public Result register(@RequestBody AnonymousUser dto) {
        try {
            iAnonymousLogin.register(dto);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e);
        }
    }

    @PostMapping("/devastate")
    public Result devastate(@RequestBody AnonymousUser dto){
        try {
            iAnonymousLogin.devastate(dto);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e);
        }
    }

    @PostMapping("/list")
    public Result list(@RequestBody AnonymousUser dto){
        try {
            return Result.success(iAnonymousLogin.List(dto));
        } catch (Exception e) {
            return Result.fail(e);
        }
    }
}
