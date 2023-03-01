package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Message> messages = new HashSet<>(); //the list of messages of the chat

    public Chat(){}

    public Chat(Offer offer, User buyer) {
        this.offer=offer;
        this.user=buyer;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
