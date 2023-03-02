package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Message, Long> {
}
