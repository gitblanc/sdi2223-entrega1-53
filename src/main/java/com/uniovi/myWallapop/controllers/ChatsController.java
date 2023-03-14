package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Log;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.ChatsService;
import com.uniovi.myWallapop.services.OffersService;
import com.uniovi.myWallapop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ChatsController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private ChatsService chatsService;
    @Autowired
    private OffersService offersService;

    /**
     * Petición GET que devuelve la vista del chat entre el usuario que compra
     * y el usuario que ha publicado la oferta
     * @param model
     * @param offerId
     * @return
     */
    @RequestMapping("/chat/{offerId}")
    public String openChat(Model model, @PathVariable Long offerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);

        Offer offer = offersService.getOffer(offerId);
        Chat chat = chatsService.getChatOrCreate(activeUser, offer);
        model.addAttribute("chat", chat);

        return "chats/chat";
    }
}
