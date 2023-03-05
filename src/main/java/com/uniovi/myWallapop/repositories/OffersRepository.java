package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {

    @Query("SELECT distinct o FROM Offer o WHERE o.buyer.id = ?1 or o.seller.id = ?1")
    List<Offer> getOffersByUserId(Long userid);
}
