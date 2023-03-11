package com.uniovi.myWallapop.controllers;

import com.uniovi.myWallapop.entities.Log;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.services.LogsService;
import com.uniovi.myWallapop.services.SecurityService;
import com.uniovi.myWallapop.services.UsersService;
import com.uniovi.myWallapop.services.RolesService;
import com.uniovi.myWallapop.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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


    /**
     * Servicio de roles de usuario
     */
    @Autowired
    private RolesService rolesService;

    /**
     * Servicio de logs
     */
    @Autowired
    private LogsService logsService;


    /**
     * Responde a la petición /user/deleteusers
     * Eliminará los usuarios con ids pasadas por parámetro
     */
    @RequestMapping(value = "/user/deleteusers", method = RequestMethod.GET)
    public String deleteConfig(@RequestParam(value = "ids", required = false) Long[] id) {
        if (id != null && id.length != 0) {
            for (Long i : id) {
                String description = "Usuario con id " + i + " ha sido borrado";
                logsService.addLog(new Log(Log.Tipo.PET, description));
            }
            usersService.deleteUsers(id);
        }
        return "redirect:/user/userslist";
    }


    /**
     * Responde a la petición signup de tipo Post
     * Redirigirá a la vista home si no hay ningún error, en
     * caso contario volverá a la vista de registro
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result, Model model) {
        signUpFormValidator.validate(user, result);
        user.setAmount(100);
        if (result.hasErrors()) {
            String description = "Error al registrar el usuario";
            logsService.addLog(new Log(Log.Tipo.ALTA_ERR, description));
            model.addAttribute("user", user);
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        String description = "Usuario con id " + user.getId() + " ha sido registrado";
        logsService.addLog(new Log(Log.Tipo.ALTA, description));
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "redirect:home";
    }


    /**
     * Responde a la petición /login
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        String description = "Acceso a la vista /login";
        logsService.addLog(new Log(Log.Tipo.LOGIN_EX, description));
        return "login";
    }


    /**
     * Responderá a la petición de /login-error
     * Sirve para mostrar los mensajes de error en caso
     * de fallo en el formulario de logeo
     *
     * @param model
     * @return
     */
    @RequestMapping("/login-error")
    public String login(Model model) {
        String description = "Error en el logeo de usuario, nombre de usuario o contraseña incorrectos";
        logsService.addLog(new Log(Log.Tipo.LOGIN_ERR, description));
        model.addAttribute("errorMessage", "Usuario o contraseña inválida");
        return "login";
    }


    /**
     * Responde a la petición home, pasando el usuario para
     * poder acceder a los atributos desde la vista
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        String description = "Acceso a la vista /home del usuario con id " + activeUser.getId();
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "home";
    }


    /**
     * Responderá a la perición /default
     * Redigirá a una petición dependiendo del
     * role del usuario
     *
     * @param model
     * @return
     */
    @RequestMapping("/default")
    public String defaultAfterLogin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        if (activeUser.getRole().equals("ROLE_ADMIN")) {
            String description = "Administrador ha iniciado sesión";
            logsService.addLog(new Log(Log.Tipo.LOGIN_EX, description));
            return "redirect:/user/userslist";
        } else {
            String description = "Usuario con id " + activeUser.getId() + " ha iniciado sesión";
            logsService.addLog(new Log(Log.Tipo.LOGIN_EX, description));
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
        String description = "Acceso a la vista /user/userslist del usuario con id " + activeUser.getId();
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "user/userslist";
    }


    /**
     * Responde a la petición signup de tipo GET
     * Le pasará a la vista el atributo user
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        String description = "Acceso a la vista /signup";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "signup";
    }

    /**
     * Responde a la petición de listar logs
     *
     * @param model
     * @return
     */
    @RequestMapping("/user/logslist")
    public String getLogsList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);
        model.addAttribute("logslist", logsService.getLogs());
        String description = "Acceso a la vista /user/logslist del usuario con id " + activeUser.getId();
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "user/logslist";
    }

    /**
     * Responde a la petición /user/deletlogs
     * Eliminará todos los logs
     */
    @RequestMapping(value = "/user/deletelogs", method = RequestMethod.GET)
    public String deleteLogsConfig() {
        logsService.deleteALLLogs();
        return "redirect:/user/logslist";
    }

    /**
     * Método que filtra los logs del sistema para admin
     * @param model
     * @param tipo
     * @return
     */
    @RequestMapping("/user/logsfiltered")
    public String filterLogs(Model model, @RequestParam("keyword") String tipo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("user", activeUser);

        if(tipo.isBlank() || tipo.toLowerCase().equals("all") || tipo.toLowerCase().equals("todo")){
            model.addAttribute("logslist", logsService.getLogs());
            model.addAttribute("keyword", tipo);
        }else {
            List<Log> logs = logsService.getLogsByType(tipo.toLowerCase());
            model.addAttribute("logslist", logs);
            model.addAttribute("keyword", tipo);
        }
        String description = "Acceso a la vista /user/logslist del usuario con id " + activeUser.getId() + " mediante filtrado";
        logsService.addLog(new Log(Log.Tipo.PET, description));
        return "user/logslist";
    }
}
