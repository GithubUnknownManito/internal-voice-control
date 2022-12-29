package com.example.conversation.service;

import com.example.conversation.common.Result;
import com.example.conversation.dao.AnonymousUserDao;
import com.example.conversation.entity.AnonymousUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface IAnonymousLogin {
    public void register( AnonymousUser anonymousUser) throws Exception;

    public void devastate( AnonymousUser anonymousUser) throws Exception;

    public List<AnonymousUser> List(AnonymousUser anonymousUser)  throws Exception;
}
