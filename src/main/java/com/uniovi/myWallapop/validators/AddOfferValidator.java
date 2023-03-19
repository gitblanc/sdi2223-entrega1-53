package com.uniovi.myWallapop.validators;

import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddOfferValidator implements Validator {
    @Autowired
    private OffersService offersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Método que hace la validación del formulario de añadir una oferta
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.offer.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.offer.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.offer.empty");

        if (offer.getTitle().length() < 5 || offer.getTitle().length() > 24) {
            errors.rejectValue("title", "Error.offer.title.length");
        }
        if(offer.getAmount() < 0){
            errors.rejectValue("amount", "Error.offer.price.negative");
        }
    }

}
