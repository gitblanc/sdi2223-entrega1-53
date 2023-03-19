package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.entities.User;
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

    /**
     * Método que devuelve todos los mensajes que haya en la base de datos
     * @return
     */
    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<Message>();
        messagesRepository.findAll().forEach(messages::add);
        return messages;
    }

    /**
     * Método que devuelve todos los mensajes de un usuario determinado
     * @param user
     * @return
     */
    public List<Message> getMessagesByUser(User user){
        List<Message> userMessages = messagesRepository.getMessagesByUser(user);
        return userMessages;
    }

    /**
     * Método que elimina todos los mensajes de un usuario determinadpo
     * @param user
     */
    public void deleteMessagesWithUser(User user) {
        List<Message> messages = getMessagesByUser(user);
        for(Message m: messages)
            messagesRepository.deleteById(m.getId());
    }
}
