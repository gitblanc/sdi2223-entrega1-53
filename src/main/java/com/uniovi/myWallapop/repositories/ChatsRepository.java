package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatsRepository extends CrudRepository<Chat, Long> {
}
