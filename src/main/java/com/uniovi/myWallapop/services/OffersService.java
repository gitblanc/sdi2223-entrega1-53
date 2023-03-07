package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.repositories.OffersRepository;
import com.uniovi.myWallapop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.error.Mark;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OffersService {
    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    public void init() {
    }

    /**
     * Devuelve la oferta con la ID especificada
     * @param id
     * @return
     */
    public Offer getOffer(Long id) {
        return offersRepository.findById(id).get();
    }

    /**
     * Añade la oferta especificada
     * @param offer
     */
    public void addOffer(Offer offer){
        offersRepository.save(offer);
    }

    /**
     * Devuelve una lista paginada con las ofertas publicadas por el usuario especificado
     * @param user
     * @param pageable
     * @return
     */
    public Page<Offer> getPostedOffers(User user, Pageable pageable) {
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        offers = offersRepository.findAllByUser(pageable, user);
        return offers;
    }

    /**
     * Borra la oferta con el ID especificado, comprobando primer si es el creador de la oferta.
     * @param id
     * @param user, email del usuario
     */
    public void deleteOffer(Long id, String user) {
        Optional<Offer> offer = offersRepository.findById(id);
        if (offer.isPresent() && offer.get().getSeller().getEmail().equals(user))
            offersRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    public List<Offer> getOffers() {
        List<Offer> offers = new ArrayList<Offer>();
        offersRepository.findAll().forEach(offers::add);
        return offers;
    }

    public List<Offer> getOffersByUserId(Long userid){
        List<Offer> userOffers = offersRepository.getOffersByUserId(userid);
        return userOffers;
    }


    public void deleteOffersWithUserId(Long id) {
        List<Offer> offers = getOffersByUserId(id);
        for(Offer o: offers)
            offersRepository.deleteById(o.getId());
    }


    public String buyOffer(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = usersRepository.findByEmail(email);
        Offer offer = offersRepository.findById(id).get();

        if (user.getAmount() < offer.getAmount())
            return "error.buyoffer.amount";
        if(offer.isSold())
            return "error.buyoffer.sold";
        if(user.getPostedOffers().contains(offer))
            return "error.buyoffer.youroffer";

        user.setAmount(user.getAmount() - offer.getAmount());
        offer.setSold(true);
        user.getBoughtOffers().add(offer);
        offer.setBuyer(user);

        usersRepository.updateAmount(user.getAmount(), user.getId());
        offersRepository.updateSold(true, id);
        return null;
    }
}
