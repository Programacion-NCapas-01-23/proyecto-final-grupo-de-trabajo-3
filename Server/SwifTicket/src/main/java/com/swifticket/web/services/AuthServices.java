package com.swifticket.web.services;

import com.swifticket.web.models.entities.User;

public interface AuthServices {
	Boolean isTokenValid(String token);
	Boolean validateAccount(String code);
	User signIn(String email, String password); 
	String hashPassword(String password);
}
