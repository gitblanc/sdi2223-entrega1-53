package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatsRepository extends CrudRepository<Chat, Long> {

    @Query("SELECT distinct c FROM Chat c, Offer o WHERE c.user.id = ?1 OR" +
            "( c.offer.id = o.id and o.seller.id = ?1)")
    List<Chat> getChatsByUserId(Long userId);


}
