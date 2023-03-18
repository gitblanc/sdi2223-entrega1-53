package com.uniovi.myWallapop.validators;

import com.uniovi.myWallapop.entities.Message;
import com.uniovi.myWallapop.entities.Offer;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MessageValidator implements Validator {
    @Autowired
    private OffersService offersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Método que valida el formulario de los mensajes
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        Message msg = (Message) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "Error.chat.message");
    }
}
