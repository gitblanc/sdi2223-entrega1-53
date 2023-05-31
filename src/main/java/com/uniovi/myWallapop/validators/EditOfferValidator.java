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
public class EditOfferValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Método que hace la validación del formulario de editar una oferta
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.offer.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.offer.empty");
        if(offer.getAmount() < 0){
            errors.rejectValue("amount", "Error.offer.price.negative");
        }
    }

}
