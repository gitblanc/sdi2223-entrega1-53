package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @ManyToOne
    private User author;

    private String text;

    @ManyToOne
    private Chat chat;

    /**
     *Constructor sin parámetros
     */
    public Message(){}


    /**
     * Constructor que recibe por parámetros
     * el autor del mensaje y el tecto de este
     * @param author
     * @param text
     */
    public Message(User author, String text) {
        this.date = new Date();
        this.author = author;
        this.text = text;
    }


    /**
     * Retorna el valor del atributo id
     * @return
     */
    public Long getId() {
        return id;
    }


    /**
     * Cambia el valor del atributo id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Retorna el valor del atributo date
     * @return
     */
    public Date getDate() {
        return date;
    }


    /**
     * Cambia el valor del atributo date
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }


    /**
     * Retorna el valor del atributo author
     * @return
     */
    public User getAuthor() {
        return author;
    }


    /**
     * Cambia el valor del atributo
     * author
     * @param author
     */
    public void setAuthor(User author) {
        this.author = author;
    }


    /**
     * Retorna el valor del atributo text
     * @return
     */
    public String getText() {
        return text;
    }


    /**
     * Cambia el valor del atributo text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }


    /**
     * Devuelve el valor del atributo chat
     * @return
     */
    public Chat getChat() {
        return chat;
    }


    /**
     * Cambia el valor del atributo chat
     * @param chat
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }


    /**
     * Método equals de Message
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(date, message.date) && Objects.equals(author, message.author) && Objects.equals(text, message.text) && Objects.equals(chat, message.chat);
    }


    /**
     * Método toString de Message
     * @return
     */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", chat=" + chat +
                '}';
    }


    /**
     * Método hashCode de Message
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(date, author, text, chat);
    }
}
