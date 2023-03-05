package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.OffersService;
import com.uniovi.myWallapop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class OffersController {
    @Autowired
    private OffersService offersService;

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/offer/{id}/buy", method = RequestMethod.GET)
    public String setSoldTrue(@PathVariable Long id) {
        String error = offersService.buyOffer(id);
        return "redirect:/offer/bought";
    }

    @RequestMapping(value = "/offer/bought", method = RequestMethod.GET)
    public String getBoughtOffers(Model model, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("offersList", user.getBoughtOffers().stream().toList()) ;
        return "offer/bought";
    }
}