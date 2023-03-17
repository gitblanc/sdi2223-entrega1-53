package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="chats", uniqueConstraints = {
        @UniqueConstraint(columnNames={"offer_id", "user_id"})
})
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Offer offer;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private Set<Message> messages = new LinkedHashSet<>(); //the list of messages of the chat

    public Chat(){}

    public Chat(User user, Offer offer){
        this.user=user;
        this.offer=offer;
    }

    public Chat(Offer offer, User buyer) {
        this.offer=offer;
        this.user=buyer;
    }

    public void addMessage(Message message) {
        message.setChat(this);
        this.messages.add(message);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Message> getMessages() {
        return messages.stream()
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
