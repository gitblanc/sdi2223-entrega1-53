package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Chat {

    @EmbeddedId
    private ChatKey chatKey; //the identifier of the chat

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<>(); //the list of messages of the chat

    public Chat(){}

    public Chat(Offer offer, User buyer) {
        this.chatKey = new ChatKey(offer.getId(), buyer.getId());
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
