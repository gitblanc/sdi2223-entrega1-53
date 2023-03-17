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


    /**
     * Constructor sin parámetros
     */
    public Chat(){}


    /**
     * Constructor que recibe el usuario con el que se hace
     * el chat y la oferta asociada
     * @param user
     * @param offer
     */
    public Chat(User user, Offer offer){
        this.user=user;
        this.offer=offer;
    }


    /**
     * Constructor que recibe el usuario con el que se hace
     * el chat y la oferta asociada
     * @param buyer
     * @param offer
     */
    public Chat(Offer offer, User buyer) {
        this.offer=offer;
        this.user=buyer;
    }


    /**
     * Añade un mensaje a la lista
     * @param message
     */
    public void addMessage(Message message) {
        message.setChat(this);
        this.messages.add(message);
    }


    /**
     * Cambia el valor del atributo id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Devuelve el valor del atributo id
     * @return
     */
    public Long getId() {
        return id;
    }


    /**
     * Devuelve el valor del atributo offer
     * @return
     */
    public Offer getOffer() {
        return offer;
    }


    /**
     * Cambia el valor del atributo offer
     * @param offer
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }


    /**
     * Devuelve el valor del atributo user
     * @return
     */
    public User getUser() {
        return user;
    }


    /**
     * Cambia el valor del atributo user
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Recupera la lista de mensajes del chat
     * @return
     */
    public Set<Message> getMessages() {
        return messages.stream()
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    /**
     * Cambia el valor del atributo messages
     * @param messages
     */
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
