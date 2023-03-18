package com.uniovi.myWallapop.repositories;

import com.uniovi.myWallapop.entities.Chat;
import com.uniovi.myWallapop.entities.Log;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogsRepository extends CrudRepository<Log, Long> {

    /**
     * Query que devuelve los logs de un dia descendientemente
     * @return
     */
    @Query("SELECT l FROM Log l ORDER BY l.date desc")
    List<Log> getLogsByDate();

    /**
     * Query que devuelve el log depediendo del tipo
     * @param tipo
     * @return
     */
    @Query("SELECT l FROM Log l WHERE l.tipo = ?1 ORDER BY l.date desc")
    List<Log> findByTipo(Log.Tipo tipo);
}
