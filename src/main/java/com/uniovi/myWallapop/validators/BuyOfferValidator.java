package com.uniovi.myWallapop.validators;

import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BuyOfferValidator implements Validator {
    @Autowired
    private OffersService offersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;
        double amount = offer.getAmount();
        boolean status = offer.isSold();

        User user = (User) target;

        if(user.getAmount() < amount) {
            errors.rejectValue("amount", "error.buy.offer.amount");
        }
        if(status) {
            errors.rejectValue("isSold", "error.buy.offer.sold");
        }
        if(user.getPostedOffers().contains(offer)) {
            errors.rejectValue("contains", "error.buy.offer.your.offer");
        }

    }

}
