package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.repositories.ChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatsService {
    @Autowired
    private ChatsRepository chatsRepository;

    @PostConstruct
    public void init() {
    }

    public void addChat(Chat chat){
        chatsRepository.save(chat);
    }

    public List<Chat> getChats() {
        List<Chat> chats = new ArrayList<Chat>();
        chatsRepository.findAll().forEach(chats::add);
        return chats;
    }

    public List<Chat> getChatsByUserId(Long userid){
        List<Chat> userChats = chatsRepository.getChatsByUserId(userid);
        return userChats;
    }

    public void deleteChatsWithUserId(Long id) {
        List<Chat> chats = getChatsByUserId(id);
        for(Chat c: chats)
            chatsRepository.deleteById(c.getId());
    }
}
