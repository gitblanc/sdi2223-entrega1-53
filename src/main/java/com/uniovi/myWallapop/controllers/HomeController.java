package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.Log;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.LogsService;
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

    @Autowired
    private LogsService logsService;


    /**
     * Añade el usuario con el que se ha registrado
     * @param model
     * @return
     */
    @RequestMapping
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        String description = "Un usuario ha accedido a la vista index";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "index";
    }
}
