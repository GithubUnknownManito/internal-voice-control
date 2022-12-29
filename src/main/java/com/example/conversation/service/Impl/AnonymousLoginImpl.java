package com.example.conversation.service.Impl;

import com.example.conversation.common.Result;
import com.example.conversation.dao.AnonymousUserDao;
import com.example.conversation.entity.AnonymousUser;
import com.example.conversation.service.IAnonymousLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnonymousLoginImpl implements IAnonymousLogin {
    @Autowired
    AnonymousUserDao dao;

    @Override
    public void register(AnonymousUser anonymousUser) throws Exception {
        if(!dao.isFind(anonymousUser.getId())){
            dao.Add(anonymousUser);
        } else {
            dao.Update(anonymousUser);
        }
    }

    @Override
    public void devastate(AnonymousUser anonymousUser) throws Exception {
        if(dao.isFind(anonymousUser.getId())){
            dao.Remove(anonymousUser.getId());
        } else {
        }
    }

    @Override
    public List<AnonymousUser> List(AnonymousUser anonymousUser) throws Exception {
        return dao.List(anonymousUser);
    }
}
