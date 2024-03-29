package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.Log;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.LogsService;
import com.uniovi.myWallapop.services.OffersService;
import com.uniovi.myWallapop.services.UsersService;
import com.uniovi.myWallapop.validators.AddOfferValidator;
import com.uniovi.myWallapop.validators.EditOfferValidator;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.LinkedList;

@Controller
public class OffersController {
    @Autowired
    private OffersService offersService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private AddOfferValidator addOfferValidator;
    @Autowired
    private EditOfferValidator editOfferValidator;
    @Autowired
    private LogsService logsService;


    /**
     * Controlador para la petición GET de la lista de ofertas propias
     *
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

        model.addAttribute("user", user);
        model.addAttribute("offersList", offers.getContent());
        model.addAttribute("page", offers);
        String description = "Usuario con id " + user.getId() + " ha accedido a la vista de ofertas publicadas";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "offer/listPosted";
    }


    /**
     * Controlador para la petición GET del formulario de añadir oferta
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/offer/add")
    public String getOffer(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        model.addAttribute("offer", new Offer());
        String description = "Usuario con id " + activeUser.getId() + " ha accedido a la vista /offer/add";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "offer/add";
    }

    /**
     * Controlador para la petición POST del formuario de añadir oferta
     *
     * @param model
     * @param offer
     * @param result
     * @param principal
     * @return
     */
    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String setOffer(Model model, @Validated Offer offer, BindingResult result, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("user", user);
        offer.setSeller(user);
        offer.setDate(new Date());
        addOfferValidator.validate(offer, result);

        String description = "Usuario con id " + user.getId() + " ha accedido a la vista /offer/add";
        if (result.hasErrors()) {
            logsService.addLog(new Log(Log.Tipo.OFFER_ERR, description));
            return "offer/add";
        }

        offersService.addOffer(offer);
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "redirect:/offer/list/posted";
    }

    /**
     * Controlador para la petición POST de editar una oferta
     * @param id
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/offer/edit/{id}", method = RequestMethod.POST)
    public String editOfferPost(@PathVariable Long id, Model model, Principal principal, @Validated Offer offer,BindingResult result) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Offer offerToEdit = offersService.getOffer(id);
        if (!offerToEdit.getSeller().equals(user)) {
            return "redirect:/";
        }
        if (offerToEdit.isSold()) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("offer", offer);
        editOfferValidator.validate(offer, result);

        String description = "Usuario con id " + user.getId() + " ha hecho petición POST /offer/edit";
        if (result.hasErrors()) {
            logsService.addLog(new Log(Log.Tipo.OFFER_ERR, description));
            return "offer/edit";
        }

        offersService.editOffer(offer);
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "redirect:/offer/list/posted";
    }

    /**
     * Controlador para la petición GET de editar una oferta
     * @param id
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/offer/edit/{id}", method = RequestMethod.GET)
    public String editOffer(@PathVariable Long id, Model model, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Offer offer = offersService.getOffer(id);
        if (!offer.getSeller().equals(user)) {
            return "redirect:/";
        }
        if (offer.isSold()) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("offer", offer);
        String description = "Usuario con id " + user.getId() + " ha accedido a la vista /offer/edit";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "offer/edit";
    }

    /**
     * Controlador para la petición GET de los detalles de una oferta concreta
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/offer/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        model.addAttribute("offer", offersService.getOffer(id));

        String description = "Usuario con id " + activeUser.getId() + " ha accedido a los detalles de la oferta" + offersService.getOffer(id).getId();
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "offer/details";
    }

    /**
     * Controlador para la petición GET de borrar una oferta
     * @param id
     * @param principal
     * @return
     */
    @RequestMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable Long id, Principal principal) {
        offersService.deleteOffer(id, principal.getName());
        String description = "Se ha borrado la oferta: " + id;
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "redirect:/offer/list/posted";
    }

    /**
     * Controlador para la petición GET para la lista de ofertas compradas
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/offer/bought", method = RequestMethod.GET)
    public String getBoughtOffers(Model model, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("offersList", user.getBoughtOffers().stream().toList());
        String description = "Usuario con id " + user.getId() + " ha accedido a /offer/bought";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "offer/bought";
    }


    /**
     * Controlador para la petición GET de listar todas las ofertas
     * @param model
     * @param pageable
     * @param searchName
     * @return
     */
    @RequestMapping("/offer/list")
    public String getAllOffers(Model model,Pageable pageable, @RequestParam(value ="", required = false) String searchName,
                               @RequestParam(value ="", required = false) String error) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);

        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());

        if(searchName != null && !searchName.isEmpty()) {
            offers = offersService.searchOfferByTitle(pageable,searchName);
        } else {
            offers = offersService.getOffersNotYours(pageable,activeUser);
        }

        model.addAttribute("allOffersList", offers.getContent());
        model.addAttribute("page", offers);

        if(error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }
        String description = "Usuario con id " + activeUser.getId() + " ha accedido a la vista /offer/list";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "offer/list";
    }

    /**
     * Controlador para la petición GET para comprar una oferta
     * @param id id de la oferta
     * @return vista de todas las ofertas
     */
    @RequestMapping(value = "/offer/{id}/buy", method = RequestMethod.GET)
    public String setSoldTrue(@PathVariable Long id) {
        String error = offersService.buyOffer(id);
        if (error == null) {
            String description = "Se ha vendido la oferta: " + id;
            logsService.addLog(new Log(Log.Tipo.PET, description));
            return "redirect:/offer/list";
        }
        offersService.cannotBuyOffer(id);
        String description = "No se ha podido vender la oferta: " + id;
        logsService.addLog(new Log(Log.Tipo.OFFER_ERR, description));
        return "redirect:/offer/list?error="+error;
    }
}