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

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void addUserFromModify(User user) {
        user.setPassword(user.getPassword());
        usersRepository.save(user);
    }

    public boolean existsEmail(String email) {
        for(User u: getUsers()){
            if(u.getEmail().equals((email)))
                return true;
        }
        return false;
    }

    public void addUserWithoutEncrypt(User user) {
        user.setPassword(user.getPassword());
        usersRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
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
                    messagesService.deleteMessagesWithUserId(id);
                    chatsService.deleteChatsWithUserId(id);
                    offersService.deleteOffersWithUserId(id);
                }
            }
        }
        usersRepository.deleteUsersById(Arrays.asList(usersids));
    }
}
