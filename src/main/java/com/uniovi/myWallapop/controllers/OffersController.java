package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.OffersService;
import com.uniovi.myWallapop.services.UsersService;
import com.uniovi.myWallapop.validators.AddOfferValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.error.Mark;

import java.security.Principal;
import java.util.LinkedList;

@Controller
public class OffersController {
    @Autowired
    private OffersService offersService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private AddOfferValidator addOfferValidator;

    /**
     * Controlador para la petición GET de la lista de ofertas propias
     * @param model
     * @param pageable
     * @param principal
     * @return
     */
    @RequestMapping("/offer/list/posted")
    public String getListPosted(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        offers = offersService.getPostedOffers(user, pageable);

        model.addAttribute("offersList", offers.getContent());
        model.addAttribute("page", offers);
        return "offer/listPosted";
    }


    /**
     * Controlador para la petición GET del formulario de añadir oferta
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/offer/add")
    public String getOffer(Model model, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("offer", new Offer(user));
        return "offer/add";
    }

    /**
     * Controlador para la petición POST del formuario de añadir oferta
     * @param model
     * @param offer
     * @param result
     * @return
     */
    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String setOffer(Model model, @Validated Offer offer, BindingResult result) {
        addOfferValidator.validate(offer, result);
        if (result.hasErrors())
            return "offer/add";

        offersService.addOffer(offer);
        return "redirect:/offer/list/posted";
    }

    /**
     * Controlador para la petición GET de los detalles de una oferta concreta
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/offer/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("offer", offersService.getOffer(id));
        return "offer/details";
    }

    @RequestMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable Long id, Principal principal) {
        offersService.deleteOffer(id, principal.getName());
        return "redirect:/offer/listPosted";
    }
}
