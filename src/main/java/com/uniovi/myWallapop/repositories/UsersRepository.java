package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    /**
     * Devuelve el usuario encontrado con el email especificado
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * Elimina los usuarios segun su id
     * @param ids
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM users WHERE id IN(?1)")
    void deleteUsersById(List<Long> ids);

    /**
     * Actualiza la cantidad del dinero segun los parametros de cantidad e id
     * @param amount
     * @param id
     */
    @Modifying
    @Transactional
    @Query("UPDATE User SET amount = ?1 WHERE id = ?2")
    void updateAmount(double amount, Long id);
}
