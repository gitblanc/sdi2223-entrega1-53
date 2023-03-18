package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatsRepository extends CrudRepository<Chat, Long> {

    /**
     * Query que devuelve el chat dependiendo del id de usuario que se le pone como parámetro
     * @param userId
     * @return
     */
    @Query("SELECT distinct c FROM Chat c, Offer o WHERE c.user.id = ?1 OR" +
            "( c.offer.id = o.id and o.seller.id = ?1)")
    List<Chat> getChatsByUserId(Long userId);

    /**
     * Query que devuelve el chat dependiendo del usuario y de la oferta que se le pasra como parámetros
     * @param user
     * @param offer
     * @return
     */
    @Query("SELECT c FROM Chat c WHERE c.user=?1 AND c.offer=?2")
    Chat getByUserAndOffer(User user, Offer offer);
}
