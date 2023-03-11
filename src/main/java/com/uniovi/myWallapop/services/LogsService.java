package com.uniovi.myWallapop.services;

import com.uniovi.myWallapop.entities.Log;
import com.uniovi.myWallapop.repositories.LogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class LogsService {
    /**
     * Objeto de logeo
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LogsRepository logsRepository;

    @PostConstruct
    public void init() {
    }

    public void addLog(Log log) {
        Log.Tipo tipo = log.getTipo();
        if (tipo.equals(Log.Tipo.PET) || tipo.equals(Log.Tipo.ALTA) || tipo.equals(Log.Tipo.LOGOUT) || tipo.equals(Log.Tipo.LOGIN_EX))
            logger.info(log.getDescription().toString());
        else
            logger.error(log.getDescription().toString());

        logsRepository.save(log);
    }
}
