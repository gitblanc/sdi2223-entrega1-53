package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MessagesService {
    @Autowired
    private MessagesRepository messagesRepository;

    @PostConstruct
    public void init() {
    }

    /**
     * Método que añade un mensaje
     * @param message
     */
    public void addMessage(Message message){
        messagesRepository.save(message);
    }
}
