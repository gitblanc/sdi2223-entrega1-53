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

    public Message(){}

    public Message(User author, String text) {
        this.date = new Date();
        this.author = author;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(date, message.date) && Objects.equals(author, message.author) && Objects.equals(text, message.text) && Objects.equals(chat, message.chat);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(date, author, text, chat);
    }
}
