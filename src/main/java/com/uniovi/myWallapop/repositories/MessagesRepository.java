package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message, Long> {

    /**
     * Query que devuleve los mensajes dependiendo del usuario que se le pasa por parámetro
     * @param user
     * @return
     */
    @Query("SELECT distinct m FROM Message m,Chat c, Offer o WHERE m.author = ?1 OR" +
            "( m.chat.id = c.id and c.offer.id = o.id and o.seller.id = ?1)")
    List<Message> getMessagesByUser(User user);
}
