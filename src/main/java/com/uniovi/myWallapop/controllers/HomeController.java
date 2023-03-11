package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    /**
     * Servicio donde estará la lógica realacionda con
     * los usuarios
     */
    @Autowired
    private UsersService usersService;


    @RequestMapping
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        return "index";
    }
}
