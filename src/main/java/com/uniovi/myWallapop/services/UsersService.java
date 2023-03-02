package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
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

    /**
     * Devuelve el usuario identificado con el email recibido como parámetro
     * @param email
     * @return user
     */
    public User getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }
}
