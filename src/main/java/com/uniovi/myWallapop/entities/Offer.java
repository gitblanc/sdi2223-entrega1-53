package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="offers")
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private Double amount;

    private Date date;

    private boolean sold = false;

    private boolean buy = true;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer")
    private Set<Chat> chats;


    /**
     * Constructor sin parámetros
     */
    public Offer() {
    }


    /**
     * Constructor que recibe los parámetros del título,
     * descripción, el precio de la oferta y el usuario que realiza
     * la oferta
     * @param title String
     * @param description String
     * @param amount Double
     * @param seller User
     */
    public Offer(String title, String description, Double amount, User seller) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = new Date();
        this.seller = seller;
    }


    /**
     * Retorna el valor del atributo id
     * @return Long
     */
    public Long getId() {
        return id;
    }


    /**
     * Cambia el valor del atributo id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Cambia el valor del atributo title
     * @return String
     */
    public String getTitle() {
        return title;
    }


    /**
     * Cambia el valor del atributo title
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Retorna el valor del atributo description
     * @return String
     */
    public String getDescription() {
        return description;
    }


    /**
     * Cambia el valor de atributo description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Retorna el valor del atributo amount
     * @return Double
     */
    public Double getAmount() {
        return amount;
    }


    /**
     * Cambia el valor del atributo amount
     * @param amount Double
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }


    /**
     * Retorna el valor del atributo date
     * @return Date
     */
    public Date getDate() {
        return date;
    }


    /**
     * Cambia el valor del atributo date
     * @param date Date
     */
    public void setDate(Date date) {
        this.date = date;
    }


    /**
     * Comprueba si la oferta está vendida o no
     * @return boolean
     */
    public boolean isSold() {
        return sold;
    }


    /**
     * Cambia si la oferta está vendida o no
     * @param sold boolean
     */
    public void setSold(boolean sold) {
        this.sold = sold;
    }


    /**
     * Devuelve el valor del atributo seller
     * @return User
     */
    public User getSeller() {
        return seller;
    }


    /**
     * Cambia el valor del atributo seller
     * @param seller User
     */
    public void setSeller(User seller) {
        this.seller = seller;
    }


    /**
     * Devuelve el valor del atributo buyer
     * @return User
     */
    public User getBuyer() {
        return buyer;
    }


    /**
     * Cambia el valor del atributo buyer
     * @param buyer User
     */
    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }


    /**
     * Devuelve el valor del atributo chats
     * @return Set<Chat>
     */
    public Set<Chat> getChats() {
        return chats;
    }


    /**
     * Cambia el valor del atributo chats
     * @param chats Set<Chat>
     */
    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }


    /**
     * Comprueba si está comprada
     * @return boolean
     */
    public boolean isBuy() {
        return buy;
    }


    /**
     * Cambia el valor del atributo buy
     * @param buy boolean
     */
    public void setBuy(boolean buy) {
        this.buy = buy;
    }


    /**
     * Método equals de Offer
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (isSold() != offer.isSold()) return false;
        if (getId() != null ? !getId().equals(offer.getId()) : offer.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(offer.getTitle()) : offer.getTitle() != null) return false;
        if (getDescription() != null ? !getDescription().equals(offer.getDescription()) : offer.getDescription() != null)
            return false;
        if (getAmount() != null ? !getAmount().equals(offer.getAmount()) : offer.getAmount() != null) return false;
        if (getDate() != null ? !getDate().equals(offer.getDate()) : offer.getDate() != null) return false;
        if (getSeller() != null ? !getSeller().equals(offer.getSeller()) : offer.getSeller() != null) return false;
        return getBuyer() != null ? getBuyer().equals(offer.getBuyer()) : offer.getBuyer() == null;
    }


    /**
     * Método hashCode  de Offer
     * @return int
     */
    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (isSold() ? 1 : 0);
        result = 31 * result + (getSeller() != null ? getSeller().hashCode() : 0);
        result = 31 * result + (getBuyer() != null ? getBuyer().hashCode() : 0);
        return result;
    }


    /**
     * Método toString de Offer
     * @return String
     */
    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", sold=" + sold +
                ", seller=" + seller +
                ", buyer=" + buyer +
                '}';
    }
}
