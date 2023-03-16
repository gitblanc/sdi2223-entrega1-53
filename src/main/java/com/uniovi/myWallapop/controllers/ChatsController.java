package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.*;
import com.uniovi.myWallapop.services.ChatsService;
import com.uniovi.myWallapop.services.LogsService;
import com.uniovi.myWallapop.services.OffersService;
import com.uniovi.myWallapop.services.UsersService;
import com.uniovi.myWallapop.validators.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ChatsController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private ChatsService chatsService;
    @Autowired
    private OffersService offersService;
    @Autowired
    private LogsService logsService;
    @Autowired
    private MessageValidator messageValidator;

    /**
     * Petición GET que devuelve la vista del chat entre el usuario que compra
     * y el usuario que ha publicado la oferta
     * @param model
     * @param offerId
     * @return
     */
    @RequestMapping("/chat/{offerId}")
    public String openChat(Model model, @PathVariable Long offerId, @RequestParam String otherUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);

        Offer offer = offersService.getOffer(offerId);
        Chat chat = chatsService.getChatOrCreate(activeUser, offer, otherUser);

        model.addAttribute("message", new Message());
        model.addAttribute("offer", offer);
        model.addAttribute("chat", chat);

        return "chats/chat";
    }

    /**
     * Petición POST para el envío de un mensaje desde el formulario de la vista del chat
     * @param model
     * @param msg
     * @param result
     * @param principal
     * @param offerId
     * @param chatId
     * @return
     */
    @RequestMapping(value = "/chat/{offerId}/{chatId}/sendMessage", method = RequestMethod.POST)
    public String sendMessage(Model model, @Validated Message msg, BindingResult result, Principal principal,
                              @PathVariable Long offerId, @PathVariable Long chatId) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("user", user);
        msg.setDate(new Date());
        msg.setAuthor(user);
        msg.setChat(chatsService.getChatById(chatId));
        messageValidator.validate(msg, result);

        if (!result.hasErrors()) {
            chatsService.addMessage(msg);
        }

        String otherUser = chatsService.findOtherUser(user, chatId);
        return "redirect:/chat/{offerId}"+"?otherUser="+otherUser;
    }

    /**
     * Petición GET para la lista de conversaciones del usuario
     * @param model
     * @return
     */
    @RequestMapping("/chat/list")
    public String getAllOffers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);

        List<Chat> chats = chatsService.getChatsByUserId(activeUser.getId());
        model.addAttribute("chatsList", chats);

        return "chats/chatList";
    }
}
