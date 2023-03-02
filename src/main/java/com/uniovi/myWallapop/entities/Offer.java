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

    private Double price;

    private Date date;

    private boolean sold = false;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer")
    private Set<Chat> chats;

    public Offer() {
    }

    /**
     * Constructor para rellenar el usuario que crea la oferta
     * La fecha es el momento de creación
     * @param seller
     */
    public Offer(User seller) {
        setSeller(seller);
        this.date = new Date();
    }

    public Offer(String title, String description, Double amount, User seller) {
        this.title = title;
        this.description = description;
        this.price = amount;
        this.date = new Date();
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double amount) {
        this.price = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

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
        if (getPrice() != null ? !getPrice().equals(offer.getPrice()) : offer.getPrice() != null) return false;
        if (getDate() != null ? !getDate().equals(offer.getDate()) : offer.getDate() != null) return false;
        if (getSeller() != null ? !getSeller().equals(offer.getSeller()) : offer.getSeller() != null) return false;
        return getBuyer() != null ? getBuyer().equals(offer.getBuyer()) : offer.getBuyer() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (isSold() ? 1 : 0);
        result = 31 * result + (getSeller() != null ? getSeller().hashCode() : 0);
        result = 31 * result + (getBuyer() != null ? getBuyer().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + price +
                ", date=" + date +
                ", sold=" + sold +
                ", seller=" + seller +
                ", buyer=" + buyer +
                '}';
    }
}
