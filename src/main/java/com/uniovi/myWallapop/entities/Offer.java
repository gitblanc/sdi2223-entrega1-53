package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Double amount;

    private Date date;

    private boolean sold;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer")
    private User buyer;

    @OneToMany(cascade = CascadeType.ALL)
    @MapKey(name = "chat")
    private Map<ChatKey, Chat> chats;
}
