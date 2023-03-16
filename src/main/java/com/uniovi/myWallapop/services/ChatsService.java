package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.repositories.ChatsRepository;
import com.uniovi.myWallapop.repositories.MessagesRepository;
import com.uniovi.myWallapop.repositories.OffersRepository;
import com.uniovi.myWallapop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatsService {
    @Autowired
    private ChatsRepository chatsRepository;
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private OffersRepository offersRepository;

    @PostConstruct
    public void init() {
    }

    public void addChat(Chat chat){
        chatsRepository.save(chat);
    }

    public List<Chat> getChats() {
        List<Chat> chats = new ArrayList<Chat>();
        chatsRepository.findAll().forEach(chats::add);
        return chats;
    }

    public List<Chat> getChatsByUserId(Long userid){
        List<Chat> userChats = chatsRepository.getChatsByUserId(userid);
        return userChats;
    }

    public void deleteChatsWithUserId(Long id) {
        List<Chat> chats = getChatsByUserId(id);
        for(Chat c: chats)
            chatsRepository.deleteById(c.getId());
    }

    /**
     * Devuelve el chat si ya existe entre dos usuarios. Se puede abrir tanto desde la lista de chats como
     * desde la lista de ofertas. Puede devolver un chat nuevo o uno que ya existe.
     * Si no existe, crea uno nuevo.
     * @param activeUser, el usuario que abre el chat
     * @param offer, la oferta (y por tanto el usuario que la publicó)
     * @param otherUserEmail, el email del otro usuario
     * @return el chat entre ambos usuarios
     */
    public Chat getChatOrCreate(User activeUser, Offer offer, String otherUserEmail) {
        Chat chat;
        // si el usuario que solicita el chat es el vendedor
        if(activeUser.getEmail().equals(offer.getSeller().getEmail()))
            chat = chatsRepository.getByUserAndOffer(usersRepository.findByEmail(otherUserEmail), offer);
        else
            chat = chatsRepository.getByUserAndOffer(activeUser, offer);

        if(chat==null){
            chat = new Chat(activeUser, offer);
            addChat(chat);
        }
        return chat;
    }

    /**
     * Añade un mensaje al chat.
     * @param msg
     */
    public void addMessage(Message msg) {
        messagesRepository.save(msg);
    }

    /**
     * Encuentra un chat por su ID
     * @param chatId
     * @return
     */
    public Chat getChatById(Long chatId) {
        Optional<Chat> chat = chatsRepository.findById(chatId);
        return chat.get();
    }

    /**
     * Devuelve el email del otro usuario (con el que se está hablando)
     * @param user, usuario logeado
     * @param chatId, id del chat
     * @return email del otro usuario
     */
    public String findOtherUser(User user, Long chatId) {
        Chat chat = chatsRepository.findById(chatId).get();
        if (chat.getOffer().getSeller().getEmail().equals(user.getEmail())) {
            return chat.getUser().getEmail();
        } else {
            return chat.getOffer().getSeller().getEmail();
        }
    }
}
