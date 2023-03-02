package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    /**
     * Devuelve el usuario encontrado con el email especificado
     * @param email
     * @return
     */
    User findByEmail(String email);
}
