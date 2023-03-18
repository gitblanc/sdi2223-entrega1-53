package com.uniovi.myWallapop.validators;


import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;


/**
 * Validador para el formulario de registro
 */
@Component
public class SignUpFormValidator implements Validator {
    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Método que valida que el formulario del login tenga
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (usersService.existsEmail(user.getEmail())) {
            errors.rejectValue("email", "error.signup.emailAlreadyExists");
        }

        if(!user.getPassword().equals(user.getPasswordConfirm())){
            errors.rejectValue("password", "error.signup.passwordandconfirmpasswordifferent");
        }
    }
}