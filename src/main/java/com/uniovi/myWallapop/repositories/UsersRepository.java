package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
