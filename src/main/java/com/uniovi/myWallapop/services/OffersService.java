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
    }

    /**
     * Método que devuelve todas las ofertas que haya
     * @param pageable
     * @param user
     * @return
     */
    public Page<Offer> getOffers(Pageable pageable, User user) {
        Page<Offer> offers = offersRepository.findAll(pageable);
        return offers;
    }

    /**
     * Método que devuelve todoas las ofertas dependiendo del id del usuario
     * @param userid
     * @return
     */
    public List<Offer> getOffersByUserId(Long userid){
        List<Offer> userOffers = offersRepository.getOffersByUserId(userid);
        return userOffers;
    }


    /**
     * Método que elimina una oferta de un determinado usuario
     * @param id
     */
    public void deleteOffersWithUserId(Long id) {
        List<Offer> offers = getOffersByUserId(id);
        for(Offer o: offers)
            offersRepository.deleteById(o.getId());
    }


    /**
     * Método que compra una oferta dependiendo de un id
     * @param id
     * @return
     */
    public String buyOffer(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = usersRepository.findByEmail(email);
        Offer offer = offersRepository.findById(id).get();

        if (user.getAmount() < offer.getAmount()) {
            offer.setBuy(false);
            return "error.buy.offer.amount";
        }
        if(offer.isSold())
            return "error.buy.offer.sold";
        if(user.getPostedOffers().contains(offer))
            return "error.buy.offer.your.offer";

        double amount = user.getAmount() - offer.getAmount();
        user.setAmount(amount);
        offer.setSold(true);
        user.getBoughtOffers().add(offer);
        offer.setBuyer(user);
        offer.setBuy(true);

        usersRepository.updateAmount(user.getAmount(), user.getId());
        offersRepository.updateSold(true, id);
        return null;
    }

    /**
     * Método que identifica que esa oferta no puede ser comprada
     * @param id
     * @return
     */
    public String cannotBuyOffer(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = usersRepository.findByEmail(email);
        Offer offer = offersRepository.findById(id).get();

        offer.setBuy(false);
        return null;
    }

    /**
     * Método que devuelve las ofertas que contengan una determinaa cadena de texto
     * @param pageable
     * @param searchText
     * @return
     */
    public Page<Offer> searchOfferByTitle(Pageable pageable,String searchText) {
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        searchText = "%"+searchText+"%";
        offers = offersRepository.searchOfferByTitle(pageable,searchText);
        return offers;
    }

    /**
     * Método que devuleve las ofertas publicadas que no son de un determinado usuario
     * @param pageable
     * @param user
     * @return
     */
    public Page<Offer> getOffersNotYours(Pageable pageable, User user) {
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        offers = offersRepository.getOfferThatYouCanBuy(pageable, user);
        return offers;
    }


    public void editOffer(Offer offer) {
        Offer originalOffer = getOffer(offer.getId());
        originalOffer.setAmount(offer.getAmount());
        originalOffer.setTitle(offer.getTitle());
        originalOffer.setDescription(offer.getDescription());
    }
}
