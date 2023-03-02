package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.repositories.ChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
}
