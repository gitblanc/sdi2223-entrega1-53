package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class InsertSampleDataService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ChatsService chatsService;
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private OffersService offersService;

    @PostConstruct
    public void init() {
        User user1 = new User("Eduardo", "Blanco Bielsa", "uo285176@uniovi.es");
        user1.setPassword("123456");
        User user2 = new User("MAnolo", "hola buenas", "prueba 2@uniovi.es");
        user2.setPassword("123456");
        User user3 = new User("Sergio", "Pérez h", "sodioido@gmail.com");
        user3.setPassword("123456");

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);

        Offer offer1 = new Offer("Oferta1", "EStA ES LA OFERTA MAS CHUPIWAY", 45.32, user1);
        Offer offer2 = new Offer("Oferta2", "EStA ES LA releche", 4456.78, user1);

        offersService.addOffer(offer1);
        offersService.addOffer(offer2);

        Chat chat1 = new Chat(offer1, user2);
        Chat chat2 = new Chat(offer2, user3);

        chat1.addMessage(new Message(user2.getId(), "hola buenas tardes"));
        chat2.addMessage(new Message(user2.getId(), "me caes mal"));

        chatsService.addChat(chat1);
        chatsService.addChat(chat2);


    }
}
