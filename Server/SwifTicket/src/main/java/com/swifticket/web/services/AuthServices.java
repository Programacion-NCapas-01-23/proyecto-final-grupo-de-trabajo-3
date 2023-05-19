package com.swifticket.web.services;

import com.swifticket.web.models.entities.User;

import java.util.UUID;

public interface AuthServices {
	Boolean isTokenValid(UUID token);
	Boolean validateAccount(String code);
	User signIn(String email, String password); 
	String hashPassword(String password);
}
