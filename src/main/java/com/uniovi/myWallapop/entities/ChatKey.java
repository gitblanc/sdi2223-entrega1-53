package com.uniovi.myWallapop.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Esta clase es el resultado de usar una clave compuesta para prevenir duplicados de chats
 */
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
