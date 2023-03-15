package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Clase para inicialización de la base de datos
 */
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
    @Autowired
    private RolesService rolesService;



    @PostConstruct
    public void init() {
        User user0 = new User("Admin", "Admin Username", "admin@email.com");
        user0.setPassword("admin");
        user0.setRole(rolesService.getRoles()[1]);
        usersService.addUser(user0);

        List<User> usersToInsert = new ArrayList<>();
        User user1 = new User("Eduardo", "Blanco Bielsa", "user01@email.com");
        user1.setPassword("user01");
        user1.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user1);
        User user2 = new User("MAnolo", "hola buenas", "user02@email.com");
        user2.setPassword("user01");
        user2.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user2);
        User user3 = new User("Sergio", "Pérez h", "user03@email.com");
        user3.setPassword("user01");
        user3.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user3);
        User user4 = new User("user04", "apellido04", "user04@email.com");
        user4.setPassword("user01");
        user4.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user4);
        User user5 = new User("user05", "apellido05", "user05@email.com");
        user5.setPassword("user01");
        user5.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user5);
        User user6 = new User("user06", "apellido06", "user06@email.com");
        user6.setPassword("user01");
        user6.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user6);
        User user7 = new User("user07", "apellido07", "user07@email.com");
        user7.setPassword("user01");
        user7.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user7);
        User user8 = new User("user08", "apellido08", "user08@email.com");
        user8.setPassword("user01");
        user8.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user8);
        User user9 = new User("user09", "apellido09", "user09@email.com");
        user9.setPassword("user01");
        user9.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user9);
        User user10 = new User("user10", "apellido10", "user10@email.com");
        user10.setPassword("user01");
        user10.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user10);
        User user11 = new User("user11", "apellido11", "user11@email.com");
        user11.setPassword("user01");
        user11.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user11);
        User user12 = new User("user12", "apellido12", "user12@email.com");
        user12.setPassword("user01");
        user12.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user12);
        User user13 = new User("user13", "apellido13", "user13@email.com");
        user13.setPassword("user01");
        user13.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user13);
        User user14 = new User("user14", "apellido14", "user14@email.com");
        user14.setPassword("user01");
        user14.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user14);
        User user15 = new User("user15", "apellido15", "user15@email.com");
        user15.setPassword("user01");
        user15.setRole(rolesService.getRoles()[0]);
        usersToInsert.add(user15);

        Set<Offer> postedOffers;
        for (User user : usersToInsert) {
            postedOffers = new HashSet<>();
            for (int i = 1; i < 11; i++) {
                Offer offerToInsert = new Offer("Oferta-"+user.getName()+"-n"+i,
                        "Descripcion", i*10.0, user);
                postedOffers.add(offerToInsert);
            }
            user.setPostedOffers(postedOffers);
            usersService.addUser(user);
        }


        Offer offer1 = new Offer("Oferta1", "EStA ES LA OFERTA MAS CHUPIWAY", 45.32, user1);
        user1.getPostedOffers().add(offer1);
        Offer offer2 = new Offer("Oferta2", "EStA ES LA releche", 4456.78, user1);
        user1.getPostedOffers().add(offer2);
        Offer offer3= new Offer("pruebisisima vaya", "a ver si funciona", 46.78, user1);
        user1.getPostedOffers().add(offer3);
        Offer offer4= new Offer("pruebisisima vaya", "a ver si funciona", 46.78, user2);
        user2.getPostedOffers().add(offer4);
        Offer offer5= new Offer("pruebisisima vaya por 2", "a ver si funciona", 5.2, user2);
        user2.getPostedOffers().add(offer5);
        Offer offer6= new Offer("phola que tal", "a ver si funciona", 50.2, user2);
        user2.getPostedOffers().add(offer6);
        Offer offer7= new Offer("pruea pagl", "fucionara", 48.2, user3);
        user3.getPostedOffers().add(offer7);
        Offer offer8= new Offer("usqueda total vaya", " funciona", 50.2, user3);
        user3.getPostedOffers().add(offer8);
        Offer offer9= new Offer("ultimo", " funcionaaaa", 50.4, user3);
        user3.getPostedOffers().add(offer9);

        offersService.addOffer(offer1);
        offersService.addOffer(offer2);
        offersService.addOffer(offer3);
        offersService.addOffer(offer4);
        offersService.addOffer(offer5);
        offersService.addOffer(offer6);
        offersService.addOffer(offer7);
        offersService.addOffer(offer8);
        offersService.addOffer(offer9);


        Chat chat1 = new Chat(offer1, user2);
        Chat chat2 = new Chat(offer2, user3);

        Message m1 = new Message(user2.getId(), "hola buenas tardes");
        Message m2 = new Message(user2.getId(), "hola buenas tardes");

        chat1.addMessage(m1);
        chat2.addMessage(m2);

        chatsService.addChat(chat1);
        chatsService.addChat(chat2);

    }

}
