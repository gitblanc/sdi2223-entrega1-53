package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Chat {

    @EmbeddedId
    private ChatKey chatKey; //the identifier of the chat

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages; //the list of messages of the chat
}
