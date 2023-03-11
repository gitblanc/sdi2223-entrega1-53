package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Log;
import com.uniovi.myWallapop.entities.User;
import com.uniovi.myWallapop.repositories.LogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LogsService {
    /**
     * Objeto de logeo
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Repositorio de los logs
     */
    @Autowired
    private LogsRepository logsRepository;

    @PostConstruct
    public void init() {
    }

    /**
     * Método para añadir un log (también se añade al logger)
     *
     * @param log
     */
    public void addLog(Log log) {
        Log.Tipo tipo = log.getTipo();
        if (tipo.equals(Log.Tipo.PET) || tipo.equals(Log.Tipo.ALTA) || tipo.equals(Log.Tipo.LOGOUT) || tipo.equals(Log.Tipo.LOGIN_EX))
            logger.info(log.getDescription().toString());
        else
            logger.error(log.getDescription().toString());

        logsRepository.save(log);
    }

    /**
     * Método que devuelve todos los logs de la base de datos
     *
     * @return
     */
    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<Log>();
        logsRepository.findAll().forEach(logs::add);
        return logs;
    }

    /**
     * Método que elimina un log de la base de datos
     *
     * @param id
     */
    public void deleteLog(Long id) {
        logsRepository.deleteById(id);
    }

    /**
     * Método que elimina varios logs de la base de datos
     *
     * @param ids
     */
    public void deleteLogs(Long[] ids) {
        for (Long id : ids)
            deleteLog(id);
    }

    /**
     * Método que elimina todos los logs
     */
    public void deleteALLLogs() {
        logsRepository.deleteAll();
    }
}
