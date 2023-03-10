package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.SecurityService;
import com.uniovi.myWallapop.services.UsersService;
import com.uniovi.myWallapop.services.RolesService;
import com.uniovi.myWallapop.validators.SignUpFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsersController {


    /**
     * Objeto de logeo
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Servicio donde estará la lógica realacionda con
     * los usuarios
     */
    @Autowired
    private UsersService usersService;


    /**
     * Validador de registro
     */
    @Autowired
    private SignUpFormValidator signUpFormValidator;


    /**
     * Servicio de seguridad
     */
    @Autowired
    private SecurityService securityService;


    /**
     * Servicio de roles de usuario
     */
    @Autowired
    private RolesService rolesService;




    /**
     * Responde a la petición /user/deleteusers
     * Eliminará los usuarios con ids pasadas por parámetro
     */
    @RequestMapping(value = "/user/deleteusers", method = RequestMethod.GET)
    public String deleteConfig(@RequestParam(value = "ids", required = false) Long[] id)
    {
        if(id != null && id.length != 0){
           for(Long i: id)
               log.info("Usuario con id "+i+" ha sido borrado");
            usersService.deleteUsers(id);
        }
        return "redirect:/user/userslist";
    }


    /**
     * Responde a la petición signup de tipo Post
     * Redirigirá a la vista home si no hay ningún error, en
     * caso contario volverá a la vista de registro
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result,Model model) {
        signUpFormValidator.validate(user,result);
        user.setAmount(100);
        if(result.hasErrors()){
            log.error("Error al registrar el usuario");
            model.addAttribute("user",user);
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        log.info("Usuario con id "+user.getId()+" ha sido registrado");
        return "redirect:home";
    }


    /**
     * Responde a la petición /login
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    /**
     * Responderá a la petición de /login-error
     * Sirve para mostrar los mensajes de error en caso
     * de fallo en el formulario de logeo
     * @param model
     * @return
     */
    @RequestMapping("/login-error")
    public String login(Model model) {
        log.error("Error en el logeo de usuario, nombre de usuario o contraseña incorrectos");
        model.addAttribute("errorMessage", "Usuario o contraseña inválida");
        return "login";
    }


    /**
     * Responde a la petición home, pasando el usuario para
     * poder acceder a los atributos desde la vista
     * @param model
     * @return
     */
    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        log.info("Acceso a la vista /home del usuario con id "+activeUser.getId());
        return "home";
    }


    /**
     * Responderá a la perición /default
     * Redigirá a una petición dependiendo del
     * role del usuario
     * @param model
     * @return
     */
    @RequestMapping("/default")
    public String defaultAfterLogin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        if(activeUser.getRole().equals("ROLE_ADMIN")){
            log.info("Administrador ha iniciado sesión");
            return "redirect:/user/userslist";
        }else{
            log.info("Usuario con id "+activeUser.getId()+" ha iniciado sesión");
            return "redirect:/offer/list/posted";
        }
    }

    @RequestMapping("/user/userslist")
    public String getUsersList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        model.addAttribute("usersList", usersService.getUsers());
        return "user/userslist";
    }


    /**
     * Responde a la petición signup de tipo GET
     * Le pasará a la vista el atributo user
     * @param model
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }


}
