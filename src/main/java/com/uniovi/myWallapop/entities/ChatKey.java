package com.uniovi.myWallapop.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ChatKey implements Serializable {
    private Long possibleBuyerId; // possible buyer identifier

    private Long offerId; // offer identifier

    public ChatKey() {
    }

    public ChatKey(Long possibleBuyerId, Long offerId) {
        this.possibleBuyerId = possibleBuyerId;
        this.offerId = offerId;
    }
}
