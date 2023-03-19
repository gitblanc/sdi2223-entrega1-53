package com.uniovi.myWallapop.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
    String[] roles = {"ROLE_STANDARD", "ROLE_ADMIN"};

    /**
     * Método que devuelve los roles que hay
     * @return
     */
    public String[] getRoles() {
        return roles;
    }

}
