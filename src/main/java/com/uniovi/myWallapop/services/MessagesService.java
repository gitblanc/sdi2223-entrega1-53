package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Service
@Table(name="messages")
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

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<Message>();
        messagesRepository.findAll().forEach(messages::add);
        return messages;
    }

    public List<Message> getMessagesByUserId(Long userid){
        List<Message> userMessages = messagesRepository.getMessagesByUserId(userid);
        return userMessages;
    }

    public void deleteMessagesWithUserId(Long id) {
        List<Message> messages = getMessagesByUserId(id);
        for(Message m: messages)
            messagesRepository.deleteById(m.getId());
    }
}
