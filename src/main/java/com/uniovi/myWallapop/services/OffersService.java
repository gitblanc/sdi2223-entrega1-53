package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.error.Mark;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class OffersService {
    @Autowired
    private OffersRepository offersRepository;

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
}
