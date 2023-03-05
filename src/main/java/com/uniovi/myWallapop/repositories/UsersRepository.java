package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM users WHERE id IN(?1)")
    void deleteUsersById(List<Long> ids);
}
