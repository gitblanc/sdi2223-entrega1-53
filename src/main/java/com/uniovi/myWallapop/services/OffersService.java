package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
}
