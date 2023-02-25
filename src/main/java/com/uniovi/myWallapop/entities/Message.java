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

    private Long authorId;

    private String text;

    @ManyToOne
    private Chat chat;

    public Message(){}

    public Message(Long authorId, String text) {
        this.date = new Date();
        this.authorId = authorId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

        if (!Objects.equals(id, message.getId())) return false;
        if (!Objects.equals(date, message.getDate())) return false;
        if (!Objects.equals(authorId, message.getAuthorId())) return false;
        return Objects.equals(text, message.getText());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", authorId=" + authorId +
                ", text='" + text + '\'' +
                '}';
    }
}
