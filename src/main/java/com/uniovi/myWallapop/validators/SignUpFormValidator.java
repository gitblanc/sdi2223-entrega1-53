package com.uniovi.myWallapop.validators;


import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class SignUpFormValidator implements Validator {
    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (usersService.existsEmail(user.getEmail())) {
            errors.rejectValue("email", "Error.signup.emailAlreadyExists");
        }
    }
}