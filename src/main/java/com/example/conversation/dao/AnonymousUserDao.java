package com.example.conversation.dao;

import com.example.conversation.entity.AnonymousUser;
import com.example.conversation.socket.WebSocketSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AnonymousUserDao {
    private static CopyOnWriteArrayList<AnonymousUser> AnonymousUserList = new CopyOnWriteArrayList<>();

    public void Add(AnonymousUser anonymousUser){
        AnonymousUserList.add(anonymousUser);
    }
    public List<AnonymousUser> List(AnonymousUser anonymousUser){
        Stream<AnonymousUser> anonymousUserStream = AnonymousUserList.stream();
        if(anonymousUser.getId() != null){
            anonymousUserStream = anonymousUserStream.filter(e -> Objects.nonNull(e.getId()) && e.getId().equals(anonymousUser.getId()));
        }
        if(anonymousUser.getName() != null){
            anonymousUserStream = anonymousUserStream.filter(e -> Objects.nonNull(e.getId()) && e.getName().indexOf(anonymousUser.getName()) > -1 );
        }
        if(anonymousUser.getRoomId() != null){
            anonymousUserStream = anonymousUserStream.filter(e -> Objects.nonNull(e.getId()) && e.getRoomId().equals(anonymousUser.getRoomId()) );
        }
        return anonymousUserStream.collect(Collectors.toList());
    }
    public boolean isFind(String id){
        return AnonymousUserList.stream().filter(e -> Objects.nonNull(e.getId()) && e.getId().equals(id)).findAny().isPresent();
    }
    public AnonymousUser Find(String id){
        return AnonymousUserList.stream().filter(e -> Objects.nonNull(e.getId()) && e.getId().equals(id)).findAny().get();
    }

    public void Update(AnonymousUser anonymousUser){
        AnonymousUser a = Find(anonymousUser.getId());
        AnonymousUserList.remove(a);
        AnonymousUserList.add(anonymousUser);
    }

    public void Remove(String id){
        AnonymousUserList.remove(Find(id));
    }
}
