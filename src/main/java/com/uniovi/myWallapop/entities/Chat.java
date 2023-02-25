package com.uniovi.myWallapop.entities;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.OneToMany;
import java.util.List;

public class Chat {
    @EmbeddedId
    private ChatKey chatKey; //the identifier of the chat

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages; //the list of messages of the chat
}
