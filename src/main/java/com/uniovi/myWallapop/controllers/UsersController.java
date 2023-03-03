package com.uniovi.myWallapop.controllers;



import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.SecurityService;
import com.uniovi.myWallapop.services.UsersService;
import com.uniovi.myWallapop.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
public class UsersController {

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

//    @Autowired
//    private RolesService rolesService;


    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        //model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("usersList", usersService.getUsers());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        User originalUser = usersService.getUser(id);
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        usersService.addUserWithoutEncrypt(originalUser);
        return "redirect:/user/details/" + id;
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
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user,result);
        if(result.hasErrors()){
            return "signup";
        }
        //user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
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
        return "home";
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

    @RequestMapping("/user/list/update")
    public String updateList(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list :: tableUsers";
    }

}