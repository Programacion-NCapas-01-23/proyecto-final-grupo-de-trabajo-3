package com.swifticket.web.services;

import com.swifticket.web.models.entities.User;

public interface AuthServices {
	Boolean isTokenValid(String token);
	User validateAccount(String code) throws Exception;
	String generateVerifyAccountToken(String email);
	User signIn(String email, String password); 
	String hashPassword(String password);
}
