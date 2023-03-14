package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {
    @Query("SELECT r FROM Offer r WHERE r.seller = ?1 ORDER BY r.amount ASC")
    Page<Offer> findAllByUser(Pageable pageable, User user);

    @Query("SELECT distinct o FROM Offer o WHERE o.buyer.id = ?1 or o.seller.id = ?1")
    List<Offer> getOffersByUserId(Long userid);

    /**
     * Actualiza la propiedad sold de la nota
     * @param sold: nuevo valor de sold
     * @param id: id de la nota
     */
    @Modifying
    @Transactional
    @Query("UPDATE Offer SET sold = ?1 WHERE id = ?2")
    void updateSold(Boolean sold, Long id);
 
}
