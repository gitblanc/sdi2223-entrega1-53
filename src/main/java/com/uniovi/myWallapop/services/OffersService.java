package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class OffersService {
    @Autowired
    private OffersRepository offersRepository;

    @PostConstruct
    public void init() {
    }

    public void addOffer(Offer offer){
        offersRepository.save(offer);
    }

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
}
