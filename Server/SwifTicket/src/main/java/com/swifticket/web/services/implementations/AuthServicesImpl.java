package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.AuthServices;
import org.springframework.stereotype.Service;

@Service
public class AuthServicesImpl implements AuthServices {
    @Override
    public Boolean isTokenValid(String token) {
        return null;
    }

    @Override
    public Boolean validateAccount(String code) {
        return null;
    }

    @Override
    public User signIn(String email, String password) {
        return null;
    }

    @Override
    public String hashPassword(String password) {
        return null;
    }
}
