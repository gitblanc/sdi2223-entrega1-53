package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ChatsService chatsService;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private OffersService offersService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
    }

    /**
     * Método que devueve una lista de usuarios
     * @return
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        for(User u: users){
            if(u.getRole().equals(rolesService.getRoles()[1])) {
                users.remove(u);
                break;
            }

        }
        return users;
    }

    /**
     * Método que devuelve un usuario qcuyo id coincida con el que se le pasa por parámetro
     * @param id
     * @return
     */
    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    /**
     * Método que agrega un usuario
     * @param user
     */
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    /**
     * Método que elimina un usuario
     * @param id
     */
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    /**
     * Método que modifica la contraeña de un usuario y la guarda en la base de datos
     * @param user
     */
    public void addUserFromModify(User user) {
        user.setPassword(user.getPassword());
        usersRepository.save(user);
    }

    /**
     * Devuelve el usuario identificado con el email recibido como parámetro
     * @param email
     * @return user
     */
    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    /**
     * Método que devuelve true si existe un email que se le pasa por parámetro
     * @param email
     * @return
     */
    public boolean existsEmail(String email) {
        for(User u: getUsers()){
            if(u.getEmail().equals((email)))
                return true;
        }
        return false;
    }

    /**
     * Método que agrega un usuario a la base de datos
     * @param user
     */
    public void addUserWithoutEncrypt(User user) {
        user.setPassword(user.getPassword());
        usersRepository.save(user);
    }


    /**
     * Borrará los usuarios con ids
     * pasados por parámetro
     * @param usersids
     */
    public void deleteUsers(Long[] usersids) {
        for(User user: getUsers()){
            for(Long id: usersids){
                if(user.getId().equals(id)){
                    messagesService.deleteMessagesWithUser(user);
                    chatsService.deleteChatsWithUserId(id);
                    offersService.deleteOffersWithUserId(id);
                }
            }
        }
        usersRepository.deleteUsersById(Arrays.asList(usersids));
    }
}
